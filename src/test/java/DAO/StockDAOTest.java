package DAO;

import DBConfig.HibernateConfig;
import Model.Industry;
import Model.Stock;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StockDAOTest {

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

        em.createNativeQuery("INSERT INTO public.stock (id,name,industry_id) VALUES ('1','Pfizer','1');").executeUpdate();
        em.createNativeQuery("INSERT INTO public.stock (id,name,industry_id) VALUES ('2','Novo','1');").executeUpdate();
        em.createNativeQuery("INSERT INTO public.stock (id,name,industry_id) VALUES ('3','Tesla','2');").executeUpdate();

        em.getTransaction().commit();
    }

}

    @AfterEach
    void tearDown() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createNativeQuery("truncate TABLE  public.industry RESTART IDENTITY CASCADE").executeUpdate();
            em.createNativeQuery("truncate TABLE  public.stock RESTART IDENTITY CASCADE").executeUpdate();
            em.getTransaction().commit();
        }
    }
  

    @Test
    void doesStockExist() {
        try (EntityManager em = emf.createEntityManager()) {
            StockDAO stockDAO = StockDAO.getInstance(emf);

            // Test case for industry that exists
            boolean exists = stockDAO.doesStockExist("Pfizer");
            assertTrue(exists, "Expected Stock 'Pfizer' to exist");

            // Test case for industry that does not exist
            boolean notExists = stockDAO.doesStockExist("NonExistent");
            assertFalse(notExists, "Expected industry 'NonExistent' to not exist");
        }
    }
    @Test
    void findByName() {

        StockDAO stockDAO= StockDAO.getInstance(emf);
        Stock stock = stockDAO.findByName("Novo", Stock.class, "Stock");
        assertEquals("2", stock.getId());
        assertNotNull(stock);

    }

    @Test
    void findById() {

        StockDAO stockDAO = StockDAO.getInstance(emf);
        Stock stock = stockDAO.findById("3");
        assertEquals("Tesla", stock.getName());
        assertNotNull(stock);

    }

    @Test
    void findAll() {

        StockDAO stockDAO = StockDAO.getInstance(emf);
        List<Stock> stockList = stockDAO.findAll("Stock", Stock.class);
        assertEquals("1", stockList.get(0).getId());
        assertEquals("2", stockList.get(1).getId());
        assertEquals("3", stockList.get(2).getId());

        assertEquals("Pfizer", stockList.get(0).getName());
        assertEquals("Novo", stockList.get(1).getName());
        assertEquals("Tesla", stockList.get(2).getName());
    }

    @Test
    void persist() {

        StockDAO stockDAO = StockDAO.getInstance(emf);
        Stock stock = new Stock("Amazon");
        Stock stockTest;
        stock.setId("4");
        stockDAO.persist(stock);
        try (EntityManager em = emf.createEntityManager()){
            stockTest = em.find(Stock.class, "4");
        }
        assertEquals("Amazon", stockTest.getName());

    }

    @Test
    void update() {
        StockDAO stockDAO = StockDAO.getInstance(emf);
        Stock stock;
        Stock stockTest;
        try (var em = emf.createEntityManager()) {
            stock = em.find(Stock.class, "1");
            stock.setName("NytNavn");

        }
        stockDAO.update(stock);
        try (var em = emf.createEntityManager()) {
            stockTest = em.find(Stock.class, "1");

        }assertEquals(stock.getName(), stockTest.getName());
        assertEquals(stock.getId(), stockTest.getId());
    }

    @Test
    void delete() {

        StockDAO stockDAO = StockDAO.getInstance(emf);
        Stock stock;
        try (var em = emf.createEntityManager()){
            stock = em.find(Stock.class, "1");

        }
        boolean delete = stockDAO.delete(stock, stock.getId());
        boolean deleteCheck = stockDAO.delete(stock, stock.getId());
        assertTrue(delete);
        


    }
}