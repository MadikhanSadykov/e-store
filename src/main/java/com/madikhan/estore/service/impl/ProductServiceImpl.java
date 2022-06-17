package com.madikhan.estore.service.impl;


import com.madikhan.estore.dao.ProductDAO;
import com.madikhan.estore.dao.impl.ProductDAOImpl;
import com.madikhan.estore.model.Product;
import com.madikhan.estore.model.search.SearchForm;
import com.madikhan.estore.service.ProductService;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private static ProductService productService;
    private ProductDAO productDAO = ProductDAOImpl.getInstance();

    public static ProductService getInstance() {
        if (productService == null)
            productService = new ProductServiceImpl();
        return productService;
    }

    @Override
    public List<Product> listAllProducts(Long page, int limitOfProducts, int languageID) throws SQLException {
        return productDAO.getProductsWithLimit(page, limitOfProducts, languageID);
    }

    @Override
    public List<Product> listProductsByCategory(String categoryUrl, Long page, int maxAmountOfProducts, int languageID) {
        return productDAO.getProductsByCategoryWithLimit(categoryUrl, page, maxAmountOfProducts, languageID);
    }

    @Override
    public Long countAllProducts() {
        return productDAO.getCountAllProducts();
    }

    @Override
    public Long countAllProductsByCategory(String categoryUrl, int languageID) {
        return productDAO.getCountAllProductsByCategory(categoryUrl, languageID);
    }

    @Override
    public List<Product> listProductsBySearchForm(SearchForm searchForm, Long page, int maxAmountOfProducts, int languageID) {
        return productDAO.getProductsBySearchForm(searchForm, page, maxAmountOfProducts, languageID);
    }

    @Override
    public Long countAllProductsBySearchForm(SearchForm searchForm, int languageID) {
        return productDAO.getCountAllProductsBySearchForm(searchForm, languageID);
    }
}
