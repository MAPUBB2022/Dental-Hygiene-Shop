package model;

/**
 * This class resolves the many to many relationship between the Product and Order classes.
 */
public class ProductOrder {
    private Integer productId;
    private int quantity;
    private double price;

    /**
     * Product order constructor with parameters.
     */
    public ProductOrder(Integer productId, int quantity, double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Product order to string method.
     */
    @Override
    public String toString() {
        return "product id: " + productId +
                "\nquantity: " + quantity +
                "\nprice: " + price;
    }

    /**
     * Product ID getter.
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * Product ID setter.
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * Quantity getter.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Quantity setter.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Price getter.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Price setter.
     */
    public void setPrice(double price) {
        this.price = price;
    }
}
