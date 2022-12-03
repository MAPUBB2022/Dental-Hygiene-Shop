package controller;

import model.*;
import repository.IOrderRepository;
import repository.IProductRepository;
import repository.IUserRepository;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class Controller {
    IOrderRepository orderRepository;
    IProductRepository productRepository;
    IUserRepository userRepository;

    public IOrderRepository getOrderRepository() {
        return orderRepository;
    }

    public void setOrderRepository(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public IProductRepository getProductRepository() {
        return productRepository;
    }

    public void setProductRepository(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public IUserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Controller(IOrderRepository orderRepository, IProductRepository productRepository, IUserRepository userRepository) {
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

    public Order createOrderWithUser(@NotNull User user) {
        List<ProductOrder> products = List.copyOf(user.getCart().getProducts());
        return new Order(LocalDateTime.now(), user.getId(), user.findAddressById(user.getDefaultAddressId()), products);
    }

    public void placeOrderWithId(Integer userId, Order order) {
        orderRepository.add(order);
        userRepository.findById(userId).getOrderHistory().add(order);
    }

    public void placeOrderWithUser(@NotNull User user, Order order) {
        orderRepository.add(order);
        user.getOrderHistory().add(order);
        user.getCart().getProducts().clear();
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            if (password.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public void addToCart(@NotNull User user, Integer productId, Integer quantity) {
        ProductOrder product = new ProductOrder(productId, quantity, productRepository.findById(productId).getBasePrice());
        user.getCart().addProduct(product);
    }

    public void createAccount(User user) {
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


    public List<Product> sortByPrice(boolean ascending) {

        List<Product> sorted = new java.util.ArrayList<>(List.copyOf(productRepository.getProductList()));
        sorted.sort((Product a, Product b) -> (int) (a.getBasePrice()-b.getBasePrice()));
        if (!ascending) {
            Collections.reverse(sorted);
        }
        return sorted;
    }

    public List<Product> filterByHasInName(String text){
        return new java.util.ArrayList<>(List.copyOf(productRepository.getProductList())).
                stream().filter((Product a) -> a.getName().toLowerCase().contains(text.toLowerCase())).toList();
    }

//    public List<Product> filterByType(String text){
//        List<Product> filtered = new java.util.ArrayList<>(List.copyOf(productRepository.getProductList())).
//                stream().filter((Product a) -> a.getType().to).toList();
//        return filtered;
//    }

}
