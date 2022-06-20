package com.madikhan.estore.service.impl;

import com.madikhan.estore.dao.CartDAO;
import com.madikhan.estore.dao.impl.CartDAOImpl;
import com.madikhan.estore.model.Cart;
import com.madikhan.estore.model.CartItem;
import com.madikhan.estore.model.form.ProductForm;
import com.madikhan.estore.service.CartService;

import java.sql.SQLException;
import java.util.List;

public class CartServiceImpl implements CartService {

    private static CartService instance;
    private CartDAO cartDAO = CartDAOImpl.getInstance();

    private CartServiceImpl() {
        super();
    }

    public static CartService getInstance() {
        if (instance == null)
            instance = new CartServiceImpl();
        return instance;
    }

    @Override
    public void save(Long userID, ProductForm productForm) {
        cartDAO.create(userID, productForm);
    }

    @Override
    public void remove(Long userID, Long productID, Integer productCount) {
            cartDAO.delete(userID, productID, productCount);
    }

    @Override
    public List<CartItem> listAllCartItemsByUserID(Long userID) {
        return cartDAO.getAllByUserID(userID);
    }

    @Override
    public void addProductToCartFromDB(Long userID, Cart cart, Integer languageID) throws SQLException {
        cartDAO.addProductToCartFromDB(userID, cart, languageID);
    }
}
