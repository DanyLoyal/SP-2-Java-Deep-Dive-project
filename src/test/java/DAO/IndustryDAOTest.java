package DAO;

import DBConfig.HibernateConfig;
import Model.Industry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IndustryDAOTest {

    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("daotest");
    @BeforeEach

    void setUp() {


        try(
    EntityManager em = emf.createEntityManager())

    {
        em.getTransaction().begin();

        em.createNativeQuery("INSERT INTO public.industry (id, name) VALUES (1, 'Metal');").executeUpdate();
        em.createNativeQuery("INSERT INTO public.industry (id, name) VALUES (2, 'Car');").executeUpdate();
        em.createNativeQuery("INSERT INTO public.industry (id, name) VALUES (3, 'Båd');").executeUpdate();

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
}