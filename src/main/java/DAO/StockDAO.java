package DAO;

import DBConfig.HibernateConfig;
import Model.Industry;
import Model.Stock;
import Model.StockRisk;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

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
            TypedQuery<Stock> query = em.createQuery(
                    "SELECT i FROM Stock i WHERE i.name = :name", Stock.class);
            query.setParameter("name", name);
            Stock stock = query.getSingleResult();
            //return industry != null;
            if (stock != null){
                return true;
            }else{
                return false;
            }
        }catch (NoResultException nre){
            return false;
        }
    }

    public Stock findById(String id) {
        try (var em = emf.createEntityManager()) {
            Stock stock = em.find(Stock.class, id);
            if (stock != null) {
                return stock;
            }
            return null;
        }
    }
    public boolean delete(Stock stock, String id) {

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.remove(stock);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            return false;
        }
        try (var em = emf.createEntityManager()){
            if(em.find(Stock.class, id) != null){
                return false;
            }
            else {
                return true;
            }
        }
    }
}


