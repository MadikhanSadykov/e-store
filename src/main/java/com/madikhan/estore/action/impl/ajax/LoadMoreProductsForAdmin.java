package com.madikhan.estore.action.impl.ajax;

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

public class LoadMoreProductsForAdmin implements Action {

    private final ProductService productService = ProductServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException,
            ServletException, SQLException {

        Integer languageID = (Integer) request.getSession(true).getAttribute(LANGUAGE_ID);
        List<Product> products = productService.listAllProducts(getPage(request), MAX_PRODUCTS_PER_ADMIN_PAGE, languageID);
        request.setAttribute(PRODUCTS, products);
        RoutingUtil.forwardToFragment(ADMIN_PRODUCTS_TBODY_JSP, request, response);

    }
}
