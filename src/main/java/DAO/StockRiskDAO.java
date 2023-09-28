package DAO;

import DBConfig.HibernateConfig;
import Model.StockRisk;
import jakarta.persistence.EntityManagerFactory;

public class StockRiskDAO extends DAO<StockRisk> {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("stock_db");
    private static StockRiskDAO instance;


    //****** Create Singleton *****\\
    public static StockRiskDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new StockRiskDAO();
        }
        return instance;
    }

    @Override
    public StockRisk findByName(String name, Class<StockRisk> tClass, String table) {
        return null;
    }
}
