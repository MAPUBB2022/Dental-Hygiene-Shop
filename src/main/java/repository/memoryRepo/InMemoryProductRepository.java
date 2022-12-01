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

public class    InMemoryProductRepository implements IProductRepository {

    private List<Product> productList;

    public InMemoryProductRepository(List<Product> productList) {
        this.productList = productList;
    }

    public InMemoryProductRepository() {
        productList = new ArrayList<>();
        populateProducts();
    }

    public void populateProducts() {
        Product p1 = new Product("Colgate Max White", "150ml", TOOTHPASTE, 3.5, HOME, 500);
        Product p2 = new Product("Colgate Max White 3D", "200ml", MOUTHWASH, 13.5, HOME, 150);
        Product p3 = new Product("Oral-B Silky", "50m", DENTAL_FLOSS, 13.5, HOME, 150);
        Product p4 = new Product("Oral-B Pro 2", "2pcs", TOOTHBRUSH, 13.5, HOME, 200);
        Product p5 = new Product("Sensodyne Repair & Protect", "100ml", TOOTHPASTE, 4, HOME, 300);
        Product p6 = new Product("Listerine Cool", "100ml", MOUTHWASH, 8, HOME, 43);
        productList.add(p1);
        productList.add(p2);
        productList.add(p3);
        productList.add(p4);
        productList.add(p5);
        productList.add(p6);
    }

    public List<Product> getProductList() {
        return productList;
    }

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

    @Override
    public void modifyName(Integer ID, String newName) {
        Product product = this.findById(ID);
        product.setName(newName);
    }

    @Override
    public void modifyBasePrice(Integer ID, float newBasePrice) {
        Product product = this.findById(ID);
        product.setBasePrice(newBasePrice);
    }

    @Override
    public void modifyStock(Integer ID, int newStock) {
        Product product = this.findById(ID);
        product.setStock(newStock);
    }

    @Override
    public void modifyType(Integer ID, ProductType newType) {
        Product product = this.findById(ID);
        product.setType(newType);
    }

    @Override
    public void modifyUse(Integer ID, ProductUse newUse) {
        Product product = this.findById(ID);
        product.setUse(newUse);
    }
}
