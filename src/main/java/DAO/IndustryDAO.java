package DAO;

import DBConfig.HibernateConfig;
import Model.Industry;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class IndustryDAO extends DAO<Industry> {

    static EntityManagerFactory emf;
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
            TypedQuery<Industry> query = em.createQuery(
                    "SELECT i FROM Industry i WHERE i.name = :name", Industry.class);
            query.setParameter("name", name);
            Industry industry = query.getSingleResult();
            System.out.println(industry + "test industryDAO");
            //return industry != null;
            if (industry != null){
                return true;
            }else{
                return false;
            }
        }catch (NoResultException nre){
            return false;
        }
      }
    }

