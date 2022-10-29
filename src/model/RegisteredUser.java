package model;

import java.util.List;

public class RegisteredUser extends User {
    private String password;
    private List<Address> addresses;
    private int defaultAddressId;
    List<Order> orderHistory;

    @Override
    public void placeOrder() {

    }

    @Override
    public void addToCart() {

    }

    @Override
    public void removeFromCart() {

    }

    public boolean deleteAccount(){
        return false;
    }

    public boolean modifyAccount(){
        return false;
    }
}
