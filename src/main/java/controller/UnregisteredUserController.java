package controller;

import model.*;
import repository.memoryRepo.InMemoryOrderRepository;
import repository.memoryRepo.InMemoryProductRepository;
import repository.memoryRepo.InMemoryUnregisteredUserRepository;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class UnregisteredUserController implements IController<InMemoryOrderRepository, InMemoryProductRepository, InMemoryUnregisteredUserRepository>{

    InMemoryOrderRepository orderRepository;
    InMemoryProductRepository productRepository;
    InMemoryUnregisteredUserRepository userRepository;

    public UnregisteredUser createUser(String name, String email, String phoneNumber){
        return new UnregisteredUser(name, email, phoneNumber);
    }

    public Order createOrder(@NotNull ShoppingCart shoppingCart, Address address, Integer userId){
        List<ProductOrder> products = List.copyOf(shoppingCart.getProducts());
        return new Order(LocalDateTime.now(), userId, address, products);
    }

    //public RegisteredUser createAccount(Integer )
}
