package com.madikhan.estore.dao.impl;

import com.madikhan.estore.dao.ProductDAO;
import com.madikhan.estore.exception.InternalServerErrorException;
import com.madikhan.estore.jdbc.ConnectionPool;
import com.madikhan.estore.model.Product;
import com.madikhan.estore.model.search.SearchForm;
import com.madikhan.estore.model.search.SearchQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    private static final String INSERT_PRODUCT = "INSERT INTO product " +
            "(name, description, image_link, price, id_category, id_producer) VALUES (?,?,?,?,?,?)";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT p.*, c.name as category, pr.name as producer " +
            "FROM product p, producer pr, category c WHERE c.id = p.id_category AND pr.id = p.id_producer " +
            " AND p.id = ?";
    private static final String SELECT_ALL_PRODUCTS_WITH_LIMIT = "SELECT p.*, c.name as category, pr.name as producer " +
            "FROM product p, producer pr, category c WHERE c.id = p.id_category AND c.id_language = ? AND pr.id = p.id_producer " +
            " ORDER BY p.id LIMIT ? OFFSET ?";
    private static final String SELECT_ALL_PRODUCTS_BY_SEARCH = "SELECT p.*, c.name as category, pr.name as producer " +
            "FROM product p, producer pr, category c WHERE (p.name ilike ? or p.description ilike ?) AND " +
            "c.id = p.id_category AND c.id_language = ? AND pr.id = p.id_producer " +
            " ORDER BY p.id LIMIT ? OFFSET ?";
    private static final String SELECT_PRODUCTS_BY_CATEGORY_WITH_LIMIT = "SELECT p.*, c.name as category, pr.name as producer " +
            "FROM product p, producer pr, category c WHERE c.id_language = ? AND c.url = ? AND c.id = p.id_category AND pr.id = p.id_producer " +
            "LIMIT ? OFFSET ? ";
    private static final String SELECT_COUNT_ALL_PRODUCTS = "SELECT count(*) AS count FROM product";
    private static final String SELECT_COUNT_ALL_PRODUCTS_BY_CATEGORY = "SELECT count(*) AS count FROM product p, category c " +
            "WHERE c.id = p.id_category AND c.url = ? AND c.id_language = ?";
    private static final String SELECT_COUNT_ALL_PRODUCTS_BY_SEARCH_FORM = "SELECT count(*) " +
            "FROM product p WHERE (p.name ilike ? or p.description ilike ?)";


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
    }

    private SearchQuery buildSearchQuery(String selectFields, SearchForm searchForm, int languageID) {
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(selectFields);
        sql.append(" FROM product p, category c, producer pr WHERE pr.id=p.id_producer AND c.id=p.id_category AND c.id_language = ? AND ( p.name ilike ? OR p.description ilike ? )" );
        params.add(languageID);
        params.add("%" + searchForm.getQuery() + "%");
        params.add("%" + searchForm.getQuery() + "%");
        populateSqlAndParams(sql, params, searchForm.getCategories(), " c.id = ? ");
        populateSqlAndParams(sql, params, searchForm.getProducers(), " pr.id = ? ");
        return new SearchQuery(sql, params);
    }

    private void populateSqlAndParams(StringBuilder sql, List<Object> params, List<Integer> list, String expression) {
        if (list != null && !list.isEmpty()) {
            sql.append(" AND (");
            for (int i = 0; i < list.size(); i++) {
                sql.append(expression);
                params.add(list.get(i));
                if (i != list.size() - 1) {
                    sql.append(" OR ");
                }
            }
            sql.append(")");
        }
    }

    private void populatePreparedStatement(PreparedStatement preparedStatement, Object... parameters) throws SQLException {
        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setObject(i + 1, parameters[i]);
            }
        }
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
    public List<Product> getProductsWithLimit(Long page, int limitOfProducts, int languageID) {
        List<Product> products = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        Long offset = (page - 1) * limitOfProducts;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS_WITH_LIMIT)) {
            preparedStatement.setInt(1, languageID);
            preparedStatement.setInt(2, limitOfProducts);
            preparedStatement.setLong(3, offset);
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
    public List<Product> getProductsByCategoryWithLimit(String categoryUrl, Long page, int limitOfProducts, int languageID) {
        List<Product> products = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        Long offset = (page - 1) * limitOfProducts;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCTS_BY_CATEGORY_WITH_LIMIT)) {
            preparedStatement.setInt(1, languageID);
            preparedStatement.setString(2, categoryUrl);
            preparedStatement.setInt(3, limitOfProducts);
            preparedStatement.setLong(4, offset);
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
    public Long getCountAllProducts() {
        Long count = null;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COUNT_ALL_PRODUCTS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getLong("count");
            }
        } catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
        return count;
    }

    @Override
    public Long getCountAllProductsByCategory(String categoryUrl, int languageID) {
        Long count = null;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COUNT_ALL_PRODUCTS_BY_CATEGORY)) {
            preparedStatement.setString(1, categoryUrl);
            preparedStatement.setInt(2, languageID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getLong("count");
            }
        } catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
        return count;
    }

    @Override
    public List<Product> getProductsBySearchForm(SearchForm searchForm, Long page, int limitOfProducts, int languageID) {
        List<Product> products = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        Long offset = (page - 1) * limitOfProducts;
        SearchQuery searchQuery = buildSearchQuery("p.*, c.name as category, pr.name as producer", searchForm, languageID);
        searchQuery.getSql().append(" ORDER BY p.id LIMIT ? OFFSET ?");
        searchQuery.getParams().add(limitOfProducts);
        searchQuery.getParams().add(offset);
        try (PreparedStatement preparedStatement = connection.prepareStatement(searchQuery.getSql().toString())) {
            System.out.println(searchQuery.getSql().toString());
            System.out.println(searchQuery.getSql().toString());
            System.out.println(searchQuery.getSql().toString());
            System.out.println(searchQuery.getSql().toString());
            System.out.println(searchQuery.getSql().toString());
            System.out.println(searchQuery.getSql().toString());

            populatePreparedStatement(preparedStatement, searchQuery.getParams().toArray());
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
    public Long getCountAllProductsBySearchForm(SearchForm searchForm, int languageID) {
        Long count = null;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        SearchQuery searchQuery = buildSearchQuery(" count(*) as count ", searchForm, languageID);
        try (PreparedStatement preparedStatement = connection.prepareStatement(searchQuery.getSql().toString())) {
            populatePreparedStatement(preparedStatement, searchQuery.getParams().toArray());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getLong("count");
            }
        } catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
        return count;
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
