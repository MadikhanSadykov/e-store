package com.madikhan.estore.action.impl;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.util.RoutingUtil;
import com.madikhan.estore.util.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ShoppingCartAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException,
            SQLException, ServletException {

        if (SessionUtil.isCurrentShoppingCartCreated(request)) {
            RoutingUtil.forwardToPage(SHOPPING_CART_JSP, request, response);
        } else {
            RoutingUtil.redirect(PRODUCTS_PATH, request, response);
        }

    }
}
