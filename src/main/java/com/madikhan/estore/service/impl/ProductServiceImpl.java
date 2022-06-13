package com.madikhan.estore.service.impl;


import com.madikhan.estore.dao.ProductDAO;
import com.madikhan.estore.dao.impl.ProductDAOImpl;
import com.madikhan.estore.model.Category;
import com.madikhan.estore.model.Producer;
import com.madikhan.estore.model.Product;
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
    public List<Product> listAllProducts(int page, int limitOfProducts) throws SQLException {
        return productDAO.getProductsWithLimit(page, limitOfProducts);
    }

    @Override
    public List<Product> listProductsByCategory(String categoryUrl, int page, int maxAmountOfProducts) {
        return productDAO.getProductsByCategoryWithLimit(categoryUrl, page, maxAmountOfProducts);
    }

}
