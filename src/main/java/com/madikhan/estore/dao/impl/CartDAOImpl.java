package com.madikhan.estore.dao.impl;

import com.madikhan.estore.dao.CartDAO;
import com.madikhan.estore.exception.InternalServerErrorException;
import com.madikhan.estore.jdbc.ConnectionPool;
import com.madikhan.estore.model.Cart;
import com.madikhan.estore.model.CartItem;
import com.madikhan.estore.model.Product;
import com.madikhan.estore.model.form.ProductForm;
import com.madikhan.estore.service.ProductService;
import com.madikhan.estore.service.impl.ProductServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDAOImpl implements CartDAO {

    private static final String INSERT_INTO_CART = "INSERT INTO cart (product_count, id_product, id_user) VALUES (?, ?, ?)";
    private static final String DELETE_BY_USER_ID_AND_PRODUCT_ID = "DELETE FROM cart WHERE id_user = ? AND id_product = ?";
    private static final String SELECT_BY_USER_ID_AND_PRODUCT_ID = "SELECT * FROM cart WHERE id_user = ? AND id_product = ?";
    private static final String UPDATE_PRODUCT_COUNT_BY_USER_ID_AND_PRODUCT_ID = "UPDATE cart SET product_count = ? " +
            "WHERE id_user = ? AND id_product = ?";
    private static final String SELECT_ALL_BY_USER_ID = "SELECT * FROM cart WHERE id_user = ?";

    private final ProductService productService = ProductServiceImpl.getInstance();
    private ConnectionPool connectionPool;
    private Connection connection;
    private static CartDAO instance;

    private CartDAOImpl() {
        super();
    }

    private void setResultSetToCartItem(CartItem cartItem, ResultSet resultSet) throws SQLException {
        cartItem.setId(resultSet.getLong("id"));
        cartItem.setProductCount(resultSet.getInt("product_count"));
        cartItem.setIdProduct(resultSet.getLong("id_product"));
        cartItem.setIdUser(resultSet.getLong("id_user"));
    }

    public static CartDAO getInstance() {
        if (instance == null)
            instance = new CartDAOImpl();
        return instance;
    }

    @Override
    public void addProductToCartFromDB(Long userID, Cart cart, Integer languageID) throws SQLException {
        List<CartItem> cartItems = getAllByUserID(userID);
        for (CartItem cartItem : cartItems) {
            Product product = productService.getProductByID(cartItem.getIdProduct(), languageID);
            cart.addProduct(product, cartItem.getProductCount(), true);
        }
    }

    @Override
    public void create(Long userID, ProductForm productForm) {
        CartItem cartItem = getByUserIDAndProductID(userID, productForm.getIdProduct());
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();

        if (cartItem == null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_CART)) {
                preparedStatement.setInt(1, productForm.getProductCount());
                preparedStatement.setLong(2, productForm.getIdProduct());
                preparedStatement.setLong(3, userID);
                preparedStatement.executeUpdate();
            } catch (SQLException sqlException) {
                throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
            } finally {
                connectionPool.bringBackConnection(connection);
            }
        } else {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_COUNT_BY_USER_ID_AND_PRODUCT_ID)) {
                preparedStatement.setInt(1, cartItem.getProductCount() + productForm.getProductCount());
                preparedStatement.setLong(2, userID);
                preparedStatement.setLong(3, cartItem.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException sqlException) {
                throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
            } finally {
                connectionPool.bringBackConnection(connection);
            }
        }

    }

    @Override
    public void delete(Long userID, Long productID, Integer productCount) {
        CartItem cartItem = getByUserIDAndProductID(userID, productID);
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();

        if (cartItem != null) {
            if (cartItem.getProductCount() > productCount) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_COUNT_BY_USER_ID_AND_PRODUCT_ID)) {
                    preparedStatement.setInt(1, cartItem.getProductCount() - productCount);
                    preparedStatement.setLong(2, userID);
                    preparedStatement.setLong(3, productID);
                    preparedStatement.executeUpdate();
                } catch (SQLException sqlException) {
                    throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
                } finally {
                    connectionPool.bringBackConnection(connection);
                }
            } else {
                try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_USER_ID_AND_PRODUCT_ID)) {
                    preparedStatement.setLong(1, userID);
                    preparedStatement.setLong(2, productID);
                    preparedStatement.executeUpdate();
                } catch (SQLException sqlException) {
                    throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
                } finally {
                    connectionPool.bringBackConnection(connection);
                }
            }
        }
    }

    @Override
    public void create(Cart cart) {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        for (CartItem cartItem : cart.getItems()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_CART)) {
                preparedStatement.setInt(1, cartItem.getProductCount());
                preparedStatement.setLong(2, cartItem.getProduct().getId());
                preparedStatement.setLong(3, cart.getIdUser());
                preparedStatement.executeQuery();
            } catch (SQLException sqlException) {
                throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
            } finally {
                connectionPool.bringBackConnection(connection);
            }
        }
    }

    @Override
    public CartItem getByUserIDAndProductID(Long userID, Long productID) {
        CartItem cartItem = null;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_USER_ID_AND_PRODUCT_ID)) {
            preparedStatement.setLong(1, userID);
            preparedStatement.setLong(2, productID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cartItem = new CartItem();
                setResultSetToCartItem(cartItem, resultSet);
            }
        } catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
        return cartItem;
    }

    @Override
    public List<CartItem> getAllByUserID(Long userID) {
        List<CartItem> cartItems = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BY_USER_ID)) {
            preparedStatement.setLong(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CartItem cartItem = new CartItem();
                setResultSetToCartItem(cartItem, resultSet);
                cartItems.add(cartItem);
            }
        } catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
        return cartItems;
    }

    @Override
    public Cart getByID(Long id) throws SQLException {
        return null;
    }

    @Override
    public List<Cart> getAll() throws SQLException {
        return null;
    }

    @Override
    public void update(Long id, Cart object) throws SQLException {

    }

    @Override
    public void delete(Long id) throws SQLException {

    }
}
