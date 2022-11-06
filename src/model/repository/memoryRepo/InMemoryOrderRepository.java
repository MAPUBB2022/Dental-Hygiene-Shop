package model.repository.memoryRepo;

import model.Order;
import model.repository.OrderRepository;

import java.util.List;
import java.util.Objects;

public class InMemoryOrderRepository implements OrderRepository {
    private List<Order> orderList;

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
    public void update(Integer ID, Order newOrder) {
        //replaces a given order with a new order
        this.delete(ID);
        this.add(newOrder);
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


}
