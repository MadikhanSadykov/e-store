package com.madikhan.estore.dao.impl;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.dao.DAO;
import com.madikhan.estore.jdbc.ConnectionPool;
import com.madikhan.estore.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDAO implements DAO<User> {

    private static final String INSERT_USER = "INSERT INTO user " +
            "(name, surname, email, phone_number, address, password, is_admin) VALUES (?,?,?,?,?,?,?)";
    private static final String SELECT_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM user WHERE email = ? AND password = ?";

    private ConnectionPool connectionPool;
    private Connection connection;
    private static UserDAO instance;

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }



    @Override
    public void create(User user) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhoneNumber());
            statement.setString(5, user.getAddress());
            statement.setString(6, user.getPassword());
            statement.setInt(7, USER_ROLE);
            statement.executeUpdate();

        } finally {
            connectionPool.bringBackConnection(connection);
        }
    }

    @Override
    public void update(Long id, User object) {

    }

    @Override
    public User getByID(Long id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
