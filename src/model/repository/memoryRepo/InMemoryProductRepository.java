package model.repository.memoryRepo;

import model.Product;
import model.RegisteredUser;
import model.repository.ProductRepository;

import java.util.List;
import java.util.Objects;

public class InMemoryProductRepository implements ProductRepository {

    private List<Product> productList;

    public InMemoryProductRepository(List<Product> productList) {
        this.productList = productList;
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
    public void modifyType(Integer ID, String newType) {
        Product product = this.findById(ID);
        product.setType(newType);
    }

    @Override
    public void modifyUse(Integer ID, String newUse) {
        Product product = this.findById(ID);
        product.setUse(newUse);
    }
}
