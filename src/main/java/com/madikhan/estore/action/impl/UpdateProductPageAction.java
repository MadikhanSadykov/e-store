package com.madikhan.estore.action.impl;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.exception.AccessDeniedException;
import com.madikhan.estore.exception.ResourceNotFoundException;
import com.madikhan.estore.model.Product;
import com.madikhan.estore.model.User;
import com.madikhan.estore.service.ProductService;
import com.madikhan.estore.service.impl.ProductServiceImpl;
import com.madikhan.estore.util.RoutingUtil;
import com.madikhan.estore.util.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class UpdateProductPageAction implements Action {

    private final ProductService productService = ProductServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException,
            SQLException, ServletException {

        User user = SessionUtil.getCurrentUser(request);
        Long productID = Long.valueOf(request.getParameter(ID));
        Integer languageID = (Integer) request.getSession().getAttribute(LANGUAGE_ID);
        if (user.getIsAdmin()) {
            Product product = productService.getProductByID(productID, languageID);
            if (product != null) {
                request.setAttribute(PRODUCT, product);
                RoutingUtil.forwardToPage(UPDATE_PRODUCT_JSP, request, response);
            } else {
                throw new ResourceNotFoundException(THERE_IS_NO_SUCH_PRODUCT_MESSAGE);
            }
        } else {
            throw new AccessDeniedException(YOU_ARE_NOT_ADMIN_MESSAGE);
        }
    }
}
