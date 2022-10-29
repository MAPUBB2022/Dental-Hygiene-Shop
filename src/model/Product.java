package model;

public class Product {

    private static int idCounter = 0;
    private Integer id;
    private String name;
    private float basePrice;
    private int stock;

    private String type;

    private String use;//home or medical

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(float basePrice) {
        this.basePrice = basePrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Product(String name, float basePrice, int stock, String type, String use) {
        idCounter++;
        this.id = idCounter;
        this.name = name;
        this.basePrice = basePrice;
        this.stock = stock;
        this.type = type;
        this.use = use;
        this.description = "This product has no description yet.";
    }
}
