package DAO;

import DBConfig.HibernateConfig;
import Model.Stock;
import Model.StockRisk;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StockDAO {

    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("stock_db");

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

    public Stock updateStock(Stock s, StockDTO newS ){
        try (var em = emf.createEntityManager()){
            em.getTransaction().begin();
            if(s != newS) {
                s.addStockPrice(newS);
            Stock updatedStock = em.merge(newS);
            em.getTransaction().commit();
            return updatedStock;
            }
        }return null;
    }
}


