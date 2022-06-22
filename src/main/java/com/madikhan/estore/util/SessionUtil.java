package com.madikhan.estore.util;

import static com.madikhan.estore.constants.NamesConstants.*;
import static com.madikhan.estore.constants.NamesConstants.Cookie.*;
import com.madikhan.estore.model.Cart;
import com.madikhan.estore.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionUtil {

    public static Cart getCurrentShoppingCart(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute(CURRENT_SHOPPING_CART);
        if (cart == null) {
            cart = new Cart();
            setCurrentShoppingCart(request, cart);
        }
        return cart;
    }

    public static boolean isCurrentShoppingCartCreated(HttpServletRequest req) {
        return req.getSession().getAttribute(CURRENT_SHOPPING_CART) != null;
    }

    public static void setCurrentShoppingCart(HttpServletRequest req, Cart cart) {
        req.getSession().setAttribute(CURRENT_SHOPPING_CART, cart);
    }

    public static void clearCurrentShoppingCart(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().removeAttribute(CURRENT_SHOPPING_CART);
        WebUtil.setCookie(SHOPPING_CART.getName(), null, 0, resp);
    }

    public static Cookie findShoppingCartCookie(HttpServletRequest req) {
        return WebUtil.findCookie(req, SHOPPING_CART.getName());
    }

    public static void updateCurrentShoppingCartCookie(String cookieValue, HttpServletResponse resp) {
        WebUtil.setCookie(SHOPPING_CART.getName(), cookieValue,
                SHOPPING_CART.getTtl(), resp);
    }

    public static User getCurrentUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(CURRENT_USER);
    }

    public static void setCurrentUser(HttpServletRequest request, User user) {
        request.getSession().setAttribute(CURRENT_USER, user);
    }

    public static boolean isCurrentUserCreated(HttpServletRequest request) {
        return getCurrentUser(request) != null;
    }

    public static void clearCurrentUser(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().removeAttribute(CURRENT_USER);
    }

    private SessionUtil() {
    }
}