package com.madikhan.estore.filter;

import static com.madikhan.estore.constants.NamesConstants.LANGUAGE_ID;

import com.madikhan.estore.model.Cart;
import com.madikhan.estore.service.ProductService;
import com.madikhan.estore.service.impl.ProductServiceImpl;
import com.madikhan.estore.util.SessionUtil;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AutoRestoreShoppingCartFilter extends AbstractFilter {

    private static final String SHOPPING_CARD_DESERIALIZATION_DONE = "SHOPPING_CARD_DESERIALIZATION_DONE";
    private ProductService productService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        productService = ProductServiceImpl.getInstance();
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException, SQLException {

        HttpSession session = request.getSession(true);
        Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);

        if(request.getSession().getAttribute(SHOPPING_CARD_DESERIALIZATION_DONE) == null){
            if(!SessionUtil.isCurrentShoppingCartCreated(request)) {
                Cookie cookie = SessionUtil.findShoppingCartCookie(request);
                if(cookie != null) {
                    Cart cart = productService.deserializeCart(cookie.getValue(), languageID);
                    if (cart != null) {
                        SessionUtil.setCurrentShoppingCart(request, cart);
                    }
                }
            }
            request.getSession().setAttribute(SHOPPING_CARD_DESERIALIZATION_DONE, Boolean.TRUE);
        }
        chain.doFilter(request, response);
    }

}