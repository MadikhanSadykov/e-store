package com.madikhan.estore.dao.impl;

import com.madikhan.estore.dao.ProducerDAO;
import com.madikhan.estore.exception.InternalServerErrorException;
import com.madikhan.estore.jdbc.ConnectionPool;
import com.madikhan.estore.model.Producer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProducerDAOImpl implements ProducerDAO {

    private ConnectionPool connectionPool;
    private Connection connection;
    private static ProducerDAO instance;

    private static final String SELECT_ALL_PRODUCERS = "SELECT * FROM producer ORDER BY name";
    private static final String SELECT_BY_ID = "SELECT * FROM producer WHERE id = ?";

    private ProducerDAOImpl(){
        super();
    }

    private Producer setResultSetToProducer(Producer producer, ResultSet resultSet) throws SQLException {
        producer.setId(resultSet.getLong("id"));
        producer.setName(resultSet.getString("name"));
        producer.setProductCount(resultSet.getInt("product_count"));
        return producer;
    }

    public static ProducerDAO getInstance() {
        if (instance == null)
            instance = new ProducerDAOImpl();
        return instance;
    }

    @Override
    public Producer getByID(Long producerID) throws SQLException {
        Producer producer = null;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)){
             preparedStatement.setLong(1, producerID);
             ResultSet resultSet = preparedStatement.executeQuery();
             if (resultSet.next()) {
                 producer = new Producer();
                 setResultSetToProducer(producer, resultSet);
             }
        } catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
        return producer;
    }

    @Override
    public List<Producer> getAll() throws SQLException {
        List<Producer> producers = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Producer producer = new Producer();
                setResultSetToProducer(producer, resultSet);
                producers.add(producer);
            }
        } catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
        return producers;
    }

    @Override
    public void update(Long id, Producer object) throws SQLException {

    }

    @Override
    public void delete(Long id) throws SQLException {

    }

    @Override
    public void create(Producer object) throws SQLException {

    }
}
