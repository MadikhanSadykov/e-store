package com.madikhan.estore.action.impl;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.exception.AccessDeniedException;
import com.madikhan.estore.model.Order;
import com.madikhan.estore.model.User;
import com.madikhan.estore.service.OrderService;
import com.madikhan.estore.service.impl.OrderServiceImpl;
import com.madikhan.estore.util.RoutingUtil;
import com.madikhan.estore.util.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MyOrdersPageAction implements Action {

    private final OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException,
            SQLException, ServletException {

        if (SessionUtil.isCurrentUserCreated(request)) {
            User user = SessionUtil.getCurrentUser(request);
            Integer languageID = (Integer) request.getSession().getAttribute(LANGUAGE_ID);
            List<Order> orders = orderService
                    .listAllOrdersByUserID(user.getId(), NUMBER_OF_FIRST_PAGE, MAX_ORDERS_PER_HTML_PAGE, languageID);
            request.setAttribute(ORDERS, orders);
            Long orderCount = orderService.countUserOrders(user.getId());
            request.setAttribute(PAGE_COUNT, getPageCount(orderCount, MAX_ORDERS_PER_HTML_PAGE));
            RoutingUtil.forwardToPage(MY_ORDERS_LOGIN_JSP, request, response);
        } else {
            throw new AccessDeniedException(YOU_ARE_NOT_ADMIN_MESSAGE);
        }

    }
}
