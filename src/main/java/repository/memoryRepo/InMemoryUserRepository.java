package repository.memoryRepo;

import model.Address;
import model.Order;
import model.RegisteredUser;
import repository.IUserRepository;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InMemoryUserRepository implements IUserRepository {
    private List<RegisteredUser> userList;

    public InMemoryUserRepository() {
        this.populateUsers();
    }

    public void populateUsers() {
        this.userList = new ArrayList<>();
        Address address = new Address("USA", "NY", "NY", "5h Ave", "12", "12345");
        address.setId(1);
        List<Address> addrList = new ArrayList<>();
        addrList.add(address);
        RegisteredUser user1 = new RegisteredUser("Ana", "ana@yahoo.com", "01234",
                "psswd", addrList, 1, new ArrayList<Order>());
        this.userList.add(user1);
    }

    public InMemoryUserRepository(List<RegisteredUser> userList) {
        this.userList = userList;
    }

    public List<RegisteredUser> getUserList() {
        return userList;
    }

    public void setUserList(List<RegisteredUser> userList) {
        this.userList = userList;
    }

    @Override
    public void add(RegisteredUser registeredUser) {
        userList.add(registeredUser);
    }

    @Override
    public void delete(Integer ID) {
        RegisteredUser registeredUser = this.findById(ID);
        userList.remove(registeredUser);
    }

    public RegisteredUser findById(Integer ID) {
        for (RegisteredUser r : userList) {
            if (Objects.equals(r.getId(), ID)) {
                return r;
            }
        }
        return null;
    }

    public String findPasswordByEmail(String email) {
        for (RegisteredUser r : userList) {
            if (Objects.equals(r.getEmail(), email)) {
                return r.getPassword();
            }
        }
        return null;
    }

    public RegisteredUser findByEmail(String email) {
        for (RegisteredUser r : userList) {
            if (Objects.equals(r.getEmail(), email)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public void modifyShoppingCart(Integer ID, ShoppingCart newShoppingCart) {
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
