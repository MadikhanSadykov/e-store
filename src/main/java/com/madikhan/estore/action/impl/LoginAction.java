package com.madikhan.estore.action.impl;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.model.User;
import com.madikhan.estore.service.UserService;
import com.madikhan.estore.service.impl.UserServiceImpl;
import com.madikhan.estore.util.RoutingUtil;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class LoginAction implements Action {

    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;

        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String encodedPassword = DigestUtils.md5Hex(password);

        boolean isMailExists = userService.isEmailExists(email);

        if (!isMailExists) {
            request.setAttribute(USER_EMAIL_ATTRIBUTE, email);
            request.setAttribute(EMAIL_AUTH_ERROR, EMAIL_IS_WRONG_MESSAGE);
            RoutingUtil.forwardToPage("login.jsp", request, response);
        } else {
            User user = userService.getUserByEmailAndPassword(email, encodedPassword);

            if (user != null) {
                session.setAttribute(USER_ID_ATTRIBUTE, user.getId());
                session.setAttribute(USER_NAME_ATTRIBUTE, user.getName());
                session.setAttribute(USER_SURNAME_ATTRIBUTE, user.getSurname());
                session.setAttribute(USER_EMAIL_ATTRIBUTE, user.getEmail());
                session.setAttribute(USER_PHONE_NUMBER_ATTRIBUTE, user.getPhoneNumber());
                session.setAttribute(USER_ADDRESS_ATTRIBUTE, user.getAddress());
                session.setAttribute(IS_ADMIN, user.getIsAdmin());
                dispatcher = request.getRequestDispatcher(HOME_PAGE_PATH);
                dispatcher.forward(request, response);
            } else {
                request.setAttribute(USER_EMAIL_ATTRIBUTE, email);
                request.setAttribute(PASSWORD_AUTH_ERROR, PASSWORD_IS_WRONG_MESSAGE);
                RoutingUtil.forwardToPage("login.jsp", request, response);
            }
        }
    }

}
