package model;

import javax.persistence.*;
import java.util.List;

public class User {
    private static int idCounter = 0;
    private Integer id;
    private ShoppingCart cart;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private Address address;
    private List<Order> orderHistory;

    public User() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    /*
    public RegisteredUser(String password) {
        super();
        idCounter ++;
        this.setId(idCounter);
        this.password = password;
        this.addresses = new ArrayList<>();
        this.orderHistory = new ArrayList<>();
        this.defaultAddressId = null;
    }*/

    public User(String name, String email, String phoneNumber, String password,
                Address address, List<Order> orderHistory) {
        this.cart = new ShoppingCart();
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        idCounter++;
        this.setId(idCounter);
        this.password = password;
        this.address = address;
        this.orderHistory = orderHistory;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", cart=" + cart +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", defaultAddressId=" + address +
                ", orderHistory=" + orderHistory +
                '}';
    }
}
