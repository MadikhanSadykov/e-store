package com.madikhan.estore.action.impl;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.exception.AccessDeniedException;
import com.madikhan.estore.model.User;
import com.madikhan.estore.service.UserService;
import com.madikhan.estore.service.impl.UserServiceImpl;
import com.madikhan.estore.util.RoutingUtil;
import com.madikhan.estore.validator.AdminValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminUsersPageAction implements Action {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException,
            SQLException, ServletException {

        if (AdminValidator.isAdminRole(request)) {
            List<User> users = userService.listAll();
            request.setAttribute(USERS, users);
            Long totalCount = userService.countAllUsers();
            request.setAttribute(PAGE_COUNT, getPageCount(totalCount, MAX_USERS_PER_SHOPPING_CART ));
            RoutingUtil.forwardToPage(ADMIN_USERS_JSP, request, response);
        } else {
            throw new AccessDeniedException(YOU_ARE_NOT_ADMIN_MESSAGE);
        }
    }
}
