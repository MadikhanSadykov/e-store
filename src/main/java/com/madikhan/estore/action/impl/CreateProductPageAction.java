package com.madikhan.estore.action.impl;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.exception.AccessDeniedException;
import com.madikhan.estore.model.User;
import com.madikhan.estore.util.RoutingUtil;
import com.madikhan.estore.util.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class CreateProductPageAction implements Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException,
            SQLException, ServletException {

        User user = SessionUtil.getCurrentUser(request);
        if (user.getIsAdmin()) {
            RoutingUtil.forwardToPage(CREATE_PRODUCT_JSP, request, response);
        } else {
            throw new AccessDeniedException(YOU_ARE_NOT_ADMIN_MESSAGE);
        }

    }
}
