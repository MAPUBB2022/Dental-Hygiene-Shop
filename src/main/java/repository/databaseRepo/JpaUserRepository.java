package repository.databaseRepo;

import model.Address;
import model.Order;
import model.User;
import model.ShoppingCart;
import repository.IUserRepository;


import java.util.List;

public class JpaUserRepository implements IUserRepository {
    @Override
    public List<User> getUserList() {
        return null;
    }

    @Override
    public void setUserList(List<User> userList) {

    }

    @Override
    public void add(User user) {

    }

    @Override
    public void delete(Integer ID) {

    }

    @Override
    public User findById(Integer ID) {
        return null;
    }

    @Override
    public String findPasswordByEmail(String email) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public void modifyShoppingCart(Integer ID, ShoppingCart newShoppingCart){}


    @Override
    public void modifyName(Integer ID, String newName) {

    }

    @Override
    public void modifyEmail(Integer ID, String newEmail) {

    }

    @Override
    public void modifyPhoneNumber(Integer ID, String newPhoneNumber) {

    }

    @Override
    public void modifyPassword(Integer ID, String newPassword) {

    }

    @Override
    public void modifyAddresses(Integer ID, List<Address> newAddresses) {

    }

    @Override
    public void modifyDefaultAddressId(Integer ID, int newDefaultAddressId) {

    }

    @Override
    public void modifyOrderHistory(Integer ID, List<Order> newOrderHistory) {

    }
}