package DAO;

import DBConfig.HibernateConfig;
import Model.Stock;
import Model.StockRisk;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class DAO <T>implements GenericDAO <T>{

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("stock_db");
    private static StockDAO instance;

    public static StockDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new StockDAO();
        }
        return instance;
    }


    @Override
    public T findByName(String name, Class<T> tClass) {
        try (var em = emf.createEntityManager()) {
            T t = em.find(tClass, name);

            if (t != null) {
                return t;
            }
            return null;
        }
    }

    @Override
    public T findById(int id, Class<T> tClass) {
        try (var em = emf.createEntityManager()) {
            T t = em.find(tClass, id);

            if (t != null) {
                return t;
            }
            return null;
        }
    }

    @Override
    public List<T> findAll(String table) {

        try (var em = emf.createEntityManager()){
            List<T> typeList = em.createNativeQuery("SELET * FROM "+table).getResultList();
            if(typeList != null){
                return typeList;
            }
        }
        return null;
    }

    @Override
    public T persist(T t, String table) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            List<T> typeList = em.createNativeQuery("SELET * FROM "+table).getResultList();
            for (T name : typeList) {
                if (name != typeList) {
                    em.persist(t);
                    em.getTransaction().commit();
                    return t;
                }
            }
        }
        return null;
    }

    @Override
    public T update(T t) {
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.merge(t);
            em.getTransaction().commit();
        }return t;
    }

    @Override
    public boolean delete(T t) {
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.remove(t);
            em.getTransaction().commit();
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
