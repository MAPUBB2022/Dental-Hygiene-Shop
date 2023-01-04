package controller;

import model.*;
import org.junit.jupiter.api.Test;
import repository.memoryRepo.InMemoryOrderRepository;
import repository.memoryRepo.InMemoryProductRepository;
import repository.memoryRepo.InMemoryUserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private Controller controller;
    private InMemoryOrderRepository orderRepository;
    private InMemoryProductRepository productRepository;
    private InMemoryUserRepository userRepository;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        this.orderRepository = new InMemoryOrderRepository();
        this.productRepository = new InMemoryProductRepository();
        this.userRepository = new InMemoryUserRepository();
        this.controller = new Controller(orderRepository, productRepository, userRepository);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void getOrderRepository() {
        assertEquals(orderRepository, controller.getOrderRepository());
    }

    @org.junit.jupiter.api.Test
    void setOrderRepository() {
        controller.setOrderRepository(orderRepository);
        assertEquals(orderRepository, controller.getOrderRepository());
    }

    @org.junit.jupiter.api.Test
    void getProductRepository() {
        assertEquals(productRepository, controller.getProductRepository());
    }

    @org.junit.jupiter.api.Test
    void setProductRepository() {
        controller.setProductRepository(productRepository);
        assertEquals(productRepository, controller.getProductRepository());
    }

    @org.junit.jupiter.api.Test
    void getUserRepository() {
        assertEquals(userRepository, controller.getUserRepository());
    }

    @org.junit.jupiter.api.Test
    void setUserRepository() {
        controller.setUserRepository(userRepository);
        assertEquals(userRepository, controller.getUserRepository());
    }

    @org.junit.jupiter.api.Test
    void createOrderWithUser() {
        User user1 = new User("Andrei", "andreiandrei@gmail.con", "0720202020", "andrei",
                new Address("andrei", "andrei", "andrei", "andrei", "2", "2"),
                new ArrayList<>());
        Product product = controller.productRepository.getProductList().get(1);
        ProductOrder productOrder = new ProductOrder(product.getId(), 2, 10);
        user1.getCart().addProduct(productOrder);
        Order order1 = controller.createOrderWithUser(user1);
        controller.placeOrderWithUser(user1, order1);
        Order order2 = orderRepository.findById(order1.getId());
        assertEquals(order1, order2);
    }

    @org.junit.jupiter.api.Test
    void placeOrderWithUser() {
        User user1 = new User("Andrei", "andreiandrei@gmail.con", "0720202020", "andrei",
                new Address("andrei", "andrei", "andrei", "andrei", "2", "2"),
                new ArrayList<>());
        Product product = controller.productRepository.getProductList().get(1);
        ProductOrder productOrder = new ProductOrder(product.getId(), 2, 10);
        user1.getCart().addProduct(productOrder);
        Order order1 = controller.createOrderWithUser(user1);
        controller.placeOrderWithUser(user1, order1);
        assertEquals(user1.getOrderHistory().get(0), order1);
        assertTrue(user1.getCart().getProducts().isEmpty());
    }

    @org.junit.jupiter.api.Test
    void login() {
        try {
            assertEquals(controller.login("ana@yahoo.com", "psswd"), userRepository.findByEmail("ana@yahoo.com"));
        } catch (IncorrectPasswordException | UserNotFoundException e) {
            assert false;
        }
        try {
            controller.login("anasasa@yahoo.com", "asa");
        } catch (UserNotFoundException e) {
            assert true;
        } catch (IncorrectPasswordException e) {
            assert false;
        }

        try {
            controller.login("ana@yahoo.com", "asa");
        } catch (IncorrectPasswordException e) {
            assert true;
        } catch (UserNotFoundException e) {
            assert false;
        }
    }

    @org.junit.jupiter.api.Test
    void addNewProductToCart() {
        User user = controller.userRepository.findByEmail("ana@yahoo.com");
        Product product = controller.productRepository.getProductList().get(1);
        ProductOrder productOrder = new ProductOrder(product.getId(), 2, 10);
        ShoppingCart cart1 = user.getCart();
        controller.addNewProductToCart(user, product.getId(), 2);
        ShoppingCart cart2 = user.getCart();
        cart1.addProduct(productOrder);
        assertEquals(cart1, cart2);
    }

    @org.junit.jupiter.api.Test
    void addToCart() {
        User user = controller.userRepository.findByEmail("ana@yahoo.com");
        Product product = controller.productRepository.getProductList().get(1);
        ProductOrder productOrder = new ProductOrder(product.getId(), 2, 10);
        ShoppingCart cart1 = user.getCart();
        try {
            controller.addToCart(user, product.getId(), 2);
        } catch (ProductNotInRepositoryException | NegativeQuantityException | InsufficientStockException e) {
            assert false;
        }
        ShoppingCart cart2 = user.getCart();
        cart1.addProduct(productOrder);
        assertEquals(cart1, cart2);
    }

    @org.junit.jupiter.api.Test
    void createAccount() {
        User user1 = new User("Andrei", "andreiandrei@gmail.con", "0720202020", "andrei",
                new Address("andrei", "andrei", "andrei", "andrei", "2", "2"),
                new ArrayList<>());
        controller.createAccount(user1);
        User user2 = userRepository.findById(user1.getId());
        assertEquals(user1, user2);
    }

    @org.junit.jupiter.api.Test
    void sortByName() {
    }

    @org.junit.jupiter.api.Test
    void sortByPrice() {
    }

    @org.junit.jupiter.api.Test
    void filterByHasInName() {
    }

    @org.junit.jupiter.api.Test
    void filterByType() {
    }

    @org.junit.jupiter.api.Test
    void filterByUse() {
    }


    @Test
    void modifyProduct() {
    }

    @Test
    void findProductById() {
    }

    @Test
    void modifyOrderDeliveryAddress() {
    }

    @Test
    void modifyOrderProductList() {
    }

    @Test
    void addProduct() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void emptyCart() {
    }

    @Test
    void getUserCart() {
    }

    @Test
    void getOrderHistoryOfUser() {
    }

    @Test
    void filterByHasInSize() {
    }

}