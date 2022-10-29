package model;

public abstract class User {
    private ShoppingCart cart;
    public abstract void placeOrder();
    public abstract void addToCart();
    public abstract void removeFromCart();
}
