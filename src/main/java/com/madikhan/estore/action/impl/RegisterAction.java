package com.madikhan.estore.action.impl;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.dao.impl.UserDAO;
import com.madikhan.estore.model.User;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class RegisterAction implements Action {

    private UserDAO userDAO = UserDAO.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {

        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;

        User user = fillUser(request);
        userDAO.create(user);
        session.setAttribute(USER_ID, user.getId());
        session.setAttribute(EMAIL, user.getEmail());
        session.setAttribute(ROLE_ID, user.getIsAdmin());
        session.setAttribute(LOGIN, user);
        dispatcher = request.getRequestDispatcher(HOME_PAGE_PATH);
        dispatcher.forward(request, response);

    }

    private void checkLogin(){

    }

    private User fillUser(HttpServletRequest request) {
        User user = new User();

        user.setName(request.getParameter(NAME));
        user.setSurname(request.getParameter(SURNAME));
        user.setEmail(request.getParameter(EMAIL));
        user.setPhoneNumber(request.getParameter(PHONE_NUMBER));
        user.setAddress(request.getParameter(ADDRESS));
        String password = request.getParameter(PASSWORD);
        String encodedPassword = DigestUtils.md5Hex(password);
        user.setPassword(encodedPassword);
        user.setIsAdmin(USER_ROLE);
        return user;
    }
}
