package com.madikhan.estore.action.impl.ajax;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.util.RoutingUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


public class LoadMoreProductsByCategoryAction implements Action {

    private static final int SUBSTRING_INDEX = "/ajax/html/more/products".length();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
        String categoryUrl = request.getRequestURI().substring(SUBSTRING_INDEX);
        RoutingUtil.forwardToFragment("product-list.jsp", request, response);
    }
}
