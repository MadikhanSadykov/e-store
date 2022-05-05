package com.madikhan.estore.controller;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.action.factory.ActionFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class EStoreController extends HttpServlet {

    public EStoreController() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String requestURI = request.getRequestURI();
        ActionFactory actionFactory = ActionFactory.getInstance();
        Action action = actionFactory.getAction(requestURI);
        try {
            action.execute(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
