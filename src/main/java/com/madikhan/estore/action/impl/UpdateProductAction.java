package com.madikhan.estore.action.impl;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.exception.AccessDeniedException;
import com.madikhan.estore.model.*;
import com.madikhan.estore.service.CategoryService;
import com.madikhan.estore.service.OrderItemService;
import com.madikhan.estore.service.ProductService;
import com.madikhan.estore.service.impl.CategoryServiceImpl;
import com.madikhan.estore.service.impl.OrderItemServiceImpl;
import com.madikhan.estore.service.impl.ProductServiceImpl;
import com.madikhan.estore.util.RoutingUtil;
import com.madikhan.estore.util.SessionUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

public class UpdateProductAction implements Action {

    private final ProductService productService = ProductServiceImpl.getInstance();
    private final CategoryService categoryService = CategoryServiceImpl.getInstance();
    private final OrderItemService orderItemService = OrderItemServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException,
            SQLException, ServletException {

        User user = SessionUtil.getCurrentUser(request);

        if (user.getIsAdmin()) {
            Long countProductInOrderItems =
                    orderItemService.countAllByProductID(Long.valueOf(request.getParameter(ID)));

            if (countProductInOrderItems == ZERO) {
                Product product = new Product();
                product.setId(Long.valueOf(request.getParameter(ID)));
                product.setName(request.getParameter(NAME));
                product.setDescription(request.getParameter(DESCRIPTION));
                product.setPrice(BigDecimal.valueOf(Double.parseDouble(request.getParameter(PRICE))));
                product.setCategoryID(Long.valueOf(request.getParameter(CATEGORY_ID)));
                product.setProducerID(Long.valueOf(request.getParameter(PRODUCER_ID)));

                Category category = categoryService.getByID(product.getCategoryID());
                String imageLink = MEDIA_FOLDER_PATH + category.getUrl() + JPG_FORMAT;
                product.setImageLink(imageLink);
                product.setCategory(category.getName());

                productService.update(product.getId(), product);
                RoutingUtil.redirect(ADMIN_PRODUCTS_JSP, request, response);
            } else {
                request.setAttribute(PRODUCT_UPDATE_ERROR, PRODUCT_UPDATE_ERROR_CAUSE);
                RequestDispatcher dispatcher;
                dispatcher = request.getRequestDispatcher(UPDATE_PRODUCT_PAGE_WITH_PARAMETER_PATH + request.getParameter(ID));
                dispatcher.forward(request, response);
            }
        } else {
            throw new AccessDeniedException(YOU_ARE_NOT_ADMIN_MESSAGE);
        }
    }
}
