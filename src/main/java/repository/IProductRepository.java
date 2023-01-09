package repository;

import model.Product;

import java.util.List;

public interface IProductRepository extends ICrudRepository<Integer, Product> {

    /**
     * Product list getter.
     */
    public List<Product> getProductList();

    /**
     * Product list setter.
     */
    public void setProductList(List<Product> productList);

    /**
     * This method adds a product to the product list.
     *
     * @param product the product.
     */
    public void add(Product product);

    /**
     * This method deletes a product from the product list.
     *
     * @param id the ID of the product.
     */
    public void delete(Integer id);

    /**
     * This method finds a product by its ID.
     *
     * @param id the ID of the product
     * @return the product.
     */
    public Product findById(Integer id);

    /**
     * This method modifies a product by changing it with a product with the same attributes, except the one/s to modify.
     *
     * @param id         the id of the product.
     * @param newProduct the new product.
     */
    public void modify(Integer id, Product newProduct);

    /**
     * This method sets the stock of a product.
     *
     * @param product the product.
     * @param stock   the stock.
     */
    public void setStockOfProduct(Product product, Integer stock);

}
