package com.madikhan.estore.action.impl;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.model.Cart;
import com.madikhan.estore.model.CartItem;
import com.madikhan.estore.model.User;
import com.madikhan.estore.service.CartService;
import com.madikhan.estore.service.OrderService;
import com.madikhan.estore.service.impl.CartServiceImpl;
import com.madikhan.estore.service.impl.OrderServiceImpl;
import com.madikhan.estore.util.RoutingUtil;
import com.madikhan.estore.util.SessionUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.madikhan.estore.constants.NamesConstants.CHANGE_PROFILE_PAGE;

public class CreateOrderAction implements Action {

    private final OrderService orderService = OrderServiceImpl.getInstance();
    private final CartService cartService = CartServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException,
            SQLException, ServletException {

        Cart cart = SessionUtil.getCurrentShoppingCart(request);
        User user = SessionUtil.getCurrentUser(request);
        Long orderID = orderService.save(cart, user.getId());

        try {
            if (!user.getSurname().isEmpty() && !user.getAddress().isEmpty() && !user.getPhoneNumber().isEmpty()) {
                for (CartItem cartItem : cart.getItems()) {
                    if (cartItem.getProduct() != null) {
                        cartService.remove(user.getId(), cartItem.getProduct().getId(), cartItem.getProductCount());
                    }
                }
                SessionUtil.clearCurrentShoppingCart(request, response);
                request.getSession().setAttribute(CURRENT_MESSAGE, ORDER_CREATED_MESSAGE);
                RoutingUtil.redirect(ORDER_PAGE_WITH_PARAMETER_PATH + orderID, request, response);
            }
        } catch (NullPointerException nullPointerException) {
            request.setAttribute(USER_DATA_NOT_COMPLETE, USER_DATA_NOT_COMPLETE_MESSAGE);
            RequestDispatcher dispatcher;
            dispatcher = request.getRequestDispatcher(CHANGE_PROFILE_PAGE);
            dispatcher.forward(request, response);
        }

    }
}
