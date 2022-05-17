package com.madikhan.estore.util;

import com.madikhan.estore.constants.NamesConstants;
import com.madikhan.estore.model.ShoppingCart;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionUtil {
    public static ShoppingCart getCurrentShoppingCart(HttpServletRequest req) {
        ShoppingCart shoppingCart = (ShoppingCart) req.getSession().getAttribute(NamesConstants.CURRENT_SHOPPING_CART);
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            setCurrentShoppingCart(req, shoppingCart);
        }
        return shoppingCart;
    }

    public static boolean isCurrentShoppingCartCreated(HttpServletRequest req) {
        return req.getSession().getAttribute(NamesConstants.CURRENT_SHOPPING_CART) != null;
    }

    public static void setCurrentShoppingCart(HttpServletRequest req, ShoppingCart shoppingCart) {
        req.getSession().setAttribute(NamesConstants.CURRENT_SHOPPING_CART, shoppingCart);
    }

    public static void clearCurrentShoppingCart(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().removeAttribute(NamesConstants.CURRENT_SHOPPING_CART);
        WebUtil.setCookie(NamesConstants.Cookie.SHOPPING_CART.getName(), null, 0, resp);
    }

    public static Cookie findShoppingCartCookie(HttpServletRequest req) {
        return WebUtil.findCookie(req, NamesConstants.Cookie.SHOPPING_CART.getName());
    }

    public static void updateCurrentShoppingCartCookie(String cookieValue, HttpServletResponse resp) {
        WebUtil.setCookie(NamesConstants.Cookie.SHOPPING_CART.getName(), cookieValue,
                NamesConstants.Cookie.SHOPPING_CART.getTtl(), resp);
    }

    private SessionUtil() {
    }
}