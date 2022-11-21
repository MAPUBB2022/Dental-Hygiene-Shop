package controller;

import model.*;
import repository.memoryRepo.InMemoryOrderRepository;
import repository.memoryRepo.InMemoryProductRepository;
import repository.memoryRepo.InMemoryUserRepository;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class RegisteredUserController implements IController<InMemoryOrderRepository, InMemoryProductRepository, InMemoryUserRepository> {
    InMemoryOrderRepository orderRepository;
    InMemoryProductRepository productRepository;
    InMemoryUserRepository userRepository;

    public InMemoryOrderRepository getOrderRepository() {
        return orderRepository;
    }

    public void setOrderRepository(InMemoryOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public InMemoryProductRepository getProductRepository() {
        return productRepository;
    }

    public void setProductRepository(InMemoryProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public InMemoryUserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(InMemoryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public RegisteredUserController(InMemoryOrderRepository orderRepository, InMemoryProductRepository productRepository, InMemoryUserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    /*public Order createOrderWithId(@NotNull ShoppingCart shoppingCart, Integer userId) {
        List<ProductOrder> products = List.copyOf(shoppingCart.getProducts());
     return new Order(LocalDateTime.now(), userId,
                userRepository.findById(userId).getAddresses().!!!!!!!!!(userRepository.findById(userId).getDefaultAddressId()),
                products);
    }*/

    public Order createOrderWithUser(@NotNull RegisteredUser user) {
        List<ProductOrder> products = List.copyOf(user.getCart().getProducts());
        return new Order(LocalDateTime.now(), user.getId(), user.findAddressById(user.getDefaultAddressId()), products);
    }

    public void placeOrderWithId(Integer userId, Order order) {
        orderRepository.add(order);
        userRepository.findById(userId).getOrderHistory().add(order);
    }

    public void placeOrderWithUser(@NotNull RegisteredUser user, Order order) {
        orderRepository.add(order);
        user.getOrderHistory().add(order);
        user.getCart().getProducts().clear();
    }

    public RegisteredUser login(String email, String password) {
        RegisteredUser registeredUser = userRepository.findByEmail(email);
        if (registeredUser != null) {
            if (password.equals(registeredUser.getPassword())) {
                return registeredUser;
            }
        }
        return null;
    }

    public void addToCart(@NotNull RegisteredUser user, Integer productId, Integer quantity) {
        ProductOrder product = new ProductOrder(productId, quantity, productRepository.findById(productId).getBasePrice());
        user.getCart().addProduct(product);
    }

    public void createAccount(RegisteredUser user) {
        userRepository.add(user);
    }

    //sort by: name, price
    //filter by: hasInName, type, use, hasInSize

    public List<Product> sortByName(boolean ascending) {

        List<Product> sorted = new java.util.ArrayList<>(List.copyOf(productRepository.getProductList()));
        sorted.sort((Product a, Product b) -> a.getName().compareToIgnoreCase(b.getName()));
        if (!ascending) {
            Collections.reverse(sorted);
        }
        return sorted;
    }


}
