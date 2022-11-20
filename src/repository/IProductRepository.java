package repository;

import model.Product;
import model.ProductType;
import model.ProductUse;

public interface IProductRepository extends ICrudRepository<Integer, Product> {
    public void modifyName(Integer ID, String newName);

    public void modifyBasePrice(Integer ID, float newBasePrice);

    public void modifyStock(Integer ID, int newStock);

    public void modifyType(Integer ID, ProductType newType);

    public void modifyUse(Integer ID, ProductUse newUse);

}
