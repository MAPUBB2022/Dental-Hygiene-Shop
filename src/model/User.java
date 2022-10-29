package model;

public abstract class User extends Person {
    private ShoppingCart cart;

    public abstract void placeOrder();
}
