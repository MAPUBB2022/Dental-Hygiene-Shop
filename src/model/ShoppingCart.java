package model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public ShoppingCart() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }
}
