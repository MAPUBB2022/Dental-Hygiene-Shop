package model;

public abstract class User {
    private ShoppingCart cart;

    private Integer id;

    public User(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    private String name;
    private String email;
    private String phoneNumber;

<<<<<<< HEAD
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
=======
    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
>>>>>>> 2233cc6058bce07170462584dbc425526fcf336b
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

}
