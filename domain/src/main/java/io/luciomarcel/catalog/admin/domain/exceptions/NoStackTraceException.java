package io.luciomarcel.catalog.admin.domain.exceptions;

public class NoStackTraceException extends RuntimeException{

    public NoStackTraceException(String message) {
        super(message, null);
    }

    public NoStackTraceException(String message, Throwable cause) {
        super(message, cause, true, false);
    }
}
