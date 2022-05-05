package com.madikhan.estore.dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {

    void create(T object) throws SQLException;
    void update(Long id, T object);
    T getByID(Long id);
    List<T> getAll();
    void delete(Long id);

}
