package com.madikhan.estore.dao.impl;

import com.madikhan.estore.dao.ProductDAO;
import com.madikhan.estore.exception.InternalServerErrorException;
import com.madikhan.estore.jdbc.ConnectionPool;
import com.madikhan.estore.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    private static final String INSERT_PRODUCT = "INSERT INTO product " +
            "(name, description, image_link, price, id_category, id_producer, id_language) VALUES (?,?,?,?,?,?,?)";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT p.*, c.name as category, pr.name as producer " +
            "FROM product p, producer pr, category c WHERE c.id = p.id_category AND pr.id = p.id_producer " +
            " AND p.id = ?";
    private static final String SELECT_ALL_PRODUCTS_WITH_LIMIT = "SELECT p.*, c.name as category, pr.name as producer " +
            "FROM product p, producer pr, category c WHERE c.id = p.id_category AND pr.id = p.id_producer " +
            "LIMIT ? OFFSET ? ";
    private static final String SELECT_PRODUCTS_BY_CATEGORY_WITH_LIMIT = "SELECT p.*, c.name as category, pr.name as producer " +
            "FROM product p, producer pr, category c WHERE c.url = ? AND c.id = p.id_category AND pr.id = p.id_producer " +
            "LIMIT ? OFFSET ? ";


    private ConnectionPool connectionPool;
    private Connection connection;
    private static ProductDAOImpl instance;

    private ProductDAOImpl() {
        super();
    }

    private void setResultSetToProduct(Product product, ResultSet resultSet) throws SQLException {
        product.setId(resultSet.getLong("id"));
        product.setName(resultSet.getString("name"));
        product.setDescription(resultSet.getString("description"));
        product.setImageLink(resultSet.getString("image_link"));
        product.setPrice(resultSet.getBigDecimal("price"));
        product.setCategory(resultSet.getString("category"));
        product.setProducer(resultSet.getString("producer"));
        product.setIdLanguage(resultSet.getInt("id_language"));
    }

    public static ProductDAOImpl getInstance() {
        if (instance == null)
            instance = new ProductDAOImpl();
        return instance;
    }

    @Override
    public void create(Product product) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT)){
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setString(3, product.getImageLink());
            preparedStatement.setBigDecimal(4, product.getPrice());
            preparedStatement.setString(5, product.getCategory());
            preparedStatement.setString(6, product.getProducer());
            preparedStatement.setInt(7, product.getIdLanguage());
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.bringBackConnection(connection);
        }
    }

    @Override
    public Product getByID(Long id) throws SQLException {
        Product product = null;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = new Product();
                setResultSetToProduct(product, resultSet);
            }
        } finally {
            connectionPool.bringBackConnection(connection);
        }
        return product;
    }

    @Override
    public List<Product> getProductsWithLimit(int page, int limitOfProducts) {
        List<Product> products = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        int offset = (page - 1) * limitOfProducts;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS_WITH_LIMIT)) {
            preparedStatement.setInt(1, limitOfProducts);
            preparedStatement.setInt(2, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                setResultSetToProduct(product, resultSet);
                products.add(product);
            }
        } catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
        return products;
    }

    @Override
    public List<Product> getProductsByCategoryWithLimit(String categoryUrl, int page, int limitOfProducts) {
        List<Product> products = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        int offset = (page - 1) * limitOfProducts;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS_WITH_LIMIT)) {
            preparedStatement.setString(1, categoryUrl);
            preparedStatement.setInt(2, limitOfProducts);
            preparedStatement.setInt(3, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                setResultSetToProduct(product, resultSet);
                products.add(product);
            }
        } catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
        return products;
    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public void update(Long id, Product object) {

    }

    @Override
    public void delete(Long id) {

    }
}
