package com.madikhan.estore.action.impl;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.model.Cart;
import com.madikhan.estore.model.CartItem;
import com.madikhan.estore.model.User;
import com.madikhan.estore.model.form.ProductForm;
import com.madikhan.estore.service.CartService;
import com.madikhan.estore.service.ProductService;
import com.madikhan.estore.service.UserService;
import com.madikhan.estore.service.impl.CartServiceImpl;
import com.madikhan.estore.service.impl.ProductServiceImpl;
import com.madikhan.estore.service.impl.UserServiceImpl;
import com.madikhan.estore.util.RoutingUtil;
import com.madikhan.estore.util.SessionUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class LoginAction implements Action {

    private final UserService userService = UserServiceImpl.getInstance();
    private final CartService cartService = CartServiceImpl.getInstance();
    private final ProductService productService = ProductServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException,
            ServletException, IOException {

        HttpSession session = request.getSession(true);
        Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);
        Cart cart = SessionUtil.getCurrentShoppingCart(request);
        RequestDispatcher dispatcher;

        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String encodedPassword = DigestUtils.md5Hex(password);

        boolean isMailExists = userService.isEmailExists(email);

        if (!isMailExists) {
            request.setAttribute(USER_EMAIL_ATTRIBUTE, email);
            request.setAttribute(EMAIL_AUTH_ERROR, EMAIL_IS_WRONG_MESSAGE);
            RoutingUtil.forwardToPage(LOGIN_JSP, request, response);
        } else {
            User user = userService.getUserByEmailAndPassword(email, encodedPassword);
            if (user != null) {
                SessionUtil.setCurrentUser(request, user);
                if (!cart.getProducts().isEmpty()) {
                    for (CartItem cartItem : cart.getItems()) {
                        if (!cartItem.getPersisted()) {
                            cartService.save(user.getId(),
                                    new ProductForm(cartItem.getProduct().getId(), cartItem.getProductCount()));
                        }
                    }
                    if (!cart.getPersistedDeletedList().isEmpty()) {
                        for (CartItem cartItem : cart.getPersistedDeleted()) {
                                cartService.remove(user.getId(), cartItem.getIdProduct(), cartItem.getProductCountToDelete());
                        }
                        cart.getPersistedDeletedList().clear();
                    }
                } else {
                    cartService.addProductToCartFromDB(user.getId(), cart, languageID);
                    String cookieValue = productService.serializeCart(cart);
                    SessionUtil.updateCurrentShoppingCartCookie(cookieValue, response);
                    SessionUtil.setCurrentUser(request, user);
                }
                dispatcher = request.getRequestDispatcher(HOME_PAGE_PATH);
                dispatcher.forward(request, response);
            } else {
                request.setAttribute(USER_EMAIL_ATTRIBUTE, email);
                request.setAttribute(PASSWORD_AUTH_ERROR, PASSWORD_IS_WRONG_MESSAGE);
                RoutingUtil.forwardToPage(LOGIN_JSP, request, response);
            }

        }
    }

}
