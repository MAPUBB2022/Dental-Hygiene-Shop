package model.repository.memoryRepo;

import model.*;
import model.repository.UserRepository;

import java.util.List;
import java.util.Objects;

public class InMemoryUserRepository implements UserRepository {

    private List<RegisteredUser> registeredUserList;

    public InMemoryUserRepository(List<RegisteredUser> registeredUserList) {
        this.registeredUserList = registeredUserList;
    }

    public List<RegisteredUser> getRegisteredUserList() {
        return registeredUserList;
    }

    public void setRegisteredUserList(List<RegisteredUser> registeredUserList) {
        this.registeredUserList = registeredUserList;
    }

    @Override
    public void add(RegisteredUser registeredUser) {
        registeredUserList.add(registeredUser);
    }

    @Override
    public void delete(Integer ID) {
        RegisteredUser registeredUser = this.findById(ID);
        registeredUserList.remove(registeredUser);
    }

    @Override
    public RegisteredUser findById(Integer ID) {
        for (RegisteredUser r : registeredUserList) {
            if (Objects.equals(r.getId(), ID)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public void modifyShoppingCart(Integer ID, ShoppingCart newShoppingCart){
        RegisteredUser registeredUser = this.findById(ID);
        registeredUser.setCart(newShoppingCart);
    }

    @Override
    public void modifyName(Integer ID, String newName) {
        RegisteredUser registeredUser = this.findById(ID);
        registeredUser.setName(newName);
    }

    @Override
    public void modifyEmail(Integer ID, String newEmail) {
        RegisteredUser registeredUser = this.findById(ID);
        registeredUser.setEmail(newEmail);
    }

    @Override
    public void modifyPhoneNumber(Integer ID, String newPhoneNumber) {
        RegisteredUser registeredUser = this.findById(ID);
        registeredUser.setPhoneNumber(newPhoneNumber);
    }

    @Override
    public void modifyPassword(Integer ID, String newPassword) {
        RegisteredUser registeredUser = this.findById(ID);
        registeredUser.setPassword(newPassword);
    }

    @Override
    public void modifyAddresses(Integer ID, List<Address> newAddresses) {
        RegisteredUser registeredUser = this.findById(ID);
        registeredUser.setAddresses(newAddresses);
    }

    @Override
    public void modifyDefaultAddressId(Integer ID, int newDefaultAddressId) {
        RegisteredUser registeredUser = this.findById(ID);
        registeredUser.setDefaultAddressId(newDefaultAddressId);
    }

    @Override
    public void modifyOrderHistory(Integer ID, List<Order> newOrderHistory) {
        RegisteredUser registeredUser = this.findById(ID);
        registeredUser.setOrderHistory(newOrderHistory);
    }
}
