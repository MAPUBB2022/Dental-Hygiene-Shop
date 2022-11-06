package model;

public abstract class User {
    private ShoppingCart cart;
    private String name;
    private String email;
    private String phoneNumber;

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

    public abstract void placeOrder(Order order);
}
