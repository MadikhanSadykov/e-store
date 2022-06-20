package com.madikhan.estore.action.impl.ajax;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.model.Cart;
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

    private ProductService productService = ProductServiceImpl.getInstance();
    private CartService cartService = CartServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
        HttpSession session = request.getSession(true);
        Integer languageID = (Integer) session.getAttribute("languageID");

        ProductForm productForm = createProductForm(request);
        Cart cart = SessionUtil.getCurrentShoppingCart(request);
        Boolean isPersisted = false;

        if (session.getAttribute("userID") != null || session.getAttribute("userID") != "") {
            cart.setIdUser((Long) session.getAttribute("userID"));
            cartService.save((Long) session.getAttribute("userID"), productForm);
            isPersisted = true;
        }


        productService.addProductToCart(productForm, cart, isPersisted, languageID);
        String cookieValue = productService.serializeCart(cart);
        SessionUtil.updateCurrentShoppingCartCookie(cookieValue, response);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalCount", cart.getTotalCount());
        jsonObject.put("totalCost", cart.getTotalCost());
        session.setAttribute(CURRENT_SHOPPING_CART, cart);
        RoutingUtil.sendJSON(jsonObject, request, response);

    }
}
