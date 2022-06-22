package com.madikhan.estore.action.impl.ajax;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.model.Order;
import com.madikhan.estore.service.OrderService;
import com.madikhan.estore.service.impl.OrderServiceImpl;
import com.madikhan.estore.util.RoutingUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LoadMoreOrdersForAdmin implements Action {

    private OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException,
            ServletException, SQLException {

        Integer languageID = (Integer) request.getSession().getAttribute(LANGUAGE_ID);
        List<Order> orders = orderService.listAllOrdersWithLimit(getPage(request), MAX_ORDERS_PER_ADMIN_PAGE, languageID);
        request.setAttribute(ORDERS, orders);
        RoutingUtil.forwardToFragment(ADMIN_ORDERS_TBODY_JSP, request, response);

    }
}
