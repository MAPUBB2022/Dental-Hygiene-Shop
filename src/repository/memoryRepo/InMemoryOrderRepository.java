package repository.memoryRepo;

import model.Address;
import model.Order;
<<<<<<< HEAD:src/repository/memoryRepo/InMemoryOrderRepository.java
import repository.OrderRepository;
=======
import model.ProductOrder;
import model.repository.OrderRepository;
>>>>>>> 2233cc6058bce07170462584dbc425526fcf336b:src/model/repository/memoryRepo/InMemoryOrderRepository.java

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
    public Order findById(Integer ID) {

        for (Order o : orderList) {
            if (Objects.equals(o.getId(), ID)) {
                return o;
            }
        }
        return null;
    }

<<<<<<< HEAD:src/repository/memoryRepo/InMemoryOrderRepository.java
=======
    public void modifyProducts(Integer ID, List<ProductOrder> products){
        Order order = this.findById(ID);
        order.setProducts(products);
    }

    public void modifyDeliveryAddress(Integer ID, Address newDeliveryAddress){
        Order order = this.findById(ID);
        order.setDeliveryAddress(newDeliveryAddress);
    }
>>>>>>> 2233cc6058bce07170462584dbc425526fcf336b:src/model/repository/memoryRepo/InMemoryOrderRepository.java

}
