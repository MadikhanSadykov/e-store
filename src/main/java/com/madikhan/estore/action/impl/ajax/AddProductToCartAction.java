package com.madikhan.estore.action.impl.ajax;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.model.Cart;
import com.madikhan.estore.model.User;
import com.madikhan.estore.model.form.ProductForm;
import com.madikhan.estore.service.CartService;
import com.madikhan.estore.service.ProductService;
import com.madikhan.estore.service.impl.CartServiceImpl;
import com.madikhan.estore.service.impl.ProductServiceImpl;
import com.madikhan.estore.util.RoutingUtil;
import com.madikhan.estore.util.SessionUtil;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class AddProductToCartAction implements Action {

    private final ProductService productService = ProductServiceImpl.getInstance();
    private final CartService cartService = CartServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException,
            SQLException, ServletException {

        HttpSession session = request.getSession(true);
        Integer languageID = (Integer) session.getAttribute(LANGUAGE_ID);
        ProductForm productForm = createProductForm(request);
        Cart cart = SessionUtil.getCurrentShoppingCart(request);
        boolean isPersisted = false;
        User user = SessionUtil.getCurrentUser(request);

        if (user != null) {
            cart.setIdUser(user.getId());
            cartService.save(user.getId(), productForm);
            isPersisted = true;
        }

        productService.addProductToCart(productForm, cart, isPersisted, languageID);
        String cookieValue = productService.serializeCart(cart);
        SessionUtil.updateCurrentShoppingCartCookie(cookieValue, response);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TOTAL_COUNT_ATTRIBUTE, cart.getTotalCount());
        jsonObject.put(TOTAL_COST_ATTRIBUTE, cart.getTotalCost());
        session.setAttribute(CURRENT_SHOPPING_CART, cart);
        RoutingUtil.sendJSON(jsonObject, request, response);

    }
}
