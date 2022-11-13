package model;

public class Product {

    private static int idCounter = 0;
    private Integer id;
    private String name;
    private double basePrice;

    private String size;//no spaces, no commas (dots only), no capital letters
    private int stock;

    private ProductType type;//see enum

    private ProductUse use;//see enum

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public ProductUse getUse() {
        return use;
    }

    public void setUse(ProductUse use) {
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

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }


    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Product(String name, String size, ProductType type, double basePrice, ProductUse use, int stock) {
        idCounter++;
        this.id = idCounter;
        this.name = name;
        this.size = size;
        this.basePrice = basePrice;
        this.stock = stock;
        this.type = type;
        this.use = use;
        this.description = "This product has no description yet.";
    }
}
