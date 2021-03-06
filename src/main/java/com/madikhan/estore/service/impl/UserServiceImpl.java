package com.madikhan.estore.service.impl;

import com.madikhan.estore.dao.UserDAO;
import com.madikhan.estore.dao.impl.UserDAOImpl;
import com.madikhan.estore.model.User;
import com.madikhan.estore.service.UserService;

import java.sql.SQLException;
import java.util.List;

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
    public void update(Long id, User user) throws SQLException {
        userDAO.update(id, user);
    }

    @Override
    public boolean isEmailExists(String email) throws SQLException {
        return userDAO.isEmailExist(email);
    }

    @Override
    public String getPasswordByUserID(Long id) throws SQLException {
        return userDAO.getPasswordByUserID(id);
    }

    @Override
    public List<User> listAll() throws SQLException {
        return userDAO.getAll();
    }

    @Override
    public void updateUserAdmin(Boolean isAdmin, Long userID) {
        userDAO.updateUserAdmin(isAdmin, userID);
    }

    @Override
    public void delete(Long userID) throws SQLException {
        userDAO.delete(userID);
    }

    @Override
    public Long countAllUsers() {
        return userDAO.getCountUsers();
    }

    @Override
    public List<User> listAllUsersWithLimit(Long page, Integer limit) {
        return userDAO.listAllUsersWithLimit(page, limit);
    }
}
