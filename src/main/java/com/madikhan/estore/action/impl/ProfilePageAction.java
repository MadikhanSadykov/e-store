package com.madikhan.estore.action.impl;

import static com.madikhan.estore.constants.NamesConstants.PROFILE_JSP;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.util.RoutingUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ProfilePageAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException,
            SQLException, ServletException {

        RoutingUtil.forwardToPage(PROFILE_JSP, request, response);

    }
}
