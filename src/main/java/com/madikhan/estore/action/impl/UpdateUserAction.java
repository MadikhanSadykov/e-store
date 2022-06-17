package com.madikhan.estore.action.impl;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.model.User;
import com.madikhan.estore.service.UserService;
import com.madikhan.estore.service.impl.UserServiceImpl;
import com.madikhan.estore.util.RoutingUtil;
import com.madikhan.estore.validator.AuthenticationValidator;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static com.madikhan.estore.constants.NamesConstants.*;

public class UpdateUserAction implements Action {

    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher;

        Long userID = Long.valueOf(request.getParameter(USER_ID_ATTRIBUTE));
        String email = request.getParameter(EMAIL);
        String newPassword = request.getParameter(NEW_PASSWORD);
        String confirmNewPassword = request.getParameter(CONFIRM_NEW_PASSWORD);
        String password = request.getParameter(PASSWORD);
        String encodedPassword = DigestUtils.md5Hex(password);
        String encodedNewPassword = DigestUtils.md5Hex(newPassword);
        String encodedPasswordInDB = userService.getPasswordByUserID(userID);

        if (encodedPassword.equals(encodedPasswordInDB)) {

            if ( (!newPassword.equals("") && !confirmNewPassword.equals("")) && newPassword.equals(confirmNewPassword)) {
                if (!AuthenticationValidator.isPasswordValid(request.getParameter(NEW_PASSWORD))) {
                    request.setAttribute(NEW_PASSWORD_IS_WRONG, PASSWORD_WRONG_FORMAT_MESSAGE);
                    request.setAttribute(EMAIL, request.getParameter(EMAIL));
                    dispatcher = request.getRequestDispatcher(CHANGE_PROFILE_PAGE);
                    dispatcher.forward(request, response);
                    return;
                }
                encodedPassword = encodedNewPassword;
            }

            if ( (!newPassword.equals("") && !confirmNewPassword.equals("")) && !newPassword.equals(confirmNewPassword)){
                request.setAttribute(CONFIRM_NEW_PASSWORD_IS_WRONG, CONFIRM_NEW_PASSWORD_IS_WRONG_MESSAGE);
                dispatcher = request.getRequestDispatcher(CHANGE_PROFILE_PAGE);
                dispatcher.forward(request, response);
                return;
            }

            if (!newPassword.equals("") && confirmNewPassword.equals("")) {
                request.setAttribute(CONFIRM_NEW_PASSWORD_IS_WRONG, CONFIRM_NEW_PASSWORD_IS_EMPTY_MESSAGE);
                dispatcher = request.getRequestDispatcher(CHANGE_PROFILE_PAGE);
                dispatcher.forward(request, response);
                return;
            }
            if (newPassword.equals("") && !confirmNewPassword.equals("")) {
                request.setAttribute(NEW_PASSWORD_IS_WRONG, NEW_PASSWORD_IS_EMPTY_MESSAGE);
                dispatcher = request.getRequestDispatcher(CHANGE_PROFILE_PAGE);
                dispatcher.forward(request, response);
                return;
            }

            if (!AuthenticationValidator.isEmailValid(request.getParameter(EMAIL))){
                request.setAttribute(EMAIL_IS_WRONG, EMAIL_WRONG_FORMAT_MESSAGE);
                request.setAttribute(EMAIL, request.getParameter(EMAIL));
                dispatcher = request.getRequestDispatcher(CHANGE_PROFILE_PAGE);
                dispatcher.forward(request, response);
                return;
            }





            User user = fillUser(request, encodedPassword);
            userService.update(userID, user);
            session.setAttribute(USER_ID_ATTRIBUTE, user.getId());
            session.setAttribute(USER_NAME_ATTRIBUTE, user.getName());
            session.setAttribute(USER_SURNAME_ATTRIBUTE, user.getSurname());
            session.setAttribute(USER_EMAIL_ATTRIBUTE, user.getEmail());
            session.setAttribute(USER_PHONE_NUMBER_ATTRIBUTE, user.getPhoneNumber());
            session.setAttribute(USER_ADDRESS_ATTRIBUTE, user.getAddress());

            dispatcher = request.getRequestDispatcher(PROFILE_PAGE_PATH);
            dispatcher.forward(request, response);
        } else {
            request.setAttribute(PASSWORD_AUTH_ERROR, PASSWORD_IS_WRONG_MESSAGE);
            dispatcher = request.getRequestDispatcher(CHANGE_PROFILE_PAGE);
            dispatcher.forward(request, response);
        }



    }

    private User fillUser(HttpServletRequest request, String encodedPassword) {
        User user = new User();

        user.setId(Long.valueOf(request.getParameter(USER_ID_ATTRIBUTE)));
        user.setName(request.getParameter(NAME));
        if (!request.getParameter(SURNAME).equals("")) {
            user.setSurname(request.getParameter(SURNAME));
        } else {
            user.setSurname(null);
        }
        if (!request.getParameter(EMAIL).equals("")) {
            user.setEmail(request.getParameter(EMAIL));
        } else {
            user.setEmail(null);
        }
        if (!request.getParameter(PHONE_NUMBER).equals("")) {
            user.setPhoneNumber(request.getParameter(PHONE_NUMBER));
        } else {
            user.setPhoneNumber(null);
        }
        if (!request.getParameter(ADDRESS).equals("")) {
            user.setAddress(request.getParameter(ADDRESS));
        } else {
            user.setAddress(null);
        }
        user.setPassword(encodedPassword);
        user.setIsAdmin(USER_ROLE);
        return user;
    }
}
