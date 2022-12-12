package repository.databaseRepo;

import model.Product;
import model.ProductType;
import model.ProductUse;
import repository.IProductRepository;

import java.util.List;

public class JdbcProductRepository implements IProductRepository {
    @Override
    public List<Product> getProductList() {
        return null;
    }

    @Override
    public void setProductList(List<Product> productList) {

    }
    //will you rename yourself now plsssss
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
    public void modify(Integer id, Product newProduct) {

    }

}
