package DAO;

import DBConfig.HibernateConfig;
import Model.Industry;
import Model.Stock;
import Model.StockRisk;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StockDAO extends DAO<Stock> {

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
    public boolean doesStockExist(String name) {
        try (var em = emf.createEntityManager()) {
            Stock stock = em.find(Stock.class, name);
            if (stock != null) {
                return true;
            }
            return false;
        }
    }
}


