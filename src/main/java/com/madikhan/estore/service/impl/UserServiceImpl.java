package com.madikhan.estore.service.impl;

import com.madikhan.estore.dao.UserDAO;
import com.madikhan.estore.dao.impl.UserDAOImpl;
import com.madikhan.estore.model.User;
import com.madikhan.estore.service.UserService;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    private static UserService userService;
    private UserDAO userDAO = UserDAOImpl.getInstance();

    private UserServiceImpl(){
        super();
    }

    public static UserService getInstance() {
        if (userService == null)
            userService = new UserServiceImpl();
        return userService;
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) throws SQLException {
        return userDAO.getUserByEmailAndPassword(email, password);
    }

    @Override
    public void save(User user) throws SQLException {
        userDAO.create(user);
    }

    @Override
    public boolean isEmailExists(String email) throws SQLException {
        return userDAO.isEmailExist(email);
    }
}
