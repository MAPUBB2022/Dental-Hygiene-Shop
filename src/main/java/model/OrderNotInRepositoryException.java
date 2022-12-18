package model;

/**
 * Is thrown when the order used in a certain operation is not in the order repository.
 */
public class OrderNotInRepositoryException extends Exception{
    public OrderNotInRepositoryException() {
    }

    public OrderNotInRepositoryException(String message) {
        super(message);
    }

    public OrderNotInRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderNotInRepositoryException(Throwable cause) {
        super(cause);
    }

    public OrderNotInRepositoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
