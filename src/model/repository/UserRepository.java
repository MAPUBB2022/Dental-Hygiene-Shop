package model.repository;

import model.Address;
import model.Order;
import model.RegisteredUser;

import java.util.List;

public interface UserRepository extends ICrudRepository<Integer, RegisteredUser>{
    public void modifyName(String newName);
    public void modifyEmail(String newEmail);
    public void modifyPhoneNumber(String newPhoneNumber);
    public void modifyPassword(String newPassword);
    public void modifyAddresses(Address newAddress);
    public void modifyDefaultAddressId(int newDefaultAddressId);
    public void modifyOrderHistory(List<Order> newOrderHistory);
}
