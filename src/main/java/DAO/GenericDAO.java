package DAO;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public interface GenericDAO <T>{

    T findByName(String name, Class<T> tClass, String table);
    T findById(int id, Class<T> tClass);
    List<T> findAll(String table, Class<T> tClass);
    T persist(T t);
    T update(T t);
    boolean delete(T t, int id);

}
