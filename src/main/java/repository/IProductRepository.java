package repository;

import model.Product;

import java.util.List;

public interface IProductRepository extends ICrudRepository<Integer, Product> {

    public List<Product> getProductList();

    public void setProductList(List<Product> productList);

    public void add(Product product);

    public void delete(Integer ID);

    public Product findById(Integer ID);

    public void modify(Integer id, Product newProduct);

    public void setStockOfProduct(Product product, Integer stock);

}
