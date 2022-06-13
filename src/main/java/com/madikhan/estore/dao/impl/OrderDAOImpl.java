package com.madikhan.estore.dao.impl;

import com.madikhan.estore.dao.OrderDAO;
import com.madikhan.estore.model.Order;

import java.sql.SQLException;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public void create(Order object) throws SQLException {

    }

    @Override
    public Order getByID(Long id) throws SQLException {
        return null;
    }

    @Override
    public List<Order> getAll() throws SQLException {
        return null;
    }

    @Override
    public void update(Long id, Order object) throws SQLException {

    }

    @Override
    public void delete(Long id) throws SQLException {

    }
}
