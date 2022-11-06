package controller;

import model.Order;
import model.User;
import model.repository.memoryRepo.InMemoryOrderRepository;
import model.repository.memoryRepo.InMemoryProductRepository;
import model.repository.memoryRepo.InMemoryUserRepository;

public class Controller implements  IController<InMemoryOrderRepository, InMemoryProductRepository, InMemoryUserRepository> {
    InMemoryOrderRepository orderRepository;
    InMemoryProductRepository productRepository;
    InMemoryUserRepository userRepository;

    void placeOrder(Integer userId, Order order){
        orderRepository.add(order);
        userRepository.findById(userId).getOrderHistory().add(order);
    }



}
