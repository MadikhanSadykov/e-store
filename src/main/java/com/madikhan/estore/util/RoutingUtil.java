package com.madikhan.estore.util;

import org.json.JSONObject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class RoutingUtil {

    public static void forwardToFragment(String jspFragment, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/JSP/fragment/" + jspFragment).forward(request, response);
    }

    public static void forwardToPage(String jspPage, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("currentPage", "page/" + jspPage);
        request.getRequestDispatcher("/WEB-INF/JSP/page-template.jsp").forward(request, response);
    }

    public static void sendHTMLFragment(String text, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println(text);
        response.getWriter().close();
    }

    public static void sendJSON(JSONObject jsonObject, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.getWriter().println(jsonObject.toString());
        response.getWriter().close();
    }

    public static void redirect(String url, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(url);
    }
}