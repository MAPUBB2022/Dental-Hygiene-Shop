package model.repository.memoryRepo;

import model.Address;
import model.Order;
import model.RegisteredUser;
import model.repository.UserRepository;

import java.util.List;

public class InMemoryUserRepository implements UserRepository {
    @Override
    public void add(RegisteredUser registeredUser) {

    }

    @Override
    public void delete(Integer ID) {

    }

    @Override
    public void update(Integer ID, RegisteredUser registeredUser) {

    }

    @Override
    public RegisteredUser findById(Integer ID) {
        return null;
    }

    @Override
    public void modifyName(String newName) {

    }

    @Override
    public void modifyEmail(String newEmail) {

    }

    @Override
    public void modifyPhoneNumber(String newPhoneNumber) {

    }

    @Override
    public void modifyPassword(String newPassword) {

    }

    @Override
    public void modifyAddresses(Address newAddress) {

    }

    @Override
    public void modifyDefaultAddressId(int newDefaultAddressId) {

    }

    @Override
    public void modifyOrderHistory(List<Order> newOrderHistory) {

    }
}
