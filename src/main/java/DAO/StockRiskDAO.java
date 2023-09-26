package DAO;

import DBConfig.HibernateConfig;
import Model.StockRisk;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class StockRiskDAO {

    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("stock_db");


    public StockRisk findStockRiskById(int id) {
        try (var em = emf.createEntityManager()) {
            StockRisk stockRisk = em.find(StockRisk.class, id);

            if (stockRisk != null) {
                return stockRisk;
            }
            return null;
        }
    }

    public StockRisk retrieveStockrisks(){
        try(var em = emf.createEntityManager()){
            List<StockRisk> stockRisks = em.

        }


    }

    public StockRisk persistStockRisk (StockRisk s){
        try (var em = emf.createEntityManager()){
            em.getTransaction().begin();


        }



    }


}
