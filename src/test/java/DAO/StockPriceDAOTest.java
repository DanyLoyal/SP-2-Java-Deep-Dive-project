package DAO;

import DBConfig.HibernateConfig;
import Model.Stock;
import Model.StockPrice;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StockPriceDAOTest {

    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("stock_db_test");

    @BeforeEach
    void setUp() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            em.createNativeQuery("INSERT INTO public.stock (id, name) VALUES ('0P0000OQN8', 'Tesla Inc');").executeUpdate();
            em.createNativeQuery("INSERT INTO public.stock (id, name) VALUES ('0P0000OQN7', 'Nordnet');").executeUpdate();

            em.createNativeQuery("INSERT INTO public.stock_price (price, price_change, price_date, stock_id) VALUES (400, '15.8%', '2023-09-27', '0P0000OQN8');").executeUpdate();
            em.createNativeQuery("INSERT INTO public.stock_price (price, price_change, price_date, stock_id) VALUES (350, '14.1%', '2023-09-21', '0P0000OQN8');").executeUpdate();
            em.createNativeQuery("INSERT INTO public.stock_price (price, price_change, price_date, stock_id) VALUES (999, '3.2%', '2023-09-22', '0P0000OQN7');").executeUpdate();

            em.getTransaction().commit();
        }
    }


    @AfterEach
    void tearDown() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createNativeQuery("truncate TABLE  public.stock RESTART IDENTITY CASCADE").executeUpdate();
            em.createNativeQuery("truncate TABLE  public.stock_price RESTART IDENTITY CASCADE").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @Test
    void findByName() {

        assertNull(StockPriceDAO.getInstance(emf).findByName("Tesla Inc", StockPrice.class, "stock_price"));

    }

    @Test
    void findById() {
        StockPrice sp = StockPriceDAO.getInstance(emf).findById(1, StockPrice.class);
        assertEquals(400, sp.getPrice());
        assertEquals("Tesla Inc", sp.getStock().getName());
        assertNull(StockPriceDAO.getInstance(emf).findById(4, StockPrice.class));
    }

    @Test
    void findAll() {
        List<StockPrice> stockPrices = StockPriceDAO.getInstance(emf).findAll("StockPrice", StockPrice.class);
        assertEquals(3, stockPrices.size());
        assertEquals("Nordnet", stockPrices.get(2).getStock().getName());
        assertEquals("0P0000OQN8", stockPrices.get(1).getStock().getId());
    }

    @Test
    void persist() {
        Stock stock;
        StockPrice stockPrice = new StockPrice(300, "1.2%", LocalDate.now());
        try(EntityManager em = emf.createEntityManager()){
            stock = em.find(Stock.class, "0P0000OQN7");
        }
        stock.addStockPrice(stockPrice);
        StockPriceDAO.getInstance(emf).persist(stockPrice);

        StockPrice stockPrice2;
        try(EntityManager em = emf.createEntityManager()){
            stockPrice2 = em.find(StockPrice.class, 4);
        }
        assertEquals("Nordnet", stockPrice2.getStock().getName());
        assertEquals(300, stockPrice2.getPrice());
        assertEquals(4, stockPrice2.getId());
    }

    @Test
    void update() {
        StockPrice update;
        StockPrice found;
        try(EntityManager em = emf.createEntityManager()){
            update = em.find(StockPrice.class, 2);
            update.setDate(LocalDate.now());
            em.getTransaction().begin();
            em.merge(update);
            em.getTransaction().commit();
            found = em.find(StockPrice.class, 2);
        }
        assertEquals(update.getPrice(), found.getPrice());
        assertEquals(LocalDate.now().getDayOfMonth(), found.getPriceDate().getDayOfMonth());
    }

    @Test
    void delete() {
        StockPrice sp;
        try(EntityManager em = emf.createEntityManager()){
            sp = em.find(StockPrice.class, 2);
            assertNotNull(sp);
        }
        StockPrice spDelete;
        try(EntityManager em = emf.createEntityManager()){
            spDelete = em.find(StockPrice.class, 2);
        }
        assertTrue(StockPriceDAO.getInstance(emf).delete(spDelete));
        try(EntityManager em = emf.createEntityManager()){
            assertNull(em.find(StockPrice.class, 2));
        }
    }
}