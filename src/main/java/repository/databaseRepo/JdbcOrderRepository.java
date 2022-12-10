package repository.databaseRepo;

import model.Address;
import model.Order;
import model.ProductOrder;
import repository.IOrderRepository;

import java.util.List;

public class JdbcOrderRepository implements IOrderRepository {
    @Override
    public List<Order> getOrderList() {
        return null;
    }

    @Override
    public void add(Order order) {

    }

    @Override
    public void delete(Integer ID) {

    }


    @Override
    public Order findById(Integer ID) {
        return null;
    }

    @Override
    public void modifyProducts(Integer ID, List<ProductOrder> products) {

    }

    @Override
    public void modifyDeliveryAddress(Integer ID, Address newDeliveryAddress) {

    }
}
