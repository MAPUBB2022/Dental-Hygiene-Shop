package model;

/**
 * Users view products, put them in their cards and order them. Each product has an ID, a name, a base price, a size,
 * a stock, a type and a use.
 */
public class Product {

    private static int idCounter = 0;

    private Integer id;
    private String name;
    private double basePrice;
    private String size;//no spaces, no commas (dots only), no capital letters
    private int stock;
    private ProductType type;//see enum
    private ProductUse use;//see enum

    /**
     * This is the product copy constructor. It creates a new product with the same attributes as a product given as parameter.
     * @param other The product given as a parameter for the copy constructor to copy.
     */
    public Product(Product other) {
        this.id = other.id;
        this.name = other.name;
        this.basePrice = other.basePrice;
        this.size = other.size;
        this.stock = other.stock;
        this.type = other.type;
        this.use = other.use;
    }

    /**
     * Type getter.
     */
    public ProductType getType() {
        return type;
    }

    /**
     * Type setter.
     */
    public void setType(ProductType type) {
        this.type = type;
    }

    /**
     * Use getter.
     */
    public ProductUse getUse() {
        return use;
    }

    /**
     * Use setter.
     */
    public void setUse(ProductUse use) {
        this.use = use;
    }

    /**
     * Description getter.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Description setter.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    /**
     * ID getter.
     */
    public int getId() {
        return id;
    }

    /**
     * ID setter.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Name getter.
     */
    public String getName() {
        return name;
    }

    /**
     * Name setter.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Base price getter.
     */
    public double getBasePrice() {
        return basePrice;
    }

    /**
     * Base price setter.
     */
    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    /**
     * Stock getter.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Stock setter.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Size getter.
     */
    public String getSize() {
        return size;
    }

    /**
     * Size setter.
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * Product constructor with parameters.
     */
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

    /**
     * Product to string method.
     */
    @Override
    public String toString() {
        return "\nProduct " +
                "id: " + id +
                "\nname: " + name +
                "\nbase price: " + basePrice +
                "\nsize: " + size +
                "\ntype: " + type +
                "\nuse: " + use +
                "\nstock: " + stock;
    }
}
