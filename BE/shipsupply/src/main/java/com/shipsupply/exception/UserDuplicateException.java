package com.shipsupply.exception;

public class UserDuplicateException extends IllegalStateException {

    public UserDuplicateException() {
        super();
    }

    public UserDuplicateException(String s) {
        super(s);
    }

    public UserDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDuplicateException(Throwable cause) {
        super(cause);
    }
}
