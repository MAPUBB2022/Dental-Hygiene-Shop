package controller;

import model.*;
import model.repository.memoryRepo.InMemoryOrderRepository;
import model.repository.memoryRepo.InMemoryProductRepository;
import model.repository.memoryRepo.InMemoryUserRepository;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class Controller implements IController<InMemoryOrderRepository, InMemoryProductRepository, InMemoryUserRepository> {
    InMemoryOrderRepository orderRepository;
    InMemoryProductRepository productRepository;
    InMemoryUserRepository userRepository;

    Order createOrder(@NotNull ShoppingCart shoppingCart, Integer userId){
        List<ProductOrder> products = List.copyOf(shoppingCart.getProducts());

        return new Order(LocalDateTime.now(), userId,
                userRepository.findById(userId).getAddresses().get(userRepository.findById(userId).getDefaultAddressId()),
                products);
        //this will be remade into separate functions
    }

    void placeOrder(Integer userId, Order order) {
        orderRepository.add(order);
        userRepository.findById(userId).getOrderHistory().add(order);
    }

    void createAccountFromOrder(Integer unregisteredUserId, String password) {

    }


}
