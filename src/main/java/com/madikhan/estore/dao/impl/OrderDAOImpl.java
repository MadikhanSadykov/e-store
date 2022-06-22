package com.madikhan.estore.dao.impl;

import com.madikhan.estore.dao.OrderDAO;
import com.madikhan.estore.dao.OrderItemDAO;
import com.madikhan.estore.exception.InternalServerErrorException;
import com.madikhan.estore.exception.ResourceNotFoundException;
import com.madikhan.estore.jdbc.ConnectionPool;
import com.madikhan.estore.model.Cart;
import com.madikhan.estore.model.CartItem;
import com.madikhan.estore.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    private static final String INSERT_INTO_ORDER = "INSERT INTO \"order\" (total_cost, created, id_user, id_status) " +
            "VALUES (?, ?, ?, ?)";
    private static final String SELECT_ORDER_BY_ID = "SELECT o.*, s.name as status FROM \"order\" o, status s WHERE o.id = ? " +
            "AND o.id_status = s.id AND s.id_language = ?";
    private static final String SELECT_ALL_ORDERS_BY_USER_ID = "SELECT o.*, s.name as status FROM \"order\" o, status s WHERE o.id_user = ? " +
            "AND o.id_status = s.id AND s.id_language = ? ORDER BY o.id LIMIT ? OFFSET ?";
    private static final String SELECT_COUNT_ALL_ORDERS_BY_USER_ID = "SELECT count(*) as count FROM \"order\" WHERE id_user = ?";
    private static final String UPDATE_STATUS_BY_ID = "UPDATE \"order\" SET id_status = ?, finished = ? WHERE id = ? ";
    private static final String SELECT_ALL_WITH_LIMIT = "SELECT o.*, s.name as status FROM \"order\" o, status s WHERE " +
            " o.id_status = s.id AND s.id_language = ? ORDER BY o.id LIMIT ? OFFSET ?";
    private static final String SELECT_COUNT_ALL_ORDERS = "SELECT count(*) as count FROM \"order\"";
    private ConnectionPool connectionPool;
    private Connection connection;
    private static OrderDAO instance;
    OrderItemDAO orderItemDAO = OrderItemDAOImpl.getInstance();

    private OrderDAOImpl() {
        super();
    }

    private void setResultSetToOrder(Order order, ResultSet resultSet) throws SQLException {
        order.setId(resultSet.getLong("id"));
        order.setTotalCost(resultSet.getBigDecimal("total_cost"));
        order.setCreated(resultSet.getTimestamp("created"));
        order.setFinished(resultSet.getTimestamp("finished"));
        order.setIdUser(resultSet.getLong("id_user"));
        order.setIdStatus(resultSet.getInt("id_status"));
        order.setStatus(resultSet.getString("status"));
    }

    public static OrderDAO getInstance() {
        if (instance == null)
            instance = new OrderDAOImpl();
        return instance;
    }

    @Override
    public Long makeOrder(Cart cart, Long userID) {
        Long orderID = null;
        if (cart == null || cart.getItems().isEmpty()) {
            throw new InternalServerErrorException("Cart is null or empty");
        }
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_ORDER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setBigDecimal(1, cart.getTotalCost());
            preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setLong(3, userID);
            preparedStatement.setInt(4, 1);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                orderID = resultSet.getLong("id");
                for (CartItem cartItem : cart.getItems()) {
                    orderItemDAO.addOrderItem(orderID, cartItem);
                }
            }
        } catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
        return orderID;
    }

    @Override
    public Order getByID(Long orderID, Integer languageID) {
        Order order = null;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_BY_ID)){
            preparedStatement.setLong(1, orderID);
            preparedStatement.setInt(2, languageID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                order = new Order();
                setResultSetToOrder(order, resultSet);
                order.setOrderItems(orderItemDAO.getAllByOrderID(orderID));
            } else {
                throw new ResourceNotFoundException("Order not found by id: " + orderID);
            }
        } catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
        return order;
    }

    @Override
    public List<Order> getAllOrdersByUserID(Long userID, Long page, Integer limit, Integer languageID) {
        List<Order> orders = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        Long offset = (page - 1) * limit;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDERS_BY_USER_ID)){
            preparedStatement.setLong(1, userID);
            preparedStatement.setInt(2, languageID);
            preparedStatement.setInt(3, limit);
            preparedStatement.setLong(4, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                setResultSetToOrder(order, resultSet);
                orders.add(order);
            }
        } catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
        return orders;
    }

    @Override
    public Long getCountOrdersByUserID(Long userID) {
        Long count = null;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COUNT_ALL_ORDERS_BY_USER_ID)){
            preparedStatement.setLong(1, userID);
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
    public void updateStatus(Long orderID, Integer statusID) {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STATUS_BY_ID)){
            preparedStatement.setInt(1, statusID);
            preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setLong(3, orderID);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
    }

    @Override
    public List<Order> listAllOrdersWithLimit(Long page, Integer limit, Integer languageID) {
        List<Order> orders = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        Long offset = (page - 1) * limit;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WITH_LIMIT)){
            preparedStatement.setInt(1, languageID);
            preparedStatement.setInt(2, limit);
            preparedStatement.setLong(3, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                setResultSetToOrder(order, resultSet);
                orders.add(order);
            }
        } catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
        return orders;
    }

    @Override
    public Long countAllOrders() {
        Long count = null;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COUNT_ALL_ORDERS)){
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
    public List<Order> getAll() throws SQLException {
        return null;
    }

    @Override
    public void update(Long id, Order object) throws SQLException {

    }

    @Override
    public void delete(Long id) throws SQLException {

    }

    @Override
    public void create(Order object) throws SQLException {

    }

    @Override
    public Order getByID(Long orderID) {
        return null;
    }
}
