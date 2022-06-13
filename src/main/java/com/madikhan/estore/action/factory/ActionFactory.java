package com.madikhan.estore.action.factory;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.action.impl.RegistrationPageAction;
import com.madikhan.estore.action.impl.*;
import com.madikhan.estore.action.impl.ajax.LoadMoreProductsAction;
import com.madikhan.estore.action.impl.ajax.LoadMoreProductsByCategoryAction;
import com.madikhan.estore.action.impl.ajax.LoadMoreSearchAction;

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
        URL_PATH_ACTION_MAP.put("/null", null);
        URL_PATH_ACTION_MAP.put("/moreProducts", new LoadMoreProductsAction());
        URL_PATH_ACTION_MAP.put("/products/*", new ProductsByCategoryAction());
        URL_PATH_ACTION_MAP.put("/search", new SearchAction());
        URL_PATH_ACTION_MAP.put("/moreProductsByCategory/*", new LoadMoreProductsByCategoryAction());
        URL_PATH_ACTION_MAP.put("/ajax/html/more/search", new LoadMoreSearchAction());
        URL_PATH_ACTION_MAP.put("/shopping-cart", new ShoppingCartAction());
    }

    public Action getAction(String request) {
        Action action = URL_PATH_ACTION_MAP.get("/error");

        for (Map.Entry<String, Action> pair : URL_PATH_ACTION_MAP.entrySet()) {
            if (request.equalsIgnoreCase(pair.getKey()) || (pair.getKey().contains("/*") && request.contains(trimString(pair.getKey()))) ) {
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

}
