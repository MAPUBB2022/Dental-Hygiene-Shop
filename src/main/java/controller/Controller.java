package controller;

import model.*;
import repository.IOrderRepository;
import repository.IProductRepository;
import repository.IUserRepository;
import org.jetbrains.annotations.NotNull;
import view.UserView;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class Controller {
    IOrderRepository orderRepository;
    IProductRepository productRepository;
    IUserRepository userRepository;

    /**
     * Order repository getter.
     */
    public IOrderRepository getOrderRepository() {
        return orderRepository;
    }

    /**
     * Order repository setter.
     */
    public void setOrderRepository(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Product repository getter.
     */
    public IProductRepository getProductRepository() {
        return productRepository;
    }

    /**
     * Product repository setter.
     */
    public void setProductRepository(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * User repository getter.
     */
    public IUserRepository getUserRepository() {
        return userRepository;
    }

    /**
     * User repository setter.
     */
    public void setUserRepository(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Controller constructor with parameters.
     */
    public Controller(IOrderRepository orderRepository, IProductRepository productRepository,
                      IUserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    /**
     * This method creates an order with the products from a user's shopping cart.
     *
     * @param user the user.
     * @return the order.
     */
    public Order createOrderWithUser(@NotNull User user) {
        List<ProductOrder> products = List.copyOf(userRepository.getCartOfUser(user));
        return new Order(LocalDateTime.now(), user.getId(), user.getAddress(), products);
    }

    /**
     * This method places an order for a user. The order is stored in the order repository and the user's shopping cart is emptied.
     * The stock of the products that the user orders are reduced accordingly.
     *
     * @param user  the user.
     * @param order the order.
     */
    public void placeOrderWithUser(@NotNull User user, Order order) {
        orderRepository.placeOrder(user, order);
        userRepository.emptyCart(user);
        for (ProductOrder p : order.getProducts()) {
            Product product = productRepository.findById(p.getProductId());
            productRepository.setStockOfProduct(product, product.getStock() - p.getQuantity());
        }
    }

    /**
     * This method logs a user into their account.
     *
     * @param email    the user's email.
     * @param password the user's password.
     * @return the user if their account exists (their email is found) and the password is correct.
     * @throws IncorrectPasswordException on incorrect password.
     * @throws UserNotFoundException      on nonexistent email.
     */
    public User login(String email, String password) throws IncorrectPasswordException, UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            if (password.equals(user.getPassword())) {
                return user;
            }
            throw new IncorrectPasswordException("Incorrect password");
        } else {
            throw new UserNotFoundException("Incorrect username");
        }
    }

    /**
     * This method adds a new product to a user's shopping cart.
     *
     * @param user      the user.
     * @param productId the ID of the product.
     * @param quantity  the quantity of the product.
     */
    public void addNewProductToCart(@NotNull User user, Integer productId, Integer quantity) {
        ProductOrder product = new ProductOrder(productId, quantity,
                productRepository.findById(productId).getBasePrice());
        userRepository.addProductToCart(user, product);
    }

    /**
     * This method adds a product to a user's shopping cart.
     * If the product already exists in the shopping cart, the quantity is increased or decreased,
     * depending on the value of the qtyToAdd variable. If the quantity of the product becomes zero or negative, the
     * product is removed from the shopping cart.
     * If the product does not exist in the shopping cart, it is added using the {@link Void addNewProductToCart(User, Integer, Integer)} method.
     *
     * @param user      the user.
     * @param productId the ID of the product.
     * @param qtyToAdd  the quantity of the product to add.
     * @throws ProductNotInRepositoryException on product ID not matching the ID of a product in the product repository.
     * @throws NegativeQuantityException       on trying to remove a product from the shopping cart that is not inside it.
     * @throws InsufficientStockException      on trying to add more pieces of a product in a shopping cart than there are in stock.
     */
    public void addToCart(@NotNull User user, Integer productId, Integer qtyToAdd)
            throws ProductNotInRepositoryException, NegativeQuantityException, InsufficientStockException {

        Product searched = productRepository.findById(productId);
        if (searched == null) {
            throw new ProductNotInRepositoryException("Product does not exist");
        }
        ProductOrder product = userRepository.findProductInCartById(user, productId);
        if (product != null) {
            int qtyInCart = product.getQuantity();
            if (qtyInCart + qtyToAdd <= 0) {
                userRepository.removeProductFromCart(user, product);
            } else if (qtyInCart + qtyToAdd > searched.getStock()) {
                throw new InsufficientStockException("Insufficient stock");
            } else {
                userRepository.setCartProductQuantity(product, qtyInCart + qtyToAdd, user);
            }
        } else {
            if (qtyToAdd >= 0) {
                if (qtyToAdd <= searched.getStock()) {
                    addNewProductToCart(user, productId, qtyToAdd);
                } else {
                    throw new InsufficientStockException("Insufficient stock");
                }
            } else {
                throw new NegativeQuantityException("New product with negative quantity will not be added");
            }
        }
    }

    /**
     * This method creates an account by adding a user and their data to the user repository.
     *
     * @param user the user.
     */
    public void createAccount(User user) {
        userRepository.add(user);
    }

    /**
     * This method sorts the products from the product repository by name in ascending or descending order. The sorting
     * is done on a copy list of the product repository.
     *
     * @param ascending this boolean parameter determines if the products will be sorted by name in ascending or descending
     *                  order. If true, the sorting will be in ascending order, otherwise it will be in descending order.
     * @return a list with the sorted products.
     */
    public List<Product> sortByName(boolean ascending) {

        List<Product> sorted = new java.util.ArrayList<>(List.copyOf(productRepository.getProductList()));
        sorted.sort((Product a, Product b) -> a.getName().compareToIgnoreCase(b.getName()));
        if (!ascending) {
            Collections.reverse(sorted);
        }
        return sorted;
    }

    /**
     * This method sorts the products from the product repository by price in ascending or descending order. The sorting
     * is done on a copy list of the product repository.
     *
     * @param ascending this boolean parameter determines if the products will be sorted by price in ascending or descending
     *                  order. If true, the sorting will be in ascending order, otherwise it will be in descending order.
     * @return a list with the sorted products.
     */
    public List<Product> sortByPrice(boolean ascending) {

        List<Product> sorted = new java.util.ArrayList<>(List.copyOf(productRepository.getProductList()));
        sorted.sort((Product a, Product b) -> (int) (a.getBasePrice() - b.getBasePrice()));
        if (!ascending) {
            Collections.reverse(sorted);
        }
        return sorted;
    }

    /**
     * This method filters the products from the product repository based on if they contain a given string.
     *
     * @param text the string.
     * @return a list of the products containing the given string.
     */
    public List<Product> filterByHasInName(String text) {
        return new java.util.ArrayList<>(List.copyOf(productRepository.getProductList())).
                stream().filter((Product a) -> a.getName().toLowerCase().contains(text.toLowerCase())).toList();
    }

    public List<Product> filterByHasInSize(String text) {
        return new java.util.ArrayList<>(List.copyOf(productRepository.getProductList())).
                stream().filter((Product a) -> a.getSize().toLowerCase().contains(text.toLowerCase())).toList();
    }

    /**
     * This method filters the products from the product repository based on if they are of a given type.
     *
     * @param type the type.
     * @return a list of the products of the given type.
     */
    public List<Product> filterByType(ProductType type) {
        return new java.util.ArrayList<>(List.copyOf(productRepository.getProductList())).
                stream().filter((Product a) -> a.getType().equals(type)).toList();
    }

    /**
     * This method filters the products from the product repository based on if they are of a given use.
     *
     * @param use the use.
     * @return a list of the products of the given use.
     */
    public List<Product> filterByUse(ProductUse use) {
        return new java.util.ArrayList<>(List.copyOf(productRepository.getProductList())).
                stream().filter((Product a) -> a.getUse().equals(use)).toList();
    }

    /**
     * This method finds a product by ID and replaces it with another product with the same attributes, except the
     * attribute/s to modify.
     *
     * @param productId  the ID of the product.
     * @param newProduct the replacement product.
     * @throws ProductNotInRepositoryException on product ID not matching the ID of a product in the product repository.
     */
    public void modifyProduct(int productId, Product newProduct) throws ProductNotInRepositoryException {
        Product searched = productRepository.findById(productId);
        if (searched == null) {
            throw new ProductNotInRepositoryException("Product does not exist");
        }
        productRepository.modify(productId, newProduct);
    }

    /**
     * This method searches for a product by ID.
     *
     * @param productId the ID of the product.
     * @return the product or null if the product is not found.
     * @throws ProductNotInRepositoryException on product ID not matching the ID of a product in the product repository.
     */
    public Product findProductById(int productId) throws ProductNotInRepositoryException {
        Product searched = productRepository.findById(productId);
        if (searched == null) {
            throw new ProductNotInRepositoryException("Product does not exist");
        }
        return searched;
    }

    /**
     * This method searches for an order by ID and modifies its delivery address if found.
     *
     * @param orderId the ID of the order.
     * @param address the new delivery address.
     * @throws OrderNotInRepositoryException on order ID not matching the ID of an order in the product repository.
     */
    public void modifyOrderDeliveryAddress(Integer orderId, Address address)
            throws OrderNotInRepositoryException {
        Order searched = orderRepository.findById(orderId);
        if (searched == null) {
            throw new OrderNotInRepositoryException("Order does not exist");
        }
        orderRepository.modifyDeliveryAddress(orderId, address);
    }

    /**
     * This method searches for an order by ID and removes a product from it in whichever amount it is present there.
     *
     * @param orderId   the ID of the sought order.
     * @param productId the ID of the sought product.
     * @throws OrderNotInRepositoryException   on order ID not matching the ID of an order in the product repository.
     * @throws ProductNotInRepositoryException on product ID not matching the ID of a product in the product repository.
     */
    public void modifyOrderProductList(Integer orderId, Integer productId)
            throws OrderNotInRepositoryException, ProductNotInRepositoryException {
        Order searchedOrder = orderRepository.findById(orderId);
        if (searchedOrder == null) {
            throw new OrderNotInRepositoryException("Order does not exist");
        }
        Product searchedProduct = productRepository.findById(productId);
        if (searchedProduct == null) {
            throw new ProductNotInRepositoryException("Product does not exist");
        }
        orderRepository.modifyProducts(orderId, productId);
    }

    /**
     * This method adds a product to the product repository.
     *
     * @param product the product.
     */
    public void addProduct(Product product) {
        productRepository.add(product);
    }

    /**
     * This method deletes a product from the product repository.
     *
     * @param id the ID of the product.
     */
    public void deleteProduct(Integer id) {
        productRepository.delete(id);
    }

    /**
     * This method empties the cart of a user.
     *
     * @param user the user.
     */
    public void emptyCart(User user) {
        userRepository.emptyCart(user);
    }

    /**
     * User cart getter.
     */
    public List<ProductOrder> getUserCart(User user) {
        return userRepository.getCartOfUser(user);
    }

    /**
     * Order history getter.
     */
    public List<Order> getOrderHistoryOfUser(User user) {
        return userRepository.getOrderHistoryOfUser(user);
    }

    /**
     * This method deletes a user from the user repository.
     *
     * @param id the id of the user.
     */
    public void deleteAccountById(Integer id) {
        userRepository.delete(id);
    }

    /**
     * This method modifies the delivery address of a user.
     *
     * @param user       the user.
     * @param newAddress the new address.
     */
    public void modifyUserDeliveryAddress(User user, Address newAddress) {
        userRepository.modifyDeliveryAddress(user, newAddress);
    }

    /**
     * This method modifies the name of a user.
     *
     * @param user the user.
     * @param name the new name.
     */
    public void modifyUserName(User user, String name) {
        userRepository.modifyName(user.getId(), name);
    }

    /**
     * This method modifies the email of a user.
     *
     * @param user  the user.
     * @param email the new email.
     */
    public void modifyUserEmail(User user, String email) {
        userRepository.modifyEmail(user.getId(), email);
    }

    /**
     * This method modifies the password of a user.
     *
     * @param user     the user.
     * @param password the new password.
     */
    public void modifyUserPassword(User user, String password) {
        userRepository.modifyPassword(user.getId(), password);
    }

    /**
     * This method modifies the phone number of a user.
     *
     * @param user        the user.
     * @param phoneNumber the new phone number.
     */
    public void modifyUserPhoneNumber(User user, String phoneNumber) {
        userRepository.modifyPhoneNumber(user.getId(), phoneNumber);
    }
}
