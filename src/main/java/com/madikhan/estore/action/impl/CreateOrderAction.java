package com.madikhan.estore.action.impl;

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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class CreateOrderAction implements Action {

    private OrderService orderService = OrderServiceImpl.getInstance();
    private CartService cartService = CartServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
        Cart cart = SessionUtil.getCurrentShoppingCart(request);
        User user = SessionUtil.getCurrentUser(request);
        Long orderID = orderService.save(cart, user.getId());
        for (CartItem cartItem : cart.getItems()) {
            if (cartItem.getProduct() != null) {
                cartService.remove(user.getId(), cartItem.getProduct().getId(), cartItem.getProductCount());
            }
        }
        SessionUtil.clearCurrentShoppingCart(request, response);
        request.getSession().setAttribute("CURRENT_MESSAGE", "Order created successfully. Please wait for our reply.");
        RoutingUtil.redirect("/order?id=" + orderID, request, response);
    }
}
