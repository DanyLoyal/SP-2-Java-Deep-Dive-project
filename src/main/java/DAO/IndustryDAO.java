package DAO;

import DBConfig.HibernateConfig;
import Model.Industry;
import jakarta.persistence.EntityManagerFactory;

public class IndustryDAO {

    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("stock_db");


    public Industry findIndustryByName(String name) {
        try (var em = emf.createEntityManager()) {
            Industry industry = em.find(Industry.class, name);
            if (industry != null) {
                return industry;
            } else {
                return null;
            }
        }
    }





}
