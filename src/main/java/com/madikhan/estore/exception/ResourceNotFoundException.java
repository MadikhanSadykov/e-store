package com.madikhan.estore.exception;

import javax.servlet.http.HttpServletResponse;

public class ResourceNotFoundException extends AbstractApplicationException {

    private static final long serialVersionUID = -1494038792898298696L;

    public ResourceNotFoundException(String s) {
        super(s, HttpServletResponse.SC_NOT_FOUND);
    }

}
