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

public class  ProductsAction implements Action {

    private final ProductService productService = ProductServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException,
            SQLException, ServletException {

        HttpSession session = request.getSession(true);
        Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);
        List<Product> products = productService.listAllProducts(NUMBER_OF_FIRST_PAGE, MAX_PRODUCTS_PER_HTML_PAGE, languageID);

        request.setAttribute(PRODUCTS, products);
        Long totalCount = productService.countAllProducts();
        request.setAttribute(PAGE_COUNT, getPageCount(totalCount, MAX_PRODUCTS_PER_HTML_PAGE ));
        RoutingUtil.forwardToPage(PRODUCTS_JSP, request, response);
    }

}
