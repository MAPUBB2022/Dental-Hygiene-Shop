package repository.databaseRepo;

import model.RegisteredUser;
import repository.UserRepository;

public class JdbcUserRepository implements UserRepository {
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
