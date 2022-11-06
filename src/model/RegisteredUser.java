package model;

import java.util.ArrayList;
import java.util.List;

public class RegisteredUser extends User {
    private static int idCounter = 0;
    private String password;
    private List<Address> addresses;
    private Integer defaultAddressId;
    private List<Order> orderHistory;

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
        this.setId(idCounter);
        this.password = password;
        this.addresses = new ArrayList<>();
        this.orderHistory = new ArrayList<>();
        this.defaultAddressId = null;
    }


}
