package DAO;

import DBConfig.HibernateConfig;
import Model.StockPrice;
import jakarta.persistence.EntityManagerFactory;

public class StockPriceDAO extends DAO<StockPrice> {

    private static EntityManagerFactory emf;
    private static StockPriceDAO instance;

    //****** Create Singleton *****\\
    public static StockPriceDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new StockPriceDAO();
        }
        return instance;
    }

    @Override
    public StockPrice findByName(String name, Class<StockPrice> tClass, String table) {
        return null;
    }
}