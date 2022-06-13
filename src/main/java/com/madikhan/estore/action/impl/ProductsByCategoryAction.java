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
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class ProductsByCategoryAction implements Action {

    private ProductService productService = ProductServiceImpl.getInstance();
    private static final int SUBSTRING_INDEX = "/products".length();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
        String categoryUrl = request.getRequestURI().substring(SUBSTRING_INDEX);
        List<Product> products = productService.listProductsByCategory(categoryUrl, 1, MAX_PRODUCTS_PER_HTML_PAGE);
        request.setAttribute("products", products);
        request.setAttribute("categoryUrl", categoryUrl);
        RoutingUtil.forwardToPage("products.jsp", request, response);
    }
}
