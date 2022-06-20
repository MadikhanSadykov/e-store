package com.madikhan.estore.exception;

import javax.servlet.http.HttpServletResponse;

public class AccessDeniedException extends AbstractApplicationException {

    private static final long serialVersionUID = -8005636760960585800L;

    public AccessDeniedException(String s) {
        super(s, HttpServletResponse.SC_FORBIDDEN);
    }
}
