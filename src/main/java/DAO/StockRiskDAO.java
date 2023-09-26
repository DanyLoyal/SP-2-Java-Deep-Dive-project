package DAO;

import DBConfig.HibernateConfig;
import Model.StockRisk;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class StockRiskDAO {

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


    public StockRisk findStockRiskById(int id) {
        try (var em = emf.createEntityManager()) {
            StockRisk stockRisk = em.find(StockRisk.class, id);

            if (stockRisk != null) {
                return stockRisk;
            }
            return null;
        }
    }

    public StockRisk persistStockRisk(StockRisk stock) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            List<StockRisk> stockRisks = em.createNativeQuery("SELET * FROM stock_risk").getResultList();
            for (StockRisk name : stockRisks) {
                if (name != stockRisks) {
                    em.persist(stock);
                    em.getTransaction().commit();
                    return stock;
                }
            }
        }
        return null;
    }


    public StockRisk updateStockRisk(StockRisk stockRisk){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            if(stockRisk.getRiskDate())
            em.merge(stockRisk);
            em.getTransaction().commit();
        }return stockRisk;
    }



}
