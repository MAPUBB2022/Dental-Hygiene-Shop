package model.repository;

import model.Product;

public interface ProductRepository extends ICrudRepository<Integer, Product> {
    public void modifyName(Integer ID, String newName);

    public void modifyBasePrice(Integer ID, float newBasePrice);

    public void modifyStock(Integer ID, int newStock);

    public void modifyType(Integer ID, String newType);

    public void modifyUse(Integer ID, String newUse);

}
