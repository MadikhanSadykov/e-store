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

    private OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
        String message = (String) request.getSession().getAttribute("CURRENT_MESSAGE");
        Integer languageID = (Integer) request.getSession().getAttribute("languageID");
        User user = (User) request.getSession().getAttribute(CURRENT_USER);
        request.getSession().removeAttribute("CURRENT_MESSAGE");
        request.setAttribute("CURRENT_MESSAGE", message);
        Long orderID = Long.parseLong(request.getParameter("id"));
        Order order = orderService.findByID(orderID, languageID);
        if (!order.getIdUser().equals(user.getId())) {
            throw new AccessDeniedException("Account with id = " + user.getId() + " is not owner for order with id = " + order.getId());
        }
        request.setAttribute("order", order);
        RoutingUtil.forwardToPage("order.jsp", request, response);
    }
}
