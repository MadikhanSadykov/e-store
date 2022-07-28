package com.madikhan.estore.dao;

import com.madikhan.estore.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO extends DAO<User> {

    void create(User user) throws SQLException;

    void delete(Long userID) throws SQLException;

    User getUserByEmailAndPassword(String email, String password) throws SQLException;

    boolean isEmailExist(String email) throws SQLException;

    String getPasswordByUserID(Long id) throws SQLException;

    void updateUserAdmin(Boolean isAdmin, Long userID);

    Long getCountUsers();

    List<User> listAllUsersWithLimit(Long page, Integer limit);

}
