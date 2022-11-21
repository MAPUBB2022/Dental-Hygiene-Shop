package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShoppingCart {
    private List<ProductOrder> products;

    public List<ProductOrder> getProducts() {
        return products;
    }

    public void setProducts(List<ProductOrder> products) {
        this.products = products;
    }

    public ShoppingCart() {
        products = new ArrayList<>();
    }

    public void addProduct(ProductOrder product) {
        products.add(product);
    }

    public ProductOrder findById(Integer id) {
        for (ProductOrder p : products) {
            if (Objects.equals(p.getProductId(), id)) {
                return p;
            }
        }
        return null;
    }
}