package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShoppingCart {

    static int idCounter = 0;
    Integer id;
    private List<ProductOrder> products;

    /**
     * Shopping cart constructor.
     */
    public ShoppingCart() {
        idCounter++;
        this.id = idCounter;
        products = new ArrayList<>();
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
     * Products getter.
     */
    public List<ProductOrder> getProducts() {
        return products;
    }

    /**
     * Products setter.
     */
    public void setProducts(List<ProductOrder> products) {
        this.products = products;
    }

    /**
     * This method adds a product to the shopping cart.
     *
     * @param product the product.
     */
    public void addProduct(ProductOrder product) {
        products.add(product);
    }

    /**
     * This method finds a product order by ID.
     *
     * @param id the ID of the product order.
     * @return the product order if found, null otherwise.
     */
    public ProductOrder findById(Integer id) {
        for (ProductOrder p : products) {
            if (Objects.equals(p.getProductId(), id)) {
                return p;
            }
        }
        return null;
    }
}
