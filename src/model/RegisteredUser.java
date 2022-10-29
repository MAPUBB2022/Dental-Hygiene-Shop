package model;

import java.util.ArrayList;
import java.util.List;

public class RegisteredUser extends User {
    private static int idCounter = 0;
    private Integer id;
    private String password;
    private List<Address> addresses;
    private Integer defaultAddressId;
    private List<Order> orderHistory;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public int getDefaultAddressId() {
        return defaultAddressId;
    }

    public void setDefaultAddressId(int defaultAddressId) {
        this.defaultAddressId = defaultAddressId;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public RegisteredUser(String password) {
        idCounter ++;
        this.id = idCounter;
        this.password = password;
        this.addresses = new ArrayList<>();
        this.orderHistory = new ArrayList<>();
        this.defaultAddressId = null;
    }

    @Override
    public void placeOrder() {
        //add order to orderRepo list
        //add order to customer's order history
    }

}
