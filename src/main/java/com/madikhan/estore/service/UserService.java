package com.madikhan.estore.service;

import com.madikhan.estore.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {


    User getUserByEmailAndPassword(String email, String password) throws SQLException;

    void save(User user) throws SQLException;

    boolean isEmailExists(String email) throws SQLException;

    void update(Long id, User user) throws SQLException;

    String getPasswordByUserID(Long id) throws SQLException;

    List<User> listAll() throws SQLException;

    void updateUserAdmin(Boolean isAdmin, Long userID);

    void delete(Long userID) throws SQLException;

    Long countAllUsers();

    List<User> listAllUsersWithLimit(Long page, Integer limit);

}
