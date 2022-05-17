package com.madikhan.estore.action.factory;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.action.impl.*;
import com.madikhan.estore.action.impl.ajax.LoadMoreProductsAction;
import com.madikhan.estore.action.impl.ajax.LoadMoreProductsByCategoryAction;
import com.madikhan.estore.action.impl.ajax.LoadMoreSearchAction;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.madikhan.estore.constants.URLPathConstants.*;


public class ActionFactory {

    private static ActionFactory ACTION_FACTORY;
    private static final Map<String, Action> URL_PATH_ACTION_MAP = new HashMap<>();

    static {
        URL_PATH_ACTION_MAP.put(REGISTER_URL_PATH, new RegisterAction());
        URL_PATH_ACTION_MAP.put(PRODUCTS_URL_PATH, new ProductsAction());
        URL_PATH_ACTION_MAP.put(LOAD_MORE_PRODUCTS_URL_PATH, new LoadMoreProductsAction());
        URL_PATH_ACTION_MAP.put(PRODUCTS_BY_CATEGORY_URL_PATH, new ProductsByCategoryAction());
        URL_PATH_ACTION_MAP.put(SEARCH_URL_PATH, new SearchAction());
        URL_PATH_ACTION_MAP.put(LOAD_MORE_PRODUCTS_BY_CATEGORY_URL_PATH, new LoadMoreProductsByCategoryAction());
        URL_PATH_ACTION_MAP.put(LOAD_MORE_SEARCH_URL_PATH, new LoadMoreSearchAction());
        URL_PATH_ACTION_MAP.put(SHOPPING_CART_URL_PATH, new ShoppingCartAction());
    }

    public Action getAction(String request) {
        Action service = URL_PATH_ACTION_MAP.get(ERROR_URL_PATH);

        for (Map.Entry<String, Action> pair : URL_PATH_ACTION_MAP.entrySet()) {
            if (request.equalsIgnoreCase(pair.getKey()) || (pair.getKey().contains("/*") && request.contains(trimString(pair.getKey()))) ) {
                service = pair.getValue();
            }
        }
        return service;
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
