package com.madikhan.estore.validator;

import com.madikhan.estore.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;

public class AdminValidator {

    public static boolean isAdminRole(HttpServletRequest request) {
        return SessionUtil.getCurrentUser(request).getIsAdmin();
    }

}
