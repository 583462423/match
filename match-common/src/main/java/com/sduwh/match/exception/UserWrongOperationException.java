package com.sduwh.match.exception;

/**
 * When discover a user doing a wrong-operation, should throw this.
 */
public class UserWrongOperationException extends RuntimeException {

    public UserWrongOperationException() {
    }

    public UserWrongOperationException(final String message) {
        super(message);
    }

    public UserWrongOperationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserWrongOperationException(final Throwable cause) {
        super(cause);
    }

    public UserWrongOperationException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
