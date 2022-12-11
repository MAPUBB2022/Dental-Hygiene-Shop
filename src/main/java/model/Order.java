package model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
    public static int idCounter = 0;

    private Integer id;
    private LocalDateTime dateTime;
    private Integer userId;

    private List<ProductOrder> products;

    private Address deliveryAddress;

    private double price;

    public Order() {
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

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
        this.price = calculatePrice();
    }

    public ProductOrder findById(Integer id) {
        for (ProductOrder p : this.products) {
            if (p.getProductId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public void deleteProduct(Integer id) {
        ProductOrder product = findById(id);
        products.remove(product);
    }

    public double calculatePrice() {
        double s = 0;
        for (ProductOrder p : products) {
            s += p.getPrice() * p.getQuantity();
        }
        return s;
    }


    @Override
    public String toString() {
        return "\nOrder " +
                "id: " + id +
                "\ndate, time: " + dateTime.toString() +
                "\ntotal price: " + price +
                "\ndelivery address: " + deliveryAddress +
                "\nproducts: " +
                productsToString();
    }

    public String productsToString() {
        StringBuilder pList = new StringBuilder("");
        for (ProductOrder p : products) {
            pList.append("\n");
            pList.append(p);
        }
        return new String(pList);
    }
}
