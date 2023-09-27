package DAO;

import DBConfig.HibernateConfig;
import Model.Industry;
import jakarta.persistence.EntityManagerFactory;

public class IndustryDAO extends DAO<Industry> {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("stock_db");
    private static IndustryDAO instance;

    //****** Create Singleton *****\\
    public static IndustryDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new IndustryDAO();
        }
        return instance;
    }

    public boolean doesIndustryExist(String name) {
        try (var em = emf.createEntityManager()) {
            Industry industry = em.find(Industry.class, name);
            if (industry != null) {
                return true;
            }
            return false;
        }
    }
}
