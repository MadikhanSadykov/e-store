package com.madikhan.estore.filter;

import static com.madikhan.estore.constants.NamesConstants.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EncodingAndLanguageFilter implements Filter {

    private String defaultLanguage;
    private Integer defaultLanguageID;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        defaultLanguage = filterConfig.getInitParameter(DEFAULT_LANGUAGE);
        defaultLanguageID = Integer.valueOf(filterConfig.getInitParameter(DEFAULT_LANGUAGE_ID));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession(true);

        String language = (String) session.getAttribute(LANGUAGE);
        Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);

        if (language == null) {
            session.setAttribute(LANGUAGE, defaultLanguage);
            session.setAttribute(LANGUAGE_ID, defaultLanguageID);
        } else {
            session.setAttribute(LANGUAGE, language);
            session.setAttribute(LANGUAGE_ID, languageID);
        }

        response.setCharacterEncoding("UTF-8");
        chain.doFilter(request,response);
    }

}
