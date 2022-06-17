package com.madikhan.estore.service;

import com.madikhan.estore.model.Product;
import com.madikhan.estore.model.search.SearchForm;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {

    List<Product> listAllProducts(Long page, int maxAmountOfProducts, int languageID) throws SQLException;

    List<Product> listProductsByCategory(String categoryUrl, Long page, int maxAmountOfProducts, int languageID);

    List<Product> listProductsBySearchForm(SearchForm searchForm, Long page, int maxAmountOfProducts, int languageID);

    Long countAllProductsBySearchForm(SearchForm searchForm, int languageID);

    Long countAllProducts();

    Long countAllProductsByCategory(String categoryUrl, int languageID);


}
