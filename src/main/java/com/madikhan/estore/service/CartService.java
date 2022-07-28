package com.madikhan.estore.service;

import com.madikhan.estore.model.Cart;
import com.madikhan.estore.model.CartItem;
import com.madikhan.estore.model.form.ProductForm;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CartService {

    void save(Long userID, ProductForm productForm) throws SQLException;

    void remove(Long userID, Long productID, Integer productCount);

    List<CartItem> listAllCartItemsByUserID(Long userID);

    void addProductToCartFromDB(Long userID, Cart cart, Integer languageID) throws SQLException;

    void validateShoppingCartSize(Long idProduct, Map<Long, CartItem> products);

    void validateProductCount(int productCount);

}
