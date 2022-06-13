package com.madikhan.estore.action.impl.ajax;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.model.Product;
import com.madikhan.estore.service.ProductService;
import com.madikhan.estore.service.impl.ProductServiceImpl;
import com.madikhan.estore.util.RoutingUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import static com.madikhan.estore.constants.NamesConstants.MAX_PRODUCTS_PER_HTML_PAGE;

public class LoadMoreProductsAction implements Action{

    private ProductService productService = ProductServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            List<Product> products = null;
            try {
                products = productService.listAllProducts(2, MAX_PRODUCTS_PER_HTML_PAGE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            request.setAttribute("products", products);
            RoutingUtil.forwardToFragment("product-list.jsp", request, response);
    }

}
