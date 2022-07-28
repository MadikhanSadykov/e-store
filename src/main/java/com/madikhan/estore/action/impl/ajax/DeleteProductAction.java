package com.madikhan.estore.action.impl.ajax;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.exception.AccessDeniedException;
import com.madikhan.estore.service.OrderItemService;
import com.madikhan.estore.service.ProductService;
import com.madikhan.estore.service.impl.OrderItemServiceImpl;
import com.madikhan.estore.service.impl.ProductServiceImpl;
import com.madikhan.estore.validator.AdminValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteProductAction implements Action {

    private final OrderItemService orderItemService = OrderItemServiceImpl.getInstance();
    private final ProductService productService = ProductServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException,
            SQLException, ServletException {

        if (AdminValidator.isAdminRole(request)) {
            Long productID = Long.parseLong((request.getParameter(PRODUCT_ID_ATTRIBUTE)));
            Long countOrderItems = orderItemService.countAllByProductID(productID);
            if ( countOrderItems == null || countOrderItems == ZERO) {
                productService.delete(productID);
            } else {
                throw new Error();
            }
        } else {
            throw new AccessDeniedException(YOU_ARE_NOT_ADMIN_MESSAGE);
        }

    }
}
