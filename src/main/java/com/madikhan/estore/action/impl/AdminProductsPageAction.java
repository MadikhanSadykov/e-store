package com.madikhan.estore.action.impl;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.exception.AccessDeniedException;
import com.madikhan.estore.model.Product;
import com.madikhan.estore.service.ProductService;
import com.madikhan.estore.service.impl.ProductServiceImpl;
import com.madikhan.estore.util.RoutingUtil;
import com.madikhan.estore.validator.AdminValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminProductsPageAction implements Action {

    private final ProductService productService = ProductServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException,
            SQLException, ServletException {

        Integer languageID = (Integer) request.getSession().getAttribute(LANGUAGE_ID);
        if (AdminValidator.isAdminRole(request)) {
            List<Product> products = productService
                    .listAllProducts(NUMBER_OF_FIRST_PAGE, MAX_PRODUCTS_PER_ADMIN_PAGE, languageID);

            request.setAttribute(PRODUCTS, products);
            Long totalCount = productService.countAllProducts();
            request.setAttribute(PAGE_COUNT, getPageCount(totalCount, MAX_PRODUCTS_PER_ADMIN_PAGE ));
            RoutingUtil.forwardToPage(ADMIN_PRODUCTS_JSP, request, response);
        } else {
            throw new AccessDeniedException(YOU_ARE_NOT_ADMIN_MESSAGE);
        }

    }
}
