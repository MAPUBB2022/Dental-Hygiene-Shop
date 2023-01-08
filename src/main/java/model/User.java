package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Users can add products into their shopping carts, make orders and filter and sort products. Each user has an ID, a
 * shopping cart, a name, an email address, a phone number, a password, an address and an order history.
 */
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

    /**
     * User default constructor.
     */
    public User() {
        this.orderHistory = new ArrayList<>();
    }

    /**
     * User constructor with parameters.
     */
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

    /**
     * User to string method.
     */
    @Override
    public String toString() {
        return
                "id: " + id +
                "\ncart: " + cart +
                "\nname: " + name  +
                "\nemail: " + email  +
                "\nphone number: " + phoneNumber  +
                "\npassword: " + password +
                "\naddress: " + address;
    }

    /**
     * ID getter.
     */
    public Integer getId() {
        return id;
    }

    /**
     * ID setter.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Shopping cart getter.
     */
    public ShoppingCart getCart() {
        return cart;
    }

    /**
     * Shopping cart setter.
     */
    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }

    /**
     * Name getter.
     */
    public String getName() {
        return name;
    }

    /**
     * Name setter.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Email getter.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Email setter.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Phone number getter.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Phone number setter.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Password getter.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Password setter.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Address getter.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Address setter.
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Order history getter.
     */
    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    /**
     * Order history setter.
     */
    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

}
