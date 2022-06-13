package com.madikhan.estore.dao;



import com.madikhan.estore.model.Product;

import java.util.List;

public interface ProductDAO extends DAO<Product> {

    List<Product> getProductsWithLimit(int page, int limitOfProducts);

    List<Product> getProductsByCategoryWithLimit(String categoryUrl, int page, int limitOfProducts);

}
