package model;

import java.util.List;

public class RegisteredUser extends User {
    private Integer id;
    private String password;
    private List<Address> addresses;
    private int defaultAddressId;
    List<Order> orderHistory;

    @Override
    public void placeOrder() {
        //add order to orderRepo list
        //add order to customer's order history
    }

}
