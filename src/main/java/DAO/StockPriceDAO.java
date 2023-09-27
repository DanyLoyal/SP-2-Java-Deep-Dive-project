package DAO;

import DBConfig.HibernateConfig;
import Model.StockPrice;
import jakarta.persistence.EntityManagerFactory;

public class StockPriceDAO {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("stock_db");
    private static StockPriceDAO instance;

    //****** Create Singleton *****\\
    public static StockPriceDAO getInstance(EntityManagerFactory _emf){
        if(instance == null){
            emf = _emf;
            instance = new StockPriceDAO();
        }
        return instance;
    }
}