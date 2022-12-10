package repository.memoryRepo;

import model.Address;
import model.Order;
import model.ProductOrder;
import repository.IOrderRepository;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class InMemoryOrderRepository implements IOrderRepository {
    @Override
    public List<Order> getOrderList() {
        return orderList;
    }

    void populate() {
        List<ProductOrder> prodList = new ArrayList<>();
        prodList.add(new ProductOrder(1, 4, 7.8));
        prodList.add(new ProductOrder(4, 8, 28));
        prodList.add(new ProductOrder(6, 9, 54));
        Address a1 = new Address("romania", "MM", "BM", "bd Unirii", "17", "423243");
        Address a2 = new Address("romania", "MS", "TgM", "bd Republicii", "15", "425443");
        Order o1 = new Order(LocalDateTime.now(), 1, a1, prodList);
        prodList.remove(2);
        LocalDateTime dt = LocalDateTime.of(2019, 7, 4, 15, 40, 5);
        Order o2 = new Order(dt, 1, a2, prodList);
        orderList.add(o1);
        orderList.add(o2);
    }

    private List<Order> orderList;

    public InMemoryOrderRepository() {
        this.orderList = new ArrayList<>();
        populate();
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

    @Override
    public void modifyProducts(Integer ID, List<ProductOrder> products) {
        Order order = this.findById(ID);
        order.setProducts(products);
    }

    @Override
    public void modifyDeliveryAddress(Integer ID, Address newDeliveryAddress) {
        Order order = this.findById(ID);
        order.setDeliveryAddress(newDeliveryAddress);
    }

}
