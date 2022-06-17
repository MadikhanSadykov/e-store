package com.madikhan.estore.service;

import com.madikhan.estore.model.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {

    List<Category> listAllCategories(Integer languageID) throws SQLException;

}
