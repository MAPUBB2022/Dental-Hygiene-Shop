package repository.memoryRepo;

import model.Product;
import repository.ProductRepository;

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
}
