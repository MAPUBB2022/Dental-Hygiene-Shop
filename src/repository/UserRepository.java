package repository;

import model.Address;
import model.Order;
import model.RegisteredUser;

import java.util.List;

public interface UserRepository extends ICrudRepository<Integer, RegisteredUser>{
    public void modifyName(Integer ID, String newName);
    public void modifyEmail(Integer ID, String newEmail);
    public void modifyPhoneNumber(Integer ID, String newPhoneNumber);
    public void modifyPassword(Integer ID, String newPassword);
    public void modifyAddresses(Integer ID, List<Address> newAddresses);
    public void modifyDefaultAddressId(Integer ID, int newDefaultAddressId);
    public void modifyOrderHistory(Integer ID, List<Order> newOrderHistory);
}
