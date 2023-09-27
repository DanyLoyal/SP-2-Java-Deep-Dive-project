package DAO;

import java.util.List;

public interface GenericDAO <T>{

    T findByName(String name, Class<T> tClass);
    T findById(int id, Class<T> tClass);
    List<T> findAll(String table);
    T persist(T t, String table);
    T update(T t);
    boolean delete(T t);

}
