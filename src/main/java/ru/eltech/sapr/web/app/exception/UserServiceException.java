package ru.eltech.sapr.web.app.exception;

public class UserServiceException extends RuntimeException {
    public UserServiceException(Throwable cause) { super(cause);}
    public UserServiceException(String message, Throwable cause) {super(message, cause);}
    public UserServiceException(String message) {super(message);}
}
