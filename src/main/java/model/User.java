package model;

import java.util.List;

public class User {
    private static int idCounter = 0;

    private Integer id;

    private ShoppingCart cart;

    private String name;

    private String email;

    private String phoneNumber;

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

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void addAddress(Address address) {
        this.addresses.add(address);
    }

    public Address findAddressById(Integer addressId) {
        for (Address a : this.addresses) {
            if
            (a.getId() == addressId) {
                return a;
            }
        }
        return null;
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

    public User(String name, String email, String phoneNumber, String password, List<Address> addresses,
                Integer defaultAddressId, List<Order> orderHistory) {
        this.cart = new ShoppingCart();
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        idCounter++;
        this.setId(idCounter);
        this.password = password;
        this.addresses = addresses;
        this.defaultAddressId = defaultAddressId;
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
                ", addresses=" + addresses +
                ", defaultAddressId=" + defaultAddressId +
                ", orderHistory=" + orderHistory +
                '}';
    }
}
