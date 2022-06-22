package com.madikhan.estore.action.impl.ajax;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.model.User;
import com.madikhan.estore.service.UserService;
import com.madikhan.estore.service.impl.UserServiceImpl;
import com.madikhan.estore.util.RoutingUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LoadMoreUsersForAdmin implements Action {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException,
            ServletException, SQLException {

        List<User> users = userService.listAllUsersWithLimit(getPage(request), MAX_USERS_PER_SHOPPING_CART);
        request.setAttribute(USERS, users);
        RoutingUtil.forwardToFragment(ADMIN_USERS_TBODY_JSP, request, response);

    }
}
