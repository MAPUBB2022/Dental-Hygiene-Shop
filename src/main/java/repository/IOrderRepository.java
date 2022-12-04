package repository;

import model.Address;
import model.Order;
import model.ProductOrder;

import java.util.List;

public interface IOrderRepository extends ICrudRepository<Integer, Order> {

    public List<Order> getOrderList();

    public void add(Order order);

    public void delete(Integer ID);

    public Order findById(Integer ID);

    abstract void modifyProducts(Integer ID, List<ProductOrder> products);

    abstract void modifyDeliveryAddress(Integer ID, Address newDeliveryAddress);
}
