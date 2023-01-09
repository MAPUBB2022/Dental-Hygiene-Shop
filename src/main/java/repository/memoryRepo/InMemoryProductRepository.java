package repository.memoryRepo;

import model.Product;
import model.ProductType;
import model.ProductUse;
import repository.IProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static model.ProductType.*;
import static model.ProductUse.*;

public class InMemoryProductRepository implements IProductRepository {

    private List<Product> productList;

    /**
     * Constructor with a parameter for the product list.
     */
    public InMemoryProductRepository(List<Product> productList) {
        this.productList = productList;
    }

    /**
     * Constructor which initializes the product list and populates it.
     */
    public InMemoryProductRepository() {
        productList = new ArrayList<>();
        populateProducts();
    }

    /**
     * This method populates the product list with some data.
     */
    public void populateProducts() {
        Product p1 = new Product("Colgate Max White", "150ml", TOOTHPASTE, 3.5, HOME, 500);
        Product p2 = new Product("Colgate Max White 3D", "200ml", MOUTHWASH, 13.5, HOME, 150);
        Product p3 = new Product("Oral-B Silky", "50m", DENTAL_FLOSS, 10.5, HOME, 150);
        Product p4 = new Product("Oral-B Pro 2", "2pcs", TOOTHBRUSH, 20, HOME, 200);
        Product p5 = new Product("Sensodyne Repair & Protect", "100ml", TOOTHPASTE, 4, HOME, 300);
        Product p6 = new Product("Listerine Cool", "100ml", MOUTHWASH, 8, HOME, 43);
        productList.add(p1);
        productList.add(p2);
        productList.add(p3);
        productList.add(p4);
        productList.add(p5);
        productList.add(p6);
    }

    /**
     * Product list getter.
     */
    public List<Product> getProductList() {
        return productList;
    }

    /**
     * Product list setter.
     */
    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public void add(Product product) {
        productList.add(product);
    }

    @Override
    public void delete(Integer ID) {
        Product product = this.findById(ID);
        productList.remove(product);
    }

    @Override
    public Product findById(Integer ID) {
        for (Product p : productList) {
            if (Objects.equals(p.getId(), ID)) {
                return p;
            }
        }
        return null;
    }

    /**
     * This method modifies a product by replacing it with another product with the same attributes, except the one/s to modify.
     *
     * @param id         the id of the product.
     * @param newProduct the new product.
     */
    public void modify(Integer id, Product newProduct) {
        Product product = this.findById(id);
        newProduct.setId(product.getId());
        product = newProduct;
    }

    @Override
    public void setStockOfProduct(Product product, Integer stock) {
        product.setStock(stock);
    }
}
