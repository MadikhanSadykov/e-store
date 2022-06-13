package com.madikhan.estore.exception;

public abstract class AbstractApplicationException extends IllegalArgumentException {

    private static final long serialVersionUID = 3838344980505779324L;

    private final int code;

    public AbstractApplicationException(String message, int code) {
        super(message);
        this.code = code;
    }

    public AbstractApplicationException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public AbstractApplicationException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
