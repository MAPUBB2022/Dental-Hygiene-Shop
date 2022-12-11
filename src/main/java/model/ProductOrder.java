package model;

import javax.persistence.Entity;
import javax.persistence.Id;

public class ProductOrder {
    private Integer productId;
    private int quantity;
    private double price;

    public ProductOrder(Integer productId, int quantity, double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return "product id: " + productId +
                "\nquantity: " + quantity +
                "\nprice: " + price;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
