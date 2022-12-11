package repository.memoryRepo;

import model.Address;
import model.Order;
import model.User;
import repository.IUserRepository;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InMemoryUserRepository implements IUserRepository {
    private List<User> userList;

    public InMemoryUserRepository() {
        this.populateUsers();
    }

    public void populateUsers() {
        this.userList = new ArrayList<>();
        Address address = new Address("USA", "NY", "NY", "5h Ave", "12", "12345");
        address.setId(1);
        User user1 = new User("Ana", "ana@yahoo.com", "01234",
                "psswd", address, new ArrayList<Order>());
        this.userList.add(user1);
    }

    public InMemoryUserRepository(List<User> userList) {
        this.userList = userList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public void add(User user) {
        userList.add(user);
    }

    @Override
    public void delete(Integer ID) {
        User user = this.findById(ID);
        userList.remove(user);
    }

    public User findById(Integer ID) {
        for (User r : userList) {
            if (Objects.equals(r.getId(), ID)) {
                return r;
            }
        }
        return null;
    }

    public String findPasswordByEmail(String email) {
        for (User r : userList) {
            if (Objects.equals(r.getEmail(), email)) {
                return r.getPassword();
            }
        }
        return null;
    }

    public User findByEmail(String email) {
        for (User r : userList) {
            if (Objects.equals(r.getEmail(), email)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public void modifyName(Integer ID, String newName) {
        User user = this.findById(ID);
        user.setName(newName);
    }

    @Override
    public void modifyEmail(Integer ID, String newEmail) {
        User user = this.findById(ID);
        user.setEmail(newEmail);
    }

    @Override
    public void modifyPhoneNumber(Integer ID, String newPhoneNumber) {
        User user = this.findById(ID);
        user.setPhoneNumber(newPhoneNumber);
    }

    @Override
    public void modifyPassword(Integer ID, String newPassword) {
        User user = this.findById(ID);
        user.setPassword(newPassword);
    }

}
