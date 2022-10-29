package model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    public static int idCounter = 0;
    private Integer id;
    private LocalDateTime dateTime;
    private Integer userId;
    private List<ProductOrder> products;
    private Address deliveryAddress;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ProductOrder> getProducts() {
        return products;
    }

    public void setProducts(List<ProductOrder> products) {
        this.products = products;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Order(LocalDateTime dateTime, Integer userId, Address deliveryAddress, List<ProductOrder> products) {
        idCounter++;
        this.id = idCounter;
        this.dateTime = dateTime;
        this.userId = userId;
        this.deliveryAddress = deliveryAddress;
        this.products = products;
    }
}
