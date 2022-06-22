package com.madikhan.estore.filter;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.service.CategoryService;
import com.madikhan.estore.service.LanguageService;
import com.madikhan.estore.service.ProducerService;
import com.madikhan.estore.service.impl.CategoryServiceImpl;
import com.madikhan.estore.service.impl.LanguageServiceImpl;
import com.madikhan.estore.service.impl.ProducerServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class CategoryProducerFilter extends AbstractFilter{
    private CategoryService categoryService;
    private ProducerService producerService;
    private LanguageService languageService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        categoryService = CategoryServiceImpl.getInstance();
        producerService = ProducerServiceImpl.getInstance();
        languageService = LanguageServiceImpl.getInstance();
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException, SQLException {

        HttpSession session = request.getSession(true);
        Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);

        request.setAttribute("languageList", languageService.listAllLanguages());
        request.setAttribute(CATEGORY_LIST, categoryService.listAllCategories(languageID));
        request.setAttribute(PRODUCER_LIST, producerService.listAllProducers());
        chain.doFilter(request, response);
    }
}
