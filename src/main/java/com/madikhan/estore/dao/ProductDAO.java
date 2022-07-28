package com.madikhan.estore.dao;



import com.madikhan.estore.model.Cart;
import com.madikhan.estore.model.Product;
import com.madikhan.estore.model.form.ProductForm;
import com.madikhan.estore.model.form.SearchForm;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO extends DAO<Product> {

    void create(Product product) throws SQLException;

    void delete(Long productID) throws SQLException;

    Product getByID(Long id, int languageID) throws SQLException;

    List<Product> getProductsWithLimit(Long page, int limitOfProducts, int languageID);

    List<Product> getProductsByCategoryWithLimit(String categoryUrl, Long page, int limitOfProducts, int languageID);

    List<Product> getProductsBySearchForm(SearchForm searchForm, Long page, int limitOfProducts, int languageID);

    Long getCountAllProductsBySearchForm(SearchForm searchForm, int languageID);

    Long getCountAllProducts();

    Long getCountAllProductsByCategory(String categoryUrl, int languageID);

    void addProductToCart(ProductForm productForm, Cart cart, Boolean isPersisted, int languageID) throws SQLException;

}
