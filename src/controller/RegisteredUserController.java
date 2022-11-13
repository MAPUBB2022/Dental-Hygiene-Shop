package controller;

import model.*;
import repository.memoryRepo.InMemoryOrderRepository;
import repository.memoryRepo.InMemoryProductRepository;
import repository.memoryRepo.InMemoryUserRepository;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class RegisteredUserController implements IController<InMemoryOrderRepository, InMemoryProductRepository, InMemoryUserRepository> {
    InMemoryOrderRepository orderRepository;
    InMemoryProductRepository productRepository;
    InMemoryUserRepository userRepository;

    public RegisteredUserController(InMemoryOrderRepository orderRepository, InMemoryProductRepository productRepository, InMemoryUserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Order createOrder(@NotNull ShoppingCart shoppingCart, Integer userId) {
        List<ProductOrder> products = List.copyOf(shoppingCart.getProducts());

        return new Order(LocalDateTime.now(), userId,
                userRepository.findById(userId).getAddresses().get(userRepository.findById(userId).getDefaultAddressId()),
                products);
    }

    public void placeOrder(Integer userId, Order order) {
        orderRepository.add(order);
        userRepository.findById(userId).getOrderHistory().add(order);
    }

    public RegisteredUser login(String email, String password) {
        RegisteredUser registeredUser = userRepository.findByEmail(email);
        if (registeredUser != null) {
            if (password.equals(registeredUser.getPassword())){
                return registeredUser;
            }
        }
        return null;
    }

    public void createAccount(RegisteredUser user){
        userRepository.add(user);
    }

}
