package com.madikhan.estore.dao.impl;

import com.madikhan.estore.dao.OrderItemDAO;
import com.madikhan.estore.exception.InternalServerErrorException;
import com.madikhan.estore.jdbc.ConnectionPool;
import com.madikhan.estore.model.*;
import com.madikhan.estore.service.ProductService;
import com.madikhan.estore.service.impl.ProductServiceImpl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAOImpl implements OrderItemDAO {

    private static final String INSERT_ORDER_ITEM = "INSERT INTO order_item(product_count, cost, id_order, id_product) " +
            "VALUES (?,?,?,?)";
    private static final String SELECT_ALL_BY_ORDER_ID = "SELECT * FROM order_item WHERE id_order = ?";
    private static final String SELECT_COUNT_BY_PRODUCT_ID = "SELECT count(*) as count FROM order_item WHERE id_product = ?";

    private static OrderItemDAO instance;
    private ConnectionPool connectionPool;
    private Connection connection;
    private final ProductService productService = ProductServiceImpl.getInstance();

    private OrderItemDAOImpl() {
        super();
    }

    private void setResultSetToOrderItem(OrderItem orderItem, ResultSet resultSet) throws SQLException {
        orderItem.setId(resultSet.getLong("id"));
        orderItem.setProductCount(resultSet.getInt("product_count"));
        orderItem.setCost(resultSet.getBigDecimal("cost"));
        orderItem.setIdOrder(resultSet.getLong("id_order"));
        orderItem.setIdProduct(resultSet.getLong("id_product"));
        Product product = productService.getProductByID(resultSet.getLong("id_product"), 1);
        orderItem.setProduct(product);
    }

    public static OrderItemDAO getInstance(){
        if (instance == null)
            instance = new OrderItemDAOImpl();
        return instance;
    }

    @Override
    public void addOrderItem(Long orderID, CartItem cartItem) {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_ITEM)){
            preparedStatement.setInt(1, cartItem.getProductCount());
            preparedStatement.setBigDecimal(2, cartItem.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getProductCount())));
            preparedStatement.setLong(3, orderID);
            preparedStatement.setLong(4, cartItem.getProduct().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
    }

    @Override
    public List<OrderItem> getAllByOrderID(Long orderID) {
        List<OrderItem> orderItems = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BY_ORDER_ID)){
            preparedStatement.setLong(1, orderID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OrderItem orderItem = new OrderItem();
                setResultSetToOrderItem(orderItem, resultSet);
                orderItems.add(orderItem);
            }
        } catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
        return orderItems;
    }

    @Override
    public Long countOrderItemByProductID(Long productID) {
        Long count = null;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COUNT_BY_PRODUCT_ID)){
            preparedStatement.setLong(1, productID);
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
    public OrderItem getByID(Long id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<OrderItem> getAll() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Long id, OrderItem object) throws SQLException {
        throw new UnsupportedOperationException();
    }

}
