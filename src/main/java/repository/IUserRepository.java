package repository;

import model.Address;
import model.Order;
import model.RegisteredUser;
import model.ShoppingCart;

import java.util.List;

public interface IUserRepository extends ICrudRepository<Integer, RegisteredUser> {

    public List<RegisteredUser> getUserList();

    public void setUserList(List<RegisteredUser> userList);

    public void add(RegisteredUser registeredUser);

    public void delete(Integer ID);

    public RegisteredUser findById(Integer ID);

    public String findPasswordByEmail(String email);

    public RegisteredUser findByEmail(String email);

    public void modifyShoppingCart(Integer ID, ShoppingCart newShoppingCart);

    public void modifyName(Integer ID, String newName);

    public void modifyEmail(Integer ID, String newEmail);

    public void modifyPhoneNumber(Integer ID, String newPhoneNumber);

    public void modifyPassword(Integer ID, String newPassword);

    public void modifyAddresses(Integer ID, List<Address> newAddresses);

    public void modifyDefaultAddressId(Integer ID, int newDefaultAddressId);

    public void modifyOrderHistory(Integer ID, List<Order> newOrderHistory);
}
