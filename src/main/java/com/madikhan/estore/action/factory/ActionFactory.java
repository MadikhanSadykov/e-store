package com.madikhan.estore.action.factory;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.action.impl.AdminOrdersPage;
import com.madikhan.estore.action.impl.AdminPageAction;
import com.madikhan.estore.action.impl.AdminProductsPageAction;
import com.madikhan.estore.action.impl.AdminUsersPageAction;
import com.madikhan.estore.action.impl.ChangeLanguageAction;
import com.madikhan.estore.action.impl.ChangeProfilePageAction;
import com.madikhan.estore.action.impl.CreateOrderAction;
import com.madikhan.estore.action.impl.CreateProductAction;
import com.madikhan.estore.action.impl.CreateProductPageAction;
import com.madikhan.estore.action.impl.ErrorAction;
import com.madikhan.estore.action.impl.LogOutAction;
import com.madikhan.estore.action.impl.LoginAction;
import com.madikhan.estore.action.impl.LoginPageAction;
import com.madikhan.estore.action.impl.MyOrdersPageAction;
import com.madikhan.estore.action.impl.OrderPageAction;
import com.madikhan.estore.action.impl.ProductsAction;
import com.madikhan.estore.action.impl.ProductsByCategoryAction;
import com.madikhan.estore.action.impl.ProfilePageAction;
import com.madikhan.estore.action.impl.RegisterAction;
import com.madikhan.estore.action.impl.RegistrationPageAction;
import com.madikhan.estore.action.impl.SearchAction;
import com.madikhan.estore.action.impl.ShoppingCartAction;
import com.madikhan.estore.action.impl.UpdateProductAction;
import com.madikhan.estore.action.impl.UpdateProductPageAction;
import com.madikhan.estore.action.impl.UpdateUserAction;
import com.madikhan.estore.action.impl.ajax.AddProductToCartAction;
import com.madikhan.estore.action.impl.ajax.CancelOrderAction;
import com.madikhan.estore.action.impl.ajax.CompleteOrderAction;
import com.madikhan.estore.action.impl.ajax.DeleteProductAction;
import com.madikhan.estore.action.impl.ajax.DeleteUserAction;
import com.madikhan.estore.action.impl.ajax.LoadMoreMyOrdersAction;
import com.madikhan.estore.action.impl.ajax.LoadMoreOrdersForAdmin;
import com.madikhan.estore.action.impl.ajax.LoadMoreProductsAction;
import com.madikhan.estore.action.impl.ajax.LoadMoreProductsByCategoryAction;
import com.madikhan.estore.action.impl.ajax.LoadMoreProductsForAdmin;
import com.madikhan.estore.action.impl.ajax.LoadMoreSearchAction;
import com.madikhan.estore.action.impl.ajax.LoadMoreUsersForAdmin;
import com.madikhan.estore.action.impl.ajax.MarkUserAsAdmin;
import com.madikhan.estore.action.impl.ajax.MarkUserNotAdmin;
import com.madikhan.estore.action.impl.ajax.RemoveProductFromCartAction;


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
        Action put = URL_PATH_ACTION_MAP.put("/products", new ProductsAction());
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
        URL_PATH_ACTION_MAP.put("/adminPage", new AdminPageAction());
        URL_PATH_ACTION_MAP.put("/adminUsersPage", new AdminUsersPageAction());
        URL_PATH_ACTION_MAP.put("/adminProductsPage", new AdminProductsPageAction());
        URL_PATH_ACTION_MAP.put("/adminOrdersPage", new AdminOrdersPage());
        URL_PATH_ACTION_MAP.put("/user/isAdmin", new MarkUserAsAdmin());
        URL_PATH_ACTION_MAP.put("/user/notAdmin", new MarkUserNotAdmin());
        URL_PATH_ACTION_MAP.put("/user/delete", new DeleteUserAction());
        URL_PATH_ACTION_MAP.put("/updateProduct", new UpdateProductAction());
        URL_PATH_ACTION_MAP.put("/updateProductPage", new UpdateProductPageAction());
        URL_PATH_ACTION_MAP.put("/createProduct", new CreateProductAction());
        URL_PATH_ACTION_MAP.put("/createProductPage", new CreateProductPageAction());
        URL_PATH_ACTION_MAP.put("/more/adminProductsPage", new LoadMoreProductsForAdmin());
        URL_PATH_ACTION_MAP.put("/more/adminUsersPage", new LoadMoreUsersForAdmin());
        URL_PATH_ACTION_MAP.put("/more/adminOrdersPage", new LoadMoreOrdersForAdmin());
        URL_PATH_ACTION_MAP.put("/product/delete", new DeleteProductAction());
        URL_PATH_ACTION_MAP.put("/order/complete", new CompleteOrderAction());
        URL_PATH_ACTION_MAP.put("/order/cancel", new CancelOrderAction());
        URL_PATH_ACTION_MAP.put("/error", new ErrorAction());
    }

    public Action getAction(String requestURI) {
        Action action = null;

        for (Map.Entry<String, Action> pair : URL_PATH_ACTION_MAP.entrySet()) {
            if (requestURI.equalsIgnoreCase(pair.getKey()) || (pair.getKey().contains("/*")
                    && isRequestURIContains(requestURI, trimLastTwoCharsStringURI(pair.getKey())) )) {
                action = pair.getValue();
            }
        }
        if (action == null) {
            action = URL_PATH_ACTION_MAP.get("/error");
        }
        return action;
    }

    public static ActionFactory getInstance() {
        if (ACTION_FACTORY == null) {
            ACTION_FACTORY = new ActionFactory();
        }
        return ACTION_FACTORY;
    }

    private String trimLastTwoCharsStringURI(String string) {
        int INDEX_OF_FIRST_CHAR = 0;
        int TWO_LAST_CHARS = 2;
        // subtraction of 2 in order to delete extra chars in the end (like "/*")
        return string.substring(INDEX_OF_FIRST_CHAR, string.length() - TWO_LAST_CHARS);
    }

    private boolean isRequestURIContains(String requestURI, String URI) {

        int NEGATIVE_SIGN = -1;
        int URILength = URI.length();
        int requestURILength = requestURI.length();
        int differenceLength = requestURILength - URILength;

        if (differenceLength < 0) {
            // change sign if it is negative
            differenceLength = differenceLength * (NEGATIVE_SIGN);
        }

        int trimRequestURILength = requestURILength - differenceLength;

        if (trimRequestURILength < 0) {
            // change sign if it is negative
            trimRequestURILength = trimRequestURILength * (NEGATIVE_SIGN);
        }

        if (requestURI.length() >= trimRequestURILength) {
            String compareURI = requestURI.substring(0, trimRequestURILength);
            return URI.equals(compareURI);
        }

        return false;
    }
}
