package model;

public class NegativeQuantityException extends Exception{
    public NegativeQuantityException() {
    }

    public NegativeQuantityException(String message) {
        super(message);
    }

    public NegativeQuantityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NegativeQuantityException(Throwable cause) {
        super(cause);
    }

    public NegativeQuantityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
