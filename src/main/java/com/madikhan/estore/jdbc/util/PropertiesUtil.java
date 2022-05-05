package com.madikhan.estore.jdbc.util;

import java.util.ResourceBundle;

public class PropertiesUtil {

    private static final PropertiesUtil instance = new PropertiesUtil();
    private final ResourceBundle resource = ResourceBundle.getBundle("ConnectionPool");

    public static PropertiesUtil getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return resource.getString(key);
    }

}
