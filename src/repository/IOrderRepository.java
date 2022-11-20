package repository;

import model.Address;
import model.Order;
import model.ProductOrder;

import java.util.List;

public interface IOrderRepository extends ICrudRepository<Integer, Order>{

    public void modifyProducts(Integer ID, List<ProductOrder> products);
    public void modifyDeliveryAddress(Integer ID, Address newDeliveryAddress);
}
