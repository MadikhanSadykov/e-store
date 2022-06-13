package com.madikhan.estore.constants;

public class NamesConstants {

    public static final Boolean USER_ROLE = false;
    public static final Boolean ADMIN_ROLE = true;

    public static final String IS_ADMIN = "isAdmin";
    public static final String USER_ID = "userID";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String EMAIL = "email";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String ADDRESS = "address";
    public static final String PASSWORD = "password";


    public static final String CURRENT_SHOPPING_CART = "CURRENT_SHOPPING_CART";

    public static final int MAX_PRODUCT_COUNT_PER_SHOPPING_CART = 10;

    public static final int MAX_PRODUCTS_PER_SHOPPING_CART = 20;

    public static final int MAX_PRODUCTS_PER_HTML_PAGE = 24;

    public static final String ACCOUNT_ACTIONS_HISTORY = "ACCOUNT_ACTIONS_HISTORY";
    public static final String AUTHENTICATION_ERROR = "authError";
    public static final String EMAIL_AUTH_ERROR = "emailAuthError";
    public static final String PASSWORD_AUTH_ERROR = "passwordAuthError";
    public static final String EMAIL_IS_WRONG = "Email is wrong";
    public static final String PASSWORD_IS_WRONG = "Password is wrong";
    public static final String CATEGORY_LIST = "categoryList";
    public static final String PRODUCER_LIST = "producerList";

    public static final String LOGIN = "login";
    public static final String HOME_PAGE_PATH = "/";
    public static final String ERROR_PAGE_PATH ="/error";

    public enum Cookie {
        //1 year ttl
        SHOPPING_CART("iSCC", 60 * 60 * 24 * 30);

        private final String name;
        private final int ttl;

        private Cookie(String name, int ttl) {
            this.name = name;
            this.ttl = ttl;
        }

        public String getName() {
            return name;
        }

        public int getTtl() {
            return ttl;
        }
    }
}
