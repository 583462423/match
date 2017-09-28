package com.sduwh.match.exception;

/**
 * When discover a user doing a bad-operation, ought to throw this.
 */
public class UserBadOperationException extends RuntimeException {

    public UserBadOperationException() {
    }

    public UserBadOperationException(final String message) {
        super(message);
    }

    public UserBadOperationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserBadOperationException(final Throwable cause) {
        super(cause);
    }

    public UserBadOperationException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
