package com.madikhan.estore.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private String defaultLanguage;
    private Integer defaultLanguageID;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        defaultLanguage = filterConfig.getInitParameter("defaultLanguage");
        defaultLanguageID = Integer.valueOf(filterConfig.getInitParameter("defaultLanguageID"));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession(true);

        String language = (String) session.getAttribute("language");
        Integer languageID = (Integer) session.getAttribute("languageID");

        if (language == null) {
            session.setAttribute("language", defaultLanguage);
            session.setAttribute("languageID", defaultLanguageID);
        } else {
            session.setAttribute("language", language);
            session.setAttribute("languageID", languageID);
        }

        response.setCharacterEncoding("UTF-8");
        chain.doFilter(request,response);
    }

}
