package com.madikhan.estore.dao.impl;

import com.madikhan.estore.dao.CartItemDAO;
import com.madikhan.estore.model.CartItem;

import java.sql.SQLException;
import java.util.List;

public class CartItemDAOImpl implements CartItemDAO {
    @Override
    public void create(CartItem object) throws SQLException {

    }

    @Override
    public CartItem getByID(Long id) throws SQLException {
        return null;
    }

    @Override
    public List<CartItem> getAll() throws SQLException {
        return null;
    }

    @Override
    public void update(Long id, CartItem object) throws SQLException {

    }

    @Override
    public void delete(Long id) throws SQLException {

    }
}
