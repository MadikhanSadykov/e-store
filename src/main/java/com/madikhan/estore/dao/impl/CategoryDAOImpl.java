package com.madikhan.estore.dao.impl;

import com.madikhan.estore.dao.CategoryDAO;
import com.madikhan.estore.exception.InternalServerErrorException;
import com.madikhan.estore.jdbc.ConnectionPool;
import com.madikhan.estore.model.Category;
import com.madikhan.estore.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {

    private ConnectionPool connectionPool;
    private Connection connection;
    private static CategoryDAO instance;

    private static final String SELECT_ALL_CATEGORIES = "SELECT * FROM category WHERE id_language = ? ORDER BY name";

    private CategoryDAOImpl() {
        super();
    }

    private void setResultSetToCategory(Category category, ResultSet resultSet) throws SQLException {
        category.setId(resultSet.getLong("id"));
        category.setName(resultSet.getString("name"));
        category.setUrl(resultSet.getString("url"));
        category.setProductCount(resultSet.getInt("product_count"));
        category.setIdLanguage(1);
    }

    public static CategoryDAO getInstance() {
        if (instance == null)
            instance = new CategoryDAOImpl();
        return instance;
    }

    @Override
    public void create(Category object) throws SQLException {

    }

    @Override
    public Category getByID(Long id) throws SQLException {
        return null;
    }

    @Override
    public List<Category> getAllByLanguage(Integer languageID) throws SQLException {
        List<Category> categories = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CATEGORIES)) {
            preparedStatement.setInt(1, languageID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                setResultSetToCategory(category, resultSet);
                categories.add(category);
            }
        } catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
        return categories;
    }

    @Override
    public List<Category> getAll() throws SQLException {
        return null;
    }

    @Override
    public void update(Long id, Category object) throws SQLException {

    }

    @Override
    public void delete(Long id) throws SQLException {

    }
}
