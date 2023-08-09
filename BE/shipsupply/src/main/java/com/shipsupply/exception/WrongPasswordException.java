package com.shipsupply.exception;

public class WrongPasswordException extends IllegalStateException {

    public WrongPasswordException() {
        super();
    }

    public WrongPasswordException(String s) {
        super(s);
    }

    public WrongPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongPasswordException(Throwable cause) {
        super(cause);
    }
}
