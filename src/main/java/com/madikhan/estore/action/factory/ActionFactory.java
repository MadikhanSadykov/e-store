package com.madikhan.estore.action.factory;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.action.impl.RegistrationPageAction;
import com.madikhan.estore.action.impl.*;
import com.madikhan.estore.action.impl.ajax.*;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;



public class ActionFactory {

    private static ActionFactory ACTION_FACTORY;
    private static final Map<String, Action> URL_PATH_ACTION_MAP = new HashMap<>();

    static {
        URL_PATH_ACTION_MAP.put("/register", new RegisterAction());
        URL_PATH_ACTION_MAP.put("/registrationPage", new RegistrationPageAction());
        URL_PATH_ACTION_MAP.put("/login", new LoginAction());
        URL_PATH_ACTION_MAP.put("/loginPage", new LoginPageAction());
        URL_PATH_ACTION_MAP.put("/logOut", new LogOutAction());
        URL_PATH_ACTION_MAP.put("/products", new ProductsAction());
        URL_PATH_ACTION_MAP.put("/profile", new ProfilePageAction());
        URL_PATH_ACTION_MAP.put("/profileUpdate", new UpdateUserAction());
        URL_PATH_ACTION_MAP.put("/changeProfile", new ChangeProfilePageAction());
        URL_PATH_ACTION_MAP.put("/changeLang", new ChangeLanguageAction());
        URL_PATH_ACTION_MAP.put("/more/products", new LoadMoreProductsAction());
        URL_PATH_ACTION_MAP.put("/more/productsByCategory/*", new LoadMoreProductsByCategoryAction());
        URL_PATH_ACTION_MAP.put("/productsByCategory/*", new ProductsByCategoryAction());
        URL_PATH_ACTION_MAP.put("/search", new SearchAction());
        URL_PATH_ACTION_MAP.put("/more/search", new LoadMoreSearchAction());
        URL_PATH_ACTION_MAP.put("/shopping-cart", new ShoppingCartAction());
        URL_PATH_ACTION_MAP.put("/product/add", new AddProductToCartAction());
        URL_PATH_ACTION_MAP.put("/product/remove", new RemoveProductFromCartAction());
        URL_PATH_ACTION_MAP.put("/my-orders", new MyOrdersPageAction());
        URL_PATH_ACTION_MAP.put("/create-order", new CreateOrderAction());
        URL_PATH_ACTION_MAP.put("/order", new OrderPageAction());
        URL_PATH_ACTION_MAP.put("/more/my-orders", new LoadMoreMyOrdersAction());
    }

    public Action getAction(String requestURI) {
        Action action = URL_PATH_ACTION_MAP.get("/error");

        for (Map.Entry<String, Action> pair : URL_PATH_ACTION_MAP.entrySet()) {
            if (requestURI.equalsIgnoreCase(pair.getKey()) || (pair.getKey().contains("/*")
                    && isRequestURIContains(requestURI, trimString(pair.getKey())) )) {
                action = pair.getValue();
            }
        }
        return action;
    }

    public static ActionFactory getInstance() {
        if (ACTION_FACTORY == null) {
            ACTION_FACTORY = new ActionFactory();
        }
        return ACTION_FACTORY;
    }

    private String trimString(String string) {
        return string.substring(0, string.length() - 2);
    }

    private boolean isRequestURIContains(String requestURI, String URI) {

        int URILength = URI.length();
        int requestURILength = requestURI.length();

        int differenceLength = requestURILength - URILength;

        if (differenceLength < 0)
            differenceLength *= -1;

        int trimRequestURILength = requestURILength - differenceLength;

        if (trimRequestURILength < 0)
            trimRequestURILength *= -1;

        try {
            String compareURI = requestURI.substring(0, trimRequestURILength);
            if (URI.equals(compareURI))
                return true;
        } catch (StringIndexOutOfBoundsException e) {
            return false;
        }

        return false;
    }
}
