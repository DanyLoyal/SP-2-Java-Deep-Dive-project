package DAO;

import DBConfig.HibernateConfig;
import Model.Stock;
import jakarta.persistence.EntityManagerFactory;

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


