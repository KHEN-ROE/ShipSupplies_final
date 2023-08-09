package com.shipsupply.exception;

public class DeletedUserException extends IllegalStateException {

    public DeletedUserException() {
        super();
    }

    public DeletedUserException(String message) {
        super(message);
    }

    public DeletedUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeletedUserException(Throwable cause) {
        super(cause);
    }

}
