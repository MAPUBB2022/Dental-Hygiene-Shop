package model.repository.memoryRepo;

import model.Order;
import model.repository.OrderRepository;

public class InMemoryOrderRepository implements OrderRepository {
    @Override
    public void add(Order order) {
    }

    @Override
    public void delete(Integer ID) {

    }

    @Override
    public void update(Integer ID, Order order) {
    }

    @Override
    public Order findById(Integer ID) {
        return null;
    }
}
