package DAO;

import DBConfig.HibernateConfig;
import Model.Industry;
import Model.Stock;
import Model.StockRisk;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.postgresql.core.NativeQuery;

import java.util.ArrayList;
import java.util.List;

public class DAO<T> implements GenericDAO<T> {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("stock_db");

    @Override
    public T findByName(String name, Class<T> tClass, String table) {
        try (var em = emf.createEntityManager()) {
            TypedQuery< T > query = em.createQuery(
                    "SELECT i FROM " + table + " i WHERE i.name = :name", tClass);
            query.setParameter("name", name);
            T t = query.getSingleResult();
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
    public List<T> findAll(String table, Class<T> tClass) {

        try (var em = emf.createEntityManager()) {
            TypedQuery<T> query =  em.createQuery("SELECT i FROM " + table + " i", tClass);
            List<T> typeList = query.getResultList();
            if (typeList != null) {
                return typeList;
            }
        }
        return null;
    }

    @Override
    public T persist(T t) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
                }
        return t;
            }



    @Override
    public T update(T t) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(t);
            em.getTransaction().commit();
        }
        return t;
    }

    @Override
    public boolean delete(T t, int id) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.remove(t);
            em.getTransaction().commit();

        } catch (Exception e) {
            return false;
        }
        try (var em = emf.createEntityManager()){
            if(em.find(t.getClass(), id) != null){
                return false;
            }
            else {
                return true;
            }
        }
    }
}
