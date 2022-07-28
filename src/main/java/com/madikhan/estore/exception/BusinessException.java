package com.madikhan.estore.exception;

import javax.servlet.http.HttpServletResponse;

public class BusinessException extends AbstractApplicationException {
    public BusinessException(String message) {
        super(message, HttpServletResponse.SC_FORBIDDEN);
    }
}
