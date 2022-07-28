package com.madikhan.estore.dao;

import com.madikhan.estore.model.Cart;
import com.madikhan.estore.model.CartItem;
import com.madikhan.estore.model.form.ProductForm;

import java.sql.SQLException;
import java.util.List;

public interface CartDAO extends DAO<Cart> {

    void create(Cart cart) throws SQLException;

    void create(Long userID, ProductForm productForm);

    CartItem getByUserIDAndProductID(Long userID, Long productID);

    void delete(Long userID, Long productID, Integer productCount);

    List<CartItem> getAllByUserID(Long userID);

    void addProductToCartFromDB(Long userID, Cart cart, Integer languageID) throws SQLException;

}
