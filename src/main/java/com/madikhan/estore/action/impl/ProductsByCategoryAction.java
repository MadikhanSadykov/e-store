package com.madikhan.estore.action.impl;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.model.Product;
import com.madikhan.estore.service.ProductService;
import com.madikhan.estore.service.impl.ProductServiceImpl;
import com.madikhan.estore.util.RoutingUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class ProductsByCategoryAction implements Action {

    private final ProductService productService = ProductServiceImpl.getInstance();
    private static final int SUBSTRING_INDEX = PRODUCTS_BY_CATEGORY_PATH.length();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException,
            SQLException, ServletException {

        HttpSession session = request.getSession();
        Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);

        String categoryUrl = request.getRequestURI().substring(SUBSTRING_INDEX);
        List<Product> products = productService
                .listProductsByCategory(categoryUrl, NUMBER_OF_FIRST_PAGE, MAX_PRODUCTS_PER_HTML_PAGE, languageID);
        Long totalCount = productService.countAllProductsByCategory(categoryUrl, languageID);
        request.setAttribute(PAGE_COUNT, getPageCount(totalCount, MAX_PRODUCTS_PER_HTML_PAGE ));
        request.setAttribute(PRODUCTS, products);
        request.setAttribute(SELECTED_CATEGORY_URL, categoryUrl);
        RoutingUtil.forwardToPage(PRODUCTS_JSP, request, response);

    }
}
