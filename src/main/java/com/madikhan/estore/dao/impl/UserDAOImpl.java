package com.madikhan.estore.dao.impl;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.dao.UserDAO;
import com.madikhan.estore.exception.InternalServerErrorException;
import com.madikhan.estore.jdbc.ConnectionPool;
import com.madikhan.estore.model.User;
import com.madikhan.estore.validator.AuthenticationValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static final String INSERT_USER = "INSERT INTO \"user\" " +
            "(name, email, password, is_admin) VALUES (?,?,?,?)";
    private static final String SELECT_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM \"user\" WHERE email = ? AND password = ?";
    private static final String SELECT_USER_BY_PHONE_NUMBER_AND_PASSWORD = "SELECT * FROM \"user\" WHERE phone_number = ? AND password = ?";
    private static final String SELECT_EMAIL_EXISTS = "SELECT EXISTS(SELECT 1 FROM \"user\" WHERE email = ? )";
    private static final String UPDATE_USER_BY_ID = "UPDATE \"user\" SET name = ?, surname = ?, email = ?, phone_number = ?, " +
            " address = ?, password = ?, is_admin = ? WHERE id = ?";
    private static final String SELECT_EMAIL_BY_ID = "SELECT email FROM \"user\" WHERE id = ?";
    private static final String SELECT_PASSWORD_BY_ID = "SELECT password FROM \"user\" WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM \"user\" ";
    private static final String SELECT_ALL_USERS_WITH_LIMIT = "SELECT * FROM \"user\" LIMIT ? OFFSET ?";
    private static final String UPDATE_USER_AS_ADMIN = "UPDATE \"user\" SET is_admin = ? WHERE id = ?";
    private static final String DELETE_BY_USER_ID = "DELETE FROM \"user\" WHERE id = ?";
    private static final String SELECT_COUNT_ALL_USERS = "SELECT count(*) as count FROM \"user\"";

    private ConnectionPool connectionPool;
    private Connection connection;
    private static UserDAOImpl instance;

    private UserDAOImpl(){
        super();
    }

    private void setResultSetToUser(User user, ResultSet resultSet) throws SQLException {
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setEmail(resultSet.getString("email"));
        user.setPhoneNumber(resultSet.getString("phone_number"));
        user.setAddress(resultSet.getString("address"));
        user.setPassword(resultSet.getString("password"));
        user.setIsAdmin(resultSet.getBoolean("is_admin"));
    }

    public static UserDAOImpl getInstance() {
        if (instance == null)
            instance = new UserDAOImpl();
        return instance;
    }

    @Override
    public void create(User user) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setBoolean(4, USER_ROLE);
            statement.executeUpdate();

        }  catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
    }

    @Override
    public void update(Long id, User user) {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER_BY_ID)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhoneNumber());
            statement.setString(5, user.getAddress());
            statement.setString(6, user.getPassword());
            statement.setBoolean(7, user.getIsAdmin());
            statement.setLong(8, id);
            statement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }

    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                setResultSetToUser(user, resultSet);
                users.add(user);
            }
        } catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
        return users;
    }

    @Override
    public void delete(Long userID) {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_USER_ID)){
            preparedStatement.setLong(1, userID);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        User user = null;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL_AND_PASSWORD)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String passwordInDataBase = resultSet.getString(PASSWORD);
                user = new User();
                if (AuthenticationValidator.isPasswordCorrect(password, passwordInDataBase)) {
                    setResultSetToUser(user, resultSet);
                }
            }
        } catch(SQLException sqlException){
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        }
        finally {
            connectionPool.bringBackConnection(connection);
        }
        return user;
    }

    @Override
    public String getPasswordByUserID(Long id) throws SQLException {
        String password = null;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_PASSWORD_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                password = resultSet.getString(PASSWORD);
            }
        } catch(SQLException sqlException){
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
        return password;
    }

    @Override
    public boolean isEmailExist(String email) {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMAIL_EXISTS)){
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                if (resultSet.getBoolean("exists"))
                return true;
            }
        } catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
        return false;
    }

    @Override
    public void updateUserAdmin(Boolean isAdmin, Long userID) {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_AS_ADMIN)){
            preparedStatement.setBoolean(1, isAdmin);
            preparedStatement.setLong(2, userID);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
    }

    @Override
    public Long getCountUsers() {
        Long count = null;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COUNT_ALL_USERS)){
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
    public List<User> listAllUsersWithLimit(Long page, Integer limit) {
        List<User> users = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        Long offset = (page - 1) * limit;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS_WITH_LIMIT)) {
            preparedStatement.setInt(1, limit);
            preparedStatement.setLong(2, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                setResultSetToUser(user, resultSet);
                users.add(user);
            }
        } catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
        return users;
    }

    @Override
    public User getByID(Long id) {
        throw new UnsupportedOperationException();
    }
}
