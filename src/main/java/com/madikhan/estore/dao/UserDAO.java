package com.madikhan.estore.dao;

import com.madikhan.estore.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO extends DAO<User> {

    User getUserByEmailAndPassword(String email, String password) throws SQLException;

    void updateUserPassword(Long userId, String newPassword) throws SQLException;

    void updateUserRole(Long userId, Boolean isAdmin) throws SQLException;

    boolean isEmailExist(String email) throws SQLException;

    String getPasswordByUserID(Long id) throws SQLException;

    void updateUserAdmin(Boolean isAdmin, Long userID);

    Long getCountUsers();

    List<User> listAllUsersWithLimit(Long page, Integer limit);

}
