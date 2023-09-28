package DAO;

import DBConfig.HibernateConfig;
import Model.Industry;
import Model.StockRisk;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StockRiskDAOTest {
    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("stock_db_test");

    @BeforeEach
    void setUp() {
        try(
                EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();

            em.createNativeQuery("INSERT INTO public.stock_risk (profile, rating, risk_date) VALUES ('Tesla_Vurdering', '7.45','2023,06,20')").executeUpdate();
            em.createNativeQuery("INSERT INTO public.stock_risk (profile, rating, risk_date) VALUES ('Vesta_Vurdering', '15.02','2021,03,10')").executeUpdate();
            em.createNativeQuery("INSERT INTO public.stock_risk (profile, rating, risk_date) VALUES ('Papirklip_Vurdering', '0.17','2022,11,24')").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @AfterEach
    void tearDown() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createNativeQuery("truncate TABLE  public.stock_risk RESTART IDENTITY CASCADE").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @Test
    void findByName() {
        assertNull(null);
    }

    @Test
    void findById() {

        StockRiskDAO stockRiskDAO = StockRiskDAO.getInstance(emf);
        StockRisk stockRisk = stockRiskDAO.findById(2, StockRisk.class);
        assertEquals("Vesta_Vurdering", stockRisk.getProfile());
        assertNotNull(stockRisk);

    }

    @Test
    void findAll() {

        StockRiskDAO stockRiskDAO = StockRiskDAO.getInstance(emf);
        List<StockRisk> stockRiskList = stockRiskDAO.findAll("StockRisk", StockRisk.class);
        assertEquals(1, stockRiskList.get(0).getId());
        assertEquals(2, stockRiskList.get(1).getId());
        assertEquals(3, stockRiskList.get(2).getId());

        assertEquals("Tesla_Vurdering", stockRiskList.get(0).getProfile());
        assertEquals("Vesta_Vurdering", stockRiskList.get(1).getProfile());
        assertEquals("Papirklip_Vurdering", stockRiskList.get(2).getProfile());

    }

    @Test
    void persist() {

        StockRiskDAO stockRiskDAO = StockRiskDAO.getInstance(emf);
        StockRisk stockRisk = new StockRisk(2.44,"Mini_Cooper", LocalDate.of(2022,05,06));
        stockRiskDAO.persist(stockRisk);

        assertEquals(4, stockRisk.getId());

    }

    @Test
    void update() {

        StockRiskDAO stockRiskDAO = StockRiskDAO.getInstance(emf);
        StockRisk stockRisk;
        StockRisk stockRiskTrue;
        try (var em = emf.createEntityManager()) {
            stockRisk = em.find(StockRisk.class, 2);
            stockRisk.setProfile("Vindmolle");

        }
        stockRiskDAO.update(stockRisk);
        try (var em = emf.createEntityManager()) {
            stockRiskTrue = em.find(StockRisk.class, 2);

        }assertEquals(stockRisk.getProfile(), stockRiskTrue.getProfile());
        assertEquals(stockRisk.getId(), stockRiskTrue.getId());

    }

    @Test
    void delete() {

        StockRiskDAO stockRiskDAO = StockRiskDAO.getInstance(emf);
        StockRisk stockRisk;
        try (var em = emf.createEntityManager()){
            stockRisk = em.find(StockRisk.class, 2);

        }
        boolean delete = stockRiskDAO.delete(stockRisk);
        boolean deleteCheck = stockRiskDAO.delete(stockRisk);
        assertTrue(delete);
        assertFalse(deleteCheck);
    }

    @Test
    void testFindByName() {
    }

}