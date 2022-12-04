package repository.memoryRepo;

import model.Address;
import model.Order;
import model.ProductOrder;
import repository.IOrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InMemoryOrderRepository implements IOrderRepository {
    @Override
    public List<Order> getOrderList() {
        return orderList;
    }

    private List<Order> orderList;

    public InMemoryOrderRepository() {
        this.orderList = new ArrayList<>();
    }

    @Override
    public void add(Order order) {
        orderList.add(order);
    }

    @Override
    public void delete(Integer ID) {
        Order order = findById(ID);
        orderList.remove(order);
    }

    @Override
    public Order findById(Integer ID) {

        for (Order o : orderList) {
            if (Objects.equals(o.getId(), ID)) {
                return o;
            }
        }
        return null;
    }

    public void modifyProducts(Integer ID, List<ProductOrder> products) {
        Order order = this.findById(ID);
        order.setProducts(products);
    }

    public void modifyDeliveryAddress(Integer ID, Address newDeliveryAddress) {
        Order order = this.findById(ID);
        order.setDeliveryAddress(newDeliveryAddress);
    }

}
