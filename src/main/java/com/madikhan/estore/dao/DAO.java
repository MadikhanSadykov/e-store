package com.madikhan.estore.dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {

    void create(T object) throws SQLException;

    T getByID(Long id) throws SQLException;

    List<T> getAll() throws SQLException;

    void update(Long id, T object) throws SQLException;

    void delete(Long id) throws SQLException;

}
