package com.madikhan.estore.service;

import com.madikhan.estore.model.User;

import java.sql.SQLException;

public interface UserService {


    User getUserByEmailAndPassword(String email, String password) throws SQLException;

    void save(User user) throws SQLException;

    boolean isEmailExists(String email) throws SQLException;


}
