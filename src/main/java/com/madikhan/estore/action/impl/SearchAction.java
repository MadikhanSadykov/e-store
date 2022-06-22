package com.madikhan.estore.action.impl;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.model.Product;
import com.madikhan.estore.model.form.SearchForm;
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

public class SearchAction implements Action {

    private final ProductService productService = ProductServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException,
            SQLException, ServletException {

        SearchForm searchForm = createSearchForm(request);

        HttpSession session = request.getSession(true);
        Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);
        List<Product> products = productService
                .listProductsBySearchForm(searchForm, NUMBER_OF_FIRST_PAGE, MAX_PRODUCTS_PER_HTML_PAGE, languageID);

        request.setAttribute(PRODUCTS, products);
        Long totalCount = productService.countAllProductsBySearchForm(searchForm, languageID);
        request.setAttribute(PAGE_COUNT, getPageCount(totalCount, MAX_PRODUCTS_PER_HTML_PAGE ));
        request.setAttribute(PRODUCT_COUNT, totalCount);
        request.setAttribute(SEARCH_FORM, searchForm);
        RoutingUtil.forwardToPage(SEARCH_RESULT_JSP, request, response);

    }
}
