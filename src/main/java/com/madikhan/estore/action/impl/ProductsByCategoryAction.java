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

    private ProductService productService = ProductServiceImpl.getInstance();
    private static final int SUBSTRING_INDEX = "/productsByCategory".length();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
        HttpSession session = request.getSession();

        Integer languageID = (Integer) session.getAttribute("languageID");

        String categoryUrl = request.getRequestURI().substring(SUBSTRING_INDEX);
        List<Product> products = productService.listProductsByCategory(categoryUrl, (long) 1, MAX_PRODUCTS_PER_HTML_PAGE, languageID);
        Long totalCount = productService.countAllProductsByCategory(categoryUrl, languageID);
        request.setAttribute("pageCount", getPageCount(totalCount, MAX_PRODUCTS_PER_HTML_PAGE ));
        request.setAttribute("products", products);
        request.setAttribute("selectedCategoryUrl", categoryUrl);
        RoutingUtil.forwardToPage("products.jsp", request, response);
    }
}
