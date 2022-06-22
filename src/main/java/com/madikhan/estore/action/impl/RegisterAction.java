package com.madikhan.estore.action.impl;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.model.User;
import com.madikhan.estore.service.UserService;
import com.madikhan.estore.service.impl.UserServiceImpl;
import com.madikhan.estore.validator.AuthenticationValidator;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class RegisterAction implements Action {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException,
            SQLException, ServletException {

        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;

        if (request.getParameter(PASSWORD).equals(request.getParameter(CONFIRM_PASSWORD))) {

            if (userService.isEmailExists(request.getParameter(EMAIL))) {
                request.setAttribute(EMAIL_IS_WRONG, EMAIL_EXISTS_MESSAGE);
                request.setAttribute(EMAIL, request.getParameter(EMAIL));
                dispatcher = request.getRequestDispatcher(REGISTRATION_PAGE_PATH);
                dispatcher.forward(request, response);
            } else if (!AuthenticationValidator.isEmailValid(request.getParameter(EMAIL))) {
                request.setAttribute(EMAIL_IS_WRONG, EMAIL_WRONG_FORMAT_MESSAGE);
                request.setAttribute(EMAIL, request.getParameter(EMAIL));
                dispatcher = request.getRequestDispatcher(REGISTRATION_PAGE_PATH);
                dispatcher.forward(request, response);
            } else if (!AuthenticationValidator.isPasswordValid(request.getParameter(PASSWORD))) {
                request.setAttribute(PASSWORD_IS_WRONG, PASSWORD_IS_WRONG_MESSAGE);
                request.setAttribute(EMAIL, request.getParameter(EMAIL));
                dispatcher = request.getRequestDispatcher(REGISTRATION_PAGE_PATH);
                dispatcher.forward(request, response);
            } else {
                User user = fillUser(request);
                userService.save(user);
                session.setAttribute(USER_ID_ATTRIBUTE, user.getId());
                session.setAttribute(USER_NAME_ATTRIBUTE, user.getName());
                session.setAttribute(USER_EMAIL_ATTRIBUTE, user.getEmail());
                session.setAttribute(IS_ADMIN, user.getIsAdmin());

                dispatcher = request.getRequestDispatcher(HOME_PAGE_PATH);
                dispatcher.forward(request, response);
            }
        } else {
            request.setAttribute(PASSWORD_IS_WRONG, PASSWORD_DOES_NOT_MATCH_MESSAGE);
            request.setAttribute(EMAIL, request.getParameter(EMAIL));
            dispatcher = request.getRequestDispatcher(REGISTRATION_PAGE_PATH);
            dispatcher.forward(request, response);
        }
    }

    private User fillUser(HttpServletRequest request) {
        User user = new User();
        user.setName(request.getParameter(NAME));
        user.setEmail(request.getParameter(EMAIL));
        String password = request.getParameter(PASSWORD);
        String encodedPassword = DigestUtils.md5Hex(password);
        user.setPassword(encodedPassword);
        user.setIsAdmin(USER_ROLE);
        return user;
    }
}
