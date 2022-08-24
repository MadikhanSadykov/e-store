package com.madikhan.estore.action.impl;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.model.User;
import com.madikhan.estore.service.UserService;
import com.madikhan.estore.service.impl.UserServiceImpl;
import com.madikhan.estore.util.SessionUtil;
import com.madikhan.estore.validator.AuthenticationValidator;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class UpdateUserAction implements Action {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException,
            SQLException, ServletException {

        RequestDispatcher dispatcher;
        Long userID = Long.valueOf(request.getParameter(USER_ID_ATTRIBUTE));
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
                    setUserParamsToRequest(request);
                    dispatcher = request.getRequestDispatcher(CHANGE_PROFILE_PAGE);
                    dispatcher.forward(request, response);
                    return;
                }
                encodedPassword = encodedNewPassword;
            }

            if ( (!newPassword.equals("") && !confirmNewPassword.equals("")) && !newPassword.equals(confirmNewPassword)){
                request.setAttribute(CONFIRM_NEW_PASSWORD_IS_WRONG, CONFIRM_NEW_PASSWORD_IS_WRONG_MESSAGE);
                setUserParamsToRequest(request);
                dispatcher = request.getRequestDispatcher(CHANGE_PROFILE_PAGE);
                dispatcher.forward(request, response);
                return;
            }

            if (!newPassword.equals("") && confirmNewPassword.equals("")) {
                request.setAttribute(CONFIRM_NEW_PASSWORD_IS_WRONG, CONFIRM_NEW_PASSWORD_IS_EMPTY_MESSAGE);
                setUserParamsToRequest(request);
                dispatcher = request.getRequestDispatcher(CHANGE_PROFILE_PAGE);
                dispatcher.forward(request, response);
                return;
            }
            if (newPassword.equals("") && !confirmNewPassword.equals("")) {
                request.setAttribute(NEW_PASSWORD_IS_WRONG, NEW_PASSWORD_IS_EMPTY_MESSAGE);
                setUserParamsToRequest(request);
                dispatcher = request.getRequestDispatcher(CHANGE_PROFILE_PAGE);
                dispatcher.forward(request, response);
                return;
            }

            if (!AuthenticationValidator.isEmailValid(request.getParameter(EMAIL))) {
                request.setAttribute(EMAIL_IS_WRONG, EMAIL_WRONG_FORMAT_MESSAGE);
                setUserParamsToRequest(request);
                dispatcher = request.getRequestDispatcher(CHANGE_PROFILE_PAGE);
                dispatcher.forward(request, response);
                return;
            }

            if (!AuthenticationValidator.isPhoneNumberValid(request.getParameter(PHONE_NUMBER))
                    && !request.getParameter(PHONE_NUMBER).equals("")) {
                request.setAttribute(PHONE_NUMBER_WRONG_FORMAT, PHONE_NUMBER_WRONG_FORMAT_MESSAGE);
                setUserParamsToRequest(request);
                dispatcher = request.getRequestDispatcher(CHANGE_PROFILE_PAGE);
                dispatcher.forward(request, response);
                return;
            }

            User user = fillUser(request, encodedPassword);
            userService.update(userID, user);
            SessionUtil.clearCurrentUser(request,response);
            SessionUtil.setCurrentUser(request, user);

            dispatcher = request.getRequestDispatcher(PROFILE_PAGE_PATH);
            dispatcher.forward(request, response);
        } else {
            request.setAttribute(PASSWORD_AUTH_ERROR, PASSWORD_IS_WRONG_MESSAGE);
            request.setAttribute(NAME, request.getParameter(NAME));
            request.setAttribute(SURNAME, request.getParameter(SURNAME));
            request.setAttribute(EMAIL, request.getParameter(EMAIL));
            request.setAttribute(PHONE_NUMBER, request.getParameter(PHONE_NUMBER));
            request.setAttribute(ADDRESS, request.getParameter(ADDRESS));
            dispatcher = request.getRequestDispatcher(CHANGE_PROFILE_PAGE);
            dispatcher.forward(request, response);
        }
    }

    private User fillUser(HttpServletRequest request, String encodedPassword) {
        User user = new User();
        User currentUser = SessionUtil.getCurrentUser(request);
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
        user.setIsAdmin(currentUser.getIsAdmin());
        return user;
    }

    private void setUserParamsToRequest(HttpServletRequest request) {
        request.setAttribute(NAME, request.getParameter(NAME));
        request.setAttribute(SURNAME, request.getParameter(SURNAME));
        request.setAttribute(EMAIL, request.getParameter(EMAIL));
        request.setAttribute(PHONE_NUMBER, request.getParameter(PHONE_NUMBER));
        request.setAttribute(ADDRESS, request.getParameter(ADDRESS));
    }
}
