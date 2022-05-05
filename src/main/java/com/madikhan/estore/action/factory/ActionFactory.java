package com.madikhan.estore.action.factory;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.action.impl.RegisterAction;

import java.util.HashMap;
import java.util.Map;

import static com.madikhan.estore.constants.URLPathConstants.*;


public class ActionFactory {

    private static ActionFactory SERVICE_FACTORY;
    private static final Map<String, Action> URL_PATH_SERVICE_MAP = new HashMap<>();

    static {
        URL_PATH_SERVICE_MAP.put(REGISTER_URL_PATH, new RegisterAction());
        URL_PATH_SERVICE_MAP.put(LOGIN_URL_PATH, new RegisterAction());
    }

    public Action getAction(String request) {
        Action service = URL_PATH_SERVICE_MAP.get(ERROR_URL_PATH);

        for (Map.Entry<String, Action> pair : URL_PATH_SERVICE_MAP.entrySet()) {
            if (request.equalsIgnoreCase(pair.getKey())) {
                service = pair.getValue();
            }
        }
        return service;
    }

    public static ActionFactory getInstance() {
        if (SERVICE_FACTORY == null) {
            SERVICE_FACTORY = new ActionFactory();
        }
        return SERVICE_FACTORY;
    }

}
