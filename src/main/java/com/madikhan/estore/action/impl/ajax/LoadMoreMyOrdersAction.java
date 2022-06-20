package com.madikhan.estore.action.impl.ajax;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
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

public class LoadMoreMyOrdersAction implements Action {

    private OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException,
            ServletException {
        User user = SessionUtil.getCurrentUser(request);
        Integer languageID = (Integer) request.getSession().getAttribute("languageID");
        List<Order> orders = orderService.listAllOrdersByUserID(user.getId(), getPage(request), MAX_ORDERS_PER_HTML_PAGE,
                languageID);
        request.setAttribute("orders", orders);
        RoutingUtil.forwardToFragment("my-orders-tbody.jsp", request, response);
    }
}
