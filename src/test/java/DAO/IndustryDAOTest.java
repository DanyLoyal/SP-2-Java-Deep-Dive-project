package DAO;

import DBConfig.HibernateConfig;
import Model.Industry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IndustryDAOTest {

    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("stock_db_test");
    @BeforeEach

    void setUp() {


        try(
    EntityManager em = emf.createEntityManager())

    {
        em.getTransaction().begin();

        em.createNativeQuery("INSERT INTO public.industry (name) VALUES ('Metal');").executeUpdate();
        em.createNativeQuery("INSERT INTO public.industry (name) VALUES ('Car');").executeUpdate();
        em.createNativeQuery("INSERT INTO public.industry (name) VALUES ('Båd');").executeUpdate();

        em.getTransaction().commit();
    }

}

    @AfterEach
    void tearDown() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createNativeQuery("truncate TABLE  public.industry RESTART IDENTITY CASCADE").executeUpdate();
            em.getTransaction().commit();
        }
    }
  

    @Test
    void doesIndustryExist() {
        try (EntityManager em = emf.createEntityManager()) {
            IndustryDAO industryDAO = new IndustryDAO();

            // Test case for industry that exists
            boolean exists = industryDAO.doesIndustryExist("Metal");
            assertTrue(exists, "Expected industry 'Metal' to exist");

            // Test case for industry that does not exist
            boolean notExists = industryDAO.doesIndustryExist("NonExistent");
            assertFalse(notExists, "Expected industry 'NonExistent' to not exist");
        }
    }
    @Test
    void findByName() {

        IndustryDAO industryDAO = IndustryDAO.getInstance(emf);
        Industry industry = industryDAO.findByName("Car", Industry.class, "Industry");
        assertEquals(2, industry.getId()) ;
        assertNotNull(industry);

    }

    @Test
    void findById() {

        IndustryDAO industryDAO = IndustryDAO.getInstance(emf);
        Industry industry = industryDAO.findById(3, Industry.class);
        assertEquals("Båd", industry.getName());
        assertNotNull(industry);

    }

    @Test
    void findAll() {

        IndustryDAO industryDAO = IndustryDAO.getInstance(emf);
        List<Industry> industryList = industryDAO.findAll("Industry", Industry.class);
        assertEquals(1, industryList.get(0).getId());
        assertEquals(2, industryList.get(1).getId());
        assertEquals(3, industryList.get(2).getId());

        assertEquals("Metal", industryList.get(0).getName());
        assertEquals("Car", industryList.get(1).getName());
        assertEquals("Båd", industryList.get(2).getName());
    }

    @Test
    void persist() {

        IndustryDAO industryDAO = IndustryDAO.getInstance(emf);
        Industry industry = new Industry("Drinks");
        industryDAO.persist(industry);

        assertEquals(4, industry.getId());

    }

    @Test
    void update() {
        IndustryDAO industryDAO = IndustryDAO.getInstance(emf);
        Industry industry;
        Industry industryTrue;
        try (var em = emf.createEntityManager()) {
            industry = em.find(Industry.class, 1);
            industry.setName("Drugs");

        }
        industryDAO.update(industry);
        try (var em = emf.createEntityManager()) {
            industryTrue = em.find(Industry.class, 1);

        }assertEquals(industry.getName(), industryTrue.getName());
        assertEquals(industry.getId(), industryTrue.getId());
    }

    @Test
    void delete() {

        IndustryDAO industryDAO = IndustryDAO.getInstance(emf);
        Industry industry;
        try (var em = emf.createEntityManager()){
            industry = em.find(Industry.class, 1);

        }
        boolean delete = industryDAO.delete(industry, industry.getId());
        boolean deleteCheck = industryDAO.delete(industry, industry.getId());
        assertTrue(delete);
        assertFalse(deleteCheck);
    }
}