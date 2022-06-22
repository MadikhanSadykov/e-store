package com.madikhan.estore.dao.impl;

import com.madikhan.estore.dao.LanguageDAO;
import com.madikhan.estore.exception.InternalServerErrorException;
import com.madikhan.estore.jdbc.ConnectionPool;
import com.madikhan.estore.model.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LanguageDAOImpl implements LanguageDAO {

    private static LanguageDAOImpl instance;
    private static final String SELECT_ALL_LANGUAGES = "SELECT * FROM language";
    private static final String SELECT_ID_BY_SHORT_NAME = "SELECT id FROM language WHERE short_name = ?";
    ConnectionPool connectionPool;
    Connection connection;

    private LanguageDAOImpl() {
        super();
    }

    public static LanguageDAO getInstance() {
        if (instance == null)
            instance = new LanguageDAOImpl();
        return instance;
    }

    @Override
    public Integer getLanguageID(String language) throws SQLException {
        Integer languageID = null;
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ID_BY_SHORT_NAME)) {
            statement.setString(1, language);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                languageID = resultSet.getInt("id");
            }
        } catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
        return languageID;
    }

    @Override
    public List<Language> getAll() {
        List<Language> languages = new ArrayList<>();
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_LANGUAGES)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Language language = new Language();
                language.setId(resultSet.getInt("id"));
                language.setName(resultSet.getString("name"));
                language.setShortName(resultSet.getString("short_name"));
                languages.add(language);
            }
        } catch (SQLException sqlException) {
            throw new InternalServerErrorException("Cannot execute SQL query: " + sqlException.getMessage(), sqlException);
        } finally {
            connectionPool.bringBackConnection(connection);
        }
        return languages;
    }

    @Override
    public void create(Language object) throws SQLException {

    }

    @Override
    public void update(Long id, Language object) {

    }

    @Override
    public Language getByID(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

}
