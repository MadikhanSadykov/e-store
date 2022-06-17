package com.madikhan.estore.dao;



import com.madikhan.estore.model.Product;
import com.madikhan.estore.model.search.SearchForm;

import java.util.List;

public interface ProductDAO extends DAO<Product> {

    List<Product> getProductsWithLimit(Long page, int limitOfProducts, int languageID);

    List<Product> getProductsByCategoryWithLimit(String categoryUrl, Long page, int limitOfProducts, int languageID);

    List<Product> getProductsBySearchForm(SearchForm searchForm, Long page, int limitOfProducts, int languageID);

    Long getCountAllProductsBySearchForm(SearchForm searchForm, int languageID);

    Long getCountAllProducts();

    Long getCountAllProductsByCategory(String categoryUrl, int languageID);
}
