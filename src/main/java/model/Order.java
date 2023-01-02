package model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Users can place orders. Orders have an ID, a date and time, a list of products, a delivery address and the ID of
 * the user who placed them.
 */
public class Order {
    public static int idCounter = 0;

    private Integer id;
    private LocalDateTime dateTime;
    private Integer userId;

    private List<ProductOrder> products;

    private Address deliveryAddress;

    private double price;

    /**
     * Price getter.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Price setter.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Date time getter.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Date time setter.
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * User ID getter.
     */
    public Integer getUserId() {
        return userId;
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
     * Delivery address getter.
     */
    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * Delivery address setter.
     */
    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    /**
     * Order constructor with parameters.
     */
    public Order(LocalDateTime dateTime, Integer userId, Address deliveryAddress, List<ProductOrder> products) {
        idCounter++;
        this.id = idCounter;
        this.dateTime = dateTime;
        this.userId = userId;
        this.deliveryAddress = deliveryAddress;
        this.products = products;
        this.price = calculatePrice();
    }

    /**
     * This method finds a product order by ID.
     * @param id The ID of the product order we are looking for.
     * @return The product order if found, null otherwise.
     */
    public ProductOrder findById(Integer id) {
        for (ProductOrder p : this.products) {
            if (p.getProductId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Searches for a product by ID and deletes it if found.
     * @param id The ID of the product we want to remove.
     */
    public void deleteProduct(Integer id) {
        ProductOrder product = findById(id);
        products.remove(product);
    }

    /**
     * This method calculates the price of a number of products of the same type.
     * @return The price of the products in question.
     */
    public double calculatePrice() {
        double s = 0;
        for (ProductOrder p : products) {
            s += p.getPrice() * p.getQuantity();
        }
        return s;
    }

    /**
     * Order to string method.
     */
    @Override
    public String toString() {
        return "\nOrder " +
                "id: " + id +
                "\nuserId: " + userId +
                "\ndate, time: " + dateTime.toString() +
                "\ntotal price: " + price +
                "\ndelivery address: " + deliveryAddress +
                "\nproducts: " +
                productsToString()
                + '\n';
    }

    /**
     * Products to string method.
     */
    public String productsToString() {
        StringBuilder pList = new StringBuilder("");
        for (ProductOrder p : products) {
            pList.append("\n");
            pList.append(p);
        }
        return new String(pList);
    }
}
