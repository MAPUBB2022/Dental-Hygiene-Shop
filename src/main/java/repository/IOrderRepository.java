package repository;

import model.Address;
import model.Order;
import model.User;

import java.util.List;

public interface IOrderRepository extends ICrudRepository<Integer, Order> {

    /**
     * Order list getter.
     */
    public List<Order> getOrderList();

    /**
     * This method adds an order to the order list.
     *
     * @param order the order.
     */
    public void add(Order order);

    /**
     * This method deletes an order from the order list.
     *
     * @param id the ID of the order.
     */
    public void delete(Integer id);

    /**
     * This method finds a user by their ID.
     *
     * @param id the id of the user.
     * @return the user.
     */
    public Order findById(Integer id);

    /**
     * This method deletes a product from an order.
     *
     * @param orderId   the id of the order.
     * @param productId the id of the product.
     */
    abstract void modifyProducts(Integer orderId, Integer productId);

    /**
     * This method modifies the delivery address of an order.
     *
     * @param id                 the id of the order.
     * @param newDeliveryAddress the new delivery address.
     */
    abstract void modifyDeliveryAddress(Integer id, Address newDeliveryAddress);

    /**
     * This method places an order for a user.
     *
     * @param user  the user.
     * @param order the order.
     */
    void placeOrder(User user, Order order);
}
