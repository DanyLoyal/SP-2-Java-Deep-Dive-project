package DAO;

import DBConfig.HibernateConfig;
import Model.Stock;
import Model.StockRisk;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StockDAO {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("stock_db");
    private static StockDAO instance;

    //****** Create Singleton *****\\
    public static StockDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new StockDAO();
        }
        return instance;
    }

    public Stock persistStockData(Stock s) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(s);
            em.getTransaction().commit();
            return s;
        }
    }

    public Stock findStockById(String id) {
        try (var em = emf.createEntityManager()) {
            Stock stock = em.find(Stock.class, id);
            if (stock != null) {
                return stock;
            } else {
                return null;
            }
        }
    }

    public Stock updateStock(Stock stock){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.merge(stock);
            em.getTransaction().commit();
        }
        return stock;
    }




}


