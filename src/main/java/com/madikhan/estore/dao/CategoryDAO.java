package com.madikhan.estore.dao;

import com.madikhan.estore.model.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDAO extends DAO<Category> {

    List<Category> getAllByLanguage(Integer languageID) throws SQLException;



}
