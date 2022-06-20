package com.madikhan.estore.service;

import com.madikhan.estore.model.Cart;
import com.madikhan.estore.model.CartItem;
import com.madikhan.estore.model.Product;
import com.madikhan.estore.model.form.ProductForm;
import com.madikhan.estore.model.form.SearchForm;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {

    Product getProductByID(Long idProduct, int languageID) throws SQLException;

    List<Product> listAllProducts(Long page, int maxAmountOfProducts, int languageID) throws SQLException;

    List<Product> listProductsByCategory(String categoryUrl, Long page, int maxAmountOfProducts, int languageID);

    List<Product> listProductsBySearchForm(SearchForm searchForm, Long page, int maxAmountOfProducts, int languageID);

    Long countAllProductsBySearchForm(SearchForm searchForm, int languageID);

    Long countAllProducts();

    Long countAllProductsByCategory(String categoryUrl, int languageID);

    void addProductToCart(ProductForm productForm, Cart cart, Boolean isPersisted, int languageID) throws SQLException;

    void addProductToCartFromDB(CartItem cartItem, Cart cart, Boolean isPersisted, int languageID) throws SQLException;

    void removeProductFromCart(ProductForm productForm, Cart cart) throws SQLException;

    String serializeCart(Cart cart);

    Cart deserializeCart(String string, int languageID) throws SQLException ;

}
