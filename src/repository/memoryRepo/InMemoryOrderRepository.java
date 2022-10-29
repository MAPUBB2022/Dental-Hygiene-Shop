package repository.memoryRepo;

import model.Order;
import repository.OrderRepository;
import repository.UserRepository;

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
