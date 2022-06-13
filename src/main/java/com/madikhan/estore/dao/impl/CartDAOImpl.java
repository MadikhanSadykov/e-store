package com.madikhan.estore.dao.impl;

import com.madikhan.estore.dao.CartDAO;
import com.madikhan.estore.model.Cart;

import java.sql.SQLException;
import java.util.List;

public class CartDAOImpl implements CartDAO {
    @Override
    public void create(Cart object) throws SQLException {

    }

    @Override
    public Cart getByID(Long id) throws SQLException {
        return null;
    }

    @Override
    public List<Cart> getAll() throws SQLException {
        return null;
    }

    @Override
    public void update(Long id, Cart object) throws SQLException {

    }

    @Override
    public void delete(Long id) throws SQLException {

    }
}
