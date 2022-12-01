package repository;

import model.Product;
import model.ProductType;
import model.ProductUse;

import java.util.List;

public interface IProductRepository extends ICrudRepository<Integer, Product> {

    public List<Product> getProductList();

    public void setProductList(List<Product> productList);

    public void add(Product product);

    public void delete(Integer ID);

    public Product findById(Integer ID);

    public void modifyName(Integer ID, String newName);

    public void modifyBasePrice(Integer ID, float newBasePrice);

    public void modifyStock(Integer ID, int newStock);

    public void modifyType(Integer ID, ProductType newType);

    public void modifyUse(Integer ID, ProductUse newUse);

}
