package com.madikhan.estore.service.impl;

import com.madikhan.estore.dao.CategoryDAO;
import com.madikhan.estore.dao.impl.CategoryDAOImpl;
import com.madikhan.estore.model.Category;
import com.madikhan.estore.service.CategoryService;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private static CategoryService instance;
    private CategoryDAO categoryDAO = CategoryDAOImpl.getInstance();

    private CategoryServiceImpl() {
        super();
    }

    public static CategoryService getInstance() {
        if (instance == null)
            instance = new CategoryServiceImpl();
        return instance;
    }

    @Override
    public List<Category> listAllCategories(Integer languageID) throws SQLException {
        return categoryDAO.getAllByLanguage(languageID);
    }

    @Override
    public Category getByID(Long categoryID) throws SQLException {
        return categoryDAO.getByID(categoryID);
    }
}
