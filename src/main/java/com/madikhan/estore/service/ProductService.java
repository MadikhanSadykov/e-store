package com.madikhan.estore.service;

import com.madikhan.estore.model.Category;
import com.madikhan.estore.model.Producer;
import com.madikhan.estore.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {

    List<Product> listAllProducts(int page, int maxAmountOfProducts) throws SQLException;

    List<Product> listProductsByCategory(String categoryUrl, int page, int maxAmountOfProducts);

}
