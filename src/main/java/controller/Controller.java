package controller;

import model.*;
import repository.IOrderRepository;
import repository.IProductRepository;
import repository.IUserRepository;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * The controller intermediates between the model and the view.
 */
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
     * @param user This is the user whose order we create with this method.
     * @return Order This returns the order we just created.
     */
    public Order createOrderWithUser(@NotNull User user) {
        List<ProductOrder> products = List.copyOf(userRepository.getCartOfUser(user));
        return new Order(LocalDateTime.now(), user.getId(), user.getAddress(), products);
    }

    /**
     * This method places an order for a user. The order is stored in the order repository and the user's shopping cart is emptied.
     *
     * @param user  This is the user whose order we place with this method.
     * @param order This is the order we place with this method.
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
     * @param email    This is the email used in the login attempt.
     * @param password This is the password used in the login attempt.
     * @return If the account exists (the email is found) and the password is correct, the user is returned.
     * @throws IncorrectPasswordException On incorrect password.
     * @throws UserNotFoundException      On nonexistent email.
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
     * @param user      This is the user in whose shopping cart we want to add the product.
     * @param productId This is the ID of the product we want to add to the user's shopping cart.
     * @param quantity  This is the amount of pieces of a product we want to add to the user's shopping cart.
     */
    public void addNewProductToCart(@NotNull User user, Integer productId, Integer quantity) {
        ProductOrder product = new ProductOrder(productId, quantity,
                productRepository.findById(productId).getBasePrice());
        userRepository.addProductToCart(user, product);
    }

    /**
     * This method adds a product to a user's shopping cart (unlike {@link #addNewProductToCart(User, Integer, Integer)},
     * it doesn't add a new product, but it modifies the quantity of a product already in the shopping cart).
     * If the quantity to add is negative, the quantity of the product in the shopping cart will be reduced accordingly.
     *
     * @param user      This is the user in whose shopping cart we want to add the product.
     * @param productId This is the ID of the product we want to add to the user's shopping cart.
     * @param qtyToAdd  This is the amount of pieces of a product we want to add to the user's shopping cart.
     * @throws ProductNotInRepositoryException On product ID not matching the ID of a product in the product repository.
     * @throws NegativeQuantityException       On trying to remove a product from the shopping cart that is not inside it.
     * @throws InsufficientStockException      On trying to add more pieces of a product in a shopping cart than there are in stock.
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
     * @param user This is the account we are creating with this method.
     */
    public void createAccount(User user) {
        userRepository.add(user);
    }

    /**
     * This method sorts the products from the product repository by name in ascending or descending order. The sorting
     * is done on a copy list of the product repository.
     *
     * @param ascending This boolean parameter determines if the products will be sorted by name in ascending or descending
     *                  order. If true, the sorting will be in ascending order, otherwise it will be in descending order.
     * @return A list with the sorted products.
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
     * @param ascending This boolean parameter determines if the products will be sorted by price in ascending or descending
     *                  order. If true, the sorting will be in ascending order, otherwise it will be in descending order.
     * @return A list with the sorted products.
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
     * @param text This is the string by which the filtering is done.
     * @return A list of the products containing the given string.
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
     * @param type This is the type by which the filtering is done.
     * @return A list of the products of the given type.
     */
    public List<Product> filterByType(ProductType type) {
        return new java.util.ArrayList<>(List.copyOf(productRepository.getProductList())).
                stream().filter((Product a) -> a.getType().equals(type)).toList();
    }

    /**
     * This method filters the products from the product repository based on if they are of a given use.
     *
     * @param use This is the use by which the filtering is done.
     * @return A list of the products of the given use.
     */
    public List<Product> filterByUse(ProductUse use) {
        return new java.util.ArrayList<>(List.copyOf(productRepository.getProductList())).
                stream().filter((Product a) -> a.getUse().equals(use)).toList();
    }

    /**
     * This method finds a product by ID and replaces it with another product with the same attributes, except the
     * attribute/s we want to modify.
     *
     * @param productId  This is the product we want to modify.
     * @param newProduct This is the product that replaces the product that we want to modify.
     * @throws ProductNotInRepositoryException On product ID not matching the ID of a product in the product repository.
     */
    public void modifyProduct(int productId, Product newProduct) throws ProductNotInRepositoryException {
        Product searched = productRepository.findById(productId);
        if (searched == null) {
            throw new ProductNotInRepositoryException("Product does not exist");
        }
        productRepository.modify(productId, newProduct);
    }

    /**
     * This method searches a product by ID.
     *
     * @param productId This is the ID of the product we are looking for.
     * @return The found product or null if the product is not found.
     * @throws ProductNotInRepositoryException On product ID not matching the ID of a product in the product repository.
     */
    public Product findProductById(int productId) throws ProductNotInRepositoryException {
        Product searched = productRepository.findById(productId);
        if (searched == null) {
            throw new ProductNotInRepositoryException("Product does not exist");
        }
        return searched;
    }

    /**
     * This method finds an order by ID and modifies its delivery address.
     *
     * @param orderId This is the ID of the order we are looking for.
     * @param address This is the address that we are going to replace the old one with.
     * @throws OrderNotInRepositoryException On order ID not matching the ID of an order in the product repository.
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
     * This method finds an order by ID and removes a certain product in whichever amount it is present there.
     *
     * @param orderId   This is the ID of the order we are looking for.
     * @param productId This is the ID of the product we want to remove.
     * @throws OrderNotInRepositoryException   On order ID not matching the ID of an order in the product repository.
     * @throws ProductNotInRepositoryException On product ID not matching the ID of a product in the product repository.
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

    public void addProduct(Product product) {
        productRepository.add(product);
    }

    public void deleteProduct(Integer id) {
        productRepository.delete(id);
    }

    public void emptyCart(User user) {
        userRepository.emptyCart(user);
    }

    public List<ProductOrder> getUserCart(User user) {
        return userRepository.getCartOfUser(user);
    }

    public List<Order> getOrderHistoryOfUser(User user) {
        return userRepository.getOrderHistoryOfUser(user);
    }
}
