package ru.eltech.sapr.web.app.exception;

public class BookServiceException extends RuntimeException {
    public BookServiceException(Throwable cause) { super(cause);}
    public BookServiceException(String message, Throwable cause) {super(message, cause);}
    public BookServiceException(String message) {super(message);}
}
