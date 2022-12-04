package repository;

import model.Address;
import model.Order;
import model.User;
import model.ShoppingCart;

import java.util.List;

public interface IUserRepository extends ICrudRepository<Integer, User> {

    public List<User> getUserList();

    public void setUserList(List<User> userList);

    public void add(User user);

    public void delete(Integer ID);

    public User findById(Integer ID);

    public String findPasswordByEmail(String email);

    public User findByEmail(String email);

    public void modifyShoppingCart(Integer ID, ShoppingCart newShoppingCart);

    public void modifyName(Integer ID, String newName);

    public void modifyEmail(Integer ID, String newEmail);

    public void modifyPhoneNumber(Integer ID, String newPhoneNumber);

    public void modifyPassword(Integer ID, String newPassword);

    public void modifyOrderHistory(Integer ID, List<Order> newOrderHistory);
}
