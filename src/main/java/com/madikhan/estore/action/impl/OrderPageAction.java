package com.madikhan.estore.action.impl;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.exception.AccessDeniedException;
import com.madikhan.estore.model.Order;
import com.madikhan.estore.model.User;
import com.madikhan.estore.service.OrderService;
import com.madikhan.estore.service.impl.OrderServiceImpl;
import com.madikhan.estore.util.RoutingUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class OrderPageAction implements Action {

    private final OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {

        String message = (String) request.getSession().getAttribute(CURRENT_MESSAGE);
        Integer languageID = (Integer) request.getSession().getAttribute(LANGUAGE_ID);
        User user = (User) request.getSession().getAttribute(CURRENT_USER);
        request.getSession().removeAttribute(CURRENT_MESSAGE);
        request.setAttribute(CURRENT_MESSAGE, message);
        Long orderID = Long.parseLong(request.getParameter(ID));
        Order order = orderService.findByID(orderID, languageID);
        if (!order.getIdUser().equals(user.getId()) && !user.getIsAdmin()) {
            throw new AccessDeniedException(
                    String.format(ACCOUNT_WITH_ID_NOT_OWNER_ORDER, user.getId(), order.getId()));
        }
        request.setAttribute(ORDER, order);
        RoutingUtil.forwardToPage(ORDER_JSP, request, response);

    }
}
