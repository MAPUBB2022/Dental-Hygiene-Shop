package model;

/**
 * Is thrown when the product used in a certain operation is not in the product repository.
 */
public class ProductNotInRepositoryException extends Exception{
    public ProductNotInRepositoryException() {
    }

    public ProductNotInRepositoryException(String message) {
        super(message);
    }

    public ProductNotInRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNotInRepositoryException(Throwable cause) {
        super(cause);
    }

    public ProductNotInRepositoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
