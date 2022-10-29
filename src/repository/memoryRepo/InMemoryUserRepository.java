package repository.memoryRepo;

import model.RegisteredUser;
import repository.UserRepository;

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
}
