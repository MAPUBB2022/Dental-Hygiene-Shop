package model.repository.memoryRepo;

import model.Product;
import model.repository.ProductRepository;

public class InMemoryProductRepository implements ProductRepository {
    @Override
    public void add(Product product) {

    }

    @Override
    public void delete(Integer ID) {

    }

    @Override
    public void update(Integer ID, Product product) {

    }

    @Override
    public Product findById(Integer ID) {
        return null;
    }

    @Override
    public void modifyName(String newName) {

    }

    @Override
    public void modifyBasePrice(float newBasePrice) {

    }

    @Override
    public void modifyStock(int newStock) {

    }

    @Override
    public void modifyType(String newType) {

    }

    @Override
    public void modifyUse(String newUse) {

    }
}
