package repository.databaseRepo;

import model.Product;
import model.ProductType;
import model.ProductUse;
import repository.ProductRepository;

public class JdbcProductRepository implements ProductRepository {
    @Override
    public void add(Product product) {

    }

    @Override
    public void delete(Integer ID) {

    }
    @Override
    public Product findById(Integer ID) {
        return null;
    }

    @Override
    public void modifyName(Integer ID, String newName) {

    }

    @Override
    public void modifyBasePrice(Integer ID, float newBasePrice) {

    }

    @Override
    public void modifyStock(Integer ID, int newStock) {

    }

    @Override
    public void modifyType(Integer ID, ProductType newType) {

    }

    @Override
    public void modifyUse(Integer ID, ProductUse newUse) {

    }
}
