package com.madikhan.estore.action.impl;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.util.SessionUtil;

import static com.madikhan.estore.constants.NamesConstants.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class LogOutAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
        SessionUtil.clearCurrentUser(request, response);
        request.getSession().invalidate();
        response.sendRedirect(HOME_PAGE_PATH);
    }
}
