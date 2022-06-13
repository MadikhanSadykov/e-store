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

public class  ProductsAction implements Action {

    private ProductService productService = ProductServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
        List<Product> products = null;
        try {
            products = productService.listAllProducts(1, MAX_PRODUCTS_PER_HTML_PAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("products", products);
        RoutingUtil.forwardToPage("products.jsp", request, response);
    }

}
