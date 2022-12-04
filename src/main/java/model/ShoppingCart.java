package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class ShoppingCart {
    @Id
    Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToMany(cascade = CascadeType.ALL, targetEntity = ProductOrder.class)
    @JoinColumn(name = "cart_id")
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
