package model.repository.memoryRepo;

import model.RegisteredUser;
import model.UnregisteredUser;
import model.repository.ICrudRepository;
import model.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InMemoryUnregisteredUserRepository implements ICrudRepository<Integer, UnregisteredUser> {
    private List<UnregisteredUser> userList;

    public InMemoryUnregisteredUserRepository() {
        this.userList = new ArrayList<>();
    }

    public List<UnregisteredUser> getUserList() {
        return userList;
    }

    public void setUserList(List<UnregisteredUser> userList) {
        this.userList = userList;
    }

    @Override
    public void add(UnregisteredUser unregisteredUser) {
        userList.add(unregisteredUser);
    }

    @Override
    public void delete(Integer integer) {
        userList.remove(findById(integer));
    }

    @Override
    public void update(Integer integer, UnregisteredUser unregisteredUser) {
            //nu stiu
    }

    @Override
    public UnregisteredUser findById(Integer integer) {
        for (UnregisteredUser r : userList) {
            if (Objects.equals(r.getId(), integer)) {
                return r;
            }
        }
        return null;
    }
}
