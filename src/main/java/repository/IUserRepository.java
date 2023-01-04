package repository;

import model.*;

import java.util.List;

public interface IUserRepository extends ICrudRepository<Integer, User> {

    public List<User> getUserList();

    public void setUserList(List<User> userList);

    public void add(User user);

    public void delete(Integer ID);

    public User findById(Integer ID);

    public String findPasswordByEmail(String email);

    public User findByEmail(String email);


    public void modifyName(Integer ID, String newName);

    public void modifyEmail(Integer ID, String newEmail);

    public void modifyPhoneNumber(Integer ID, String newPhoneNumber);

    public void modifyPassword(Integer ID, String newPassword);

    ProductOrder findProductInCartById(User user, Integer productId);

    void removeProductFromCart(User user, ProductOrder product);

    void setCartProductQuantity(ProductOrder product, Integer quantity, User user);

    void addProductToCart(User user, ProductOrder product);

    void emptyCart(User user);

    List<ProductOrder> getCartOfUser(User user);

    List<Order> getOrderHistoryOfUser(User user);
}
