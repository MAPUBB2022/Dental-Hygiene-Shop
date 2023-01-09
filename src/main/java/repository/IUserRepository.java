package repository;

import model.*;

import java.util.List;

public interface IUserRepository extends ICrudRepository<Integer, User> {

    /**
     * User list getter.
     */
    public List<User> getUserList();

    /**
     * User list setter.
     */
    public void setUserList(List<User> userList);

    /**
     * This method adds a user to the user list.
     *
     * @param user the user.
     */
    public void add(User user);

    /**
     * This method deletes a user from the user list.
     *
     * @param id the ID of the user.
     */
    public void delete(Integer id);

    /**
     * This method finds a user by their ID.
     *
     * @param id the ID of the user.
     * @return the user.
     */
    public User findById(Integer id);

    /**
     * This method finds the password of a user by their email address.
     *
     * @param email the email address.
     * @return the password.
     */
    public String findPasswordByEmail(String email);

    /**
     * This method finds a user by their email address.
     *
     * @param email the email address.
     * @return the user.
     */
    public User findByEmail(String email);

    /**
     * This method modifies the name of a user.
     *
     * @param id      the id of the user.
     * @param newName the new name.
     */
    public void modifyName(Integer id, String newName);

    /**
     * This method modifies the email address of a user.
     *
     * @param id       the id of the user.
     * @param newEmail the new email address.
     */
    public void modifyEmail(Integer id, String newEmail);

    /**
     * This method modifies the phone number of a user.
     *
     * @param id             the id of the user.
     * @param newPhoneNumber the new phone number.
     */
    public void modifyPhoneNumber(Integer id, String newPhoneNumber);

    /**
     * This method modifies the password of a user.
     *
     * @param id          the id of the user.
     * @param newPassword the new password.
     */
    public void modifyPassword(Integer id, String newPassword);

    /**
     * This method finds a product in a user's cart by the product's ID.
     *
     * @param user      the user.
     * @param productId the ID of the product.
     * @return the product.
     */
    ProductOrder findProductInCartById(User user, Integer productId);

    /**
     * This method removes a product from a user's shopping cart.
     *
     * @param user    the user.
     * @param product the product.
     */
    void removeProductFromCart(User user, ProductOrder product);

    /**
     * This method sets the quantity of a product from a user's shopping cart.
     *
     * @param product  the product.
     * @param quantity the quantity.
     * @param user     the user.
     */
    void setCartProductQuantity(ProductOrder product, Integer quantity, User user);

    /**
     * This method adds a product to a user's shopping cart.
     *
     * @param user    the user.
     * @param product the product.
     */
    void addProductToCart(User user, ProductOrder product);

    /**
     * This method empties the shopping cart of a user.
     *
     * @param user the user.
     */
    void emptyCart(User user);

    /**
     * @param user a user.
     * @return the shopping cart of the user.
     */
    List<ProductOrder> getCartOfUser(User user);

    /**
     * @param user a user
     * @return the order history of the user.
     */
    List<Order> getOrderHistoryOfUser(User user);

    /**
     * This method modifies the delivery address of a user.
     *
     * @param user               the user.
     * @param newDeliveryAddress the new delivery address.
     */
    void modifyDeliveryAddress(User user, Address newDeliveryAddress);
}
