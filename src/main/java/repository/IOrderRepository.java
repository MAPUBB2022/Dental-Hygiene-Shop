package repository;

import model.Address;
import model.Order;

import java.util.List;

public interface IOrderRepository extends ICrudRepository<Integer, Order> {

    public List<Order> getOrderList();

    public void add(Order order);

    public void delete(Integer ID);

    public Order findById(Integer ID);

    abstract void modifyProducts(Integer orderId, Integer productId);

    abstract void modifyDeliveryAddress(Integer ID, Address newDeliveryAddress);
}
