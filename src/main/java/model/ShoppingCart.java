package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ShoppingCart {

    static int idCounter = 0;
    Integer id;
    private List<ProductOrder> products;

    public ShoppingCart() {
        idCounter++;
        this.id = idCounter;
        products = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ProductOrder> getProducts() {
        return products;
    }

    public void setProducts(List<ProductOrder> products) {
        this.products = products;
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
