package model.repository;

import model.Product;

public interface ProductRepository extends ICrudRepository<Integer, Product> {
    public void modifyName(String newName);

    public void modifyBasePrice(float newBasePrice);

    public void modifyStock(int newStock);

    public void modifyType(String newType);

    public void modifyUse(String newUse);

}
