package model;

/**
 * Is thrown when the stock is insufficient for a certain operation.
 */
public class InsufficientStockException extends Exception {
    public InsufficientStockException() {
    }

    public InsufficientStockException(String message) {
        super(message);
    }

    public InsufficientStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientStockException(Throwable cause) {
        super(cause);
    }

    public InsufficientStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
