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

    @Test
    void getOrderRepository() {
        assertEquals(orderRepository, controller.getOrderRepository());
    }

    @Test
    void setOrderRepository() {
        controller.setOrderRepository(orderRepository);
        assertEquals(orderRepository, controller.getOrderRepository());
    }

    @Test
    void getProductRepository() {
        assertEquals(productRepository, controller.getProductRepository());
    }

    @Test
    void setProductRepository() {
        controller.setProductRepository(productRepository);
        assertEquals(productRepository, controller.getProductRepository());
    }

    @Test
    void getUserRepository() {
        assertEquals(userRepository, controller.getUserRepository());
    }

    @Test
    void setUserRepository() {
        controller.setUserRepository(userRepository);
        assertEquals(userRepository, controller.getUserRepository());
    }

    @Test
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

    @Test
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

    @Test
    void loginSuccessful() {
        try {
            assertEquals(controller.login("ana@yahoo.com", "psswd"), userRepository.findByEmail("ana@yahoo.com"));
        } catch (IncorrectPasswordException | UserNotFoundException e) {
            assert false;
        }
    }

    @Test
    void loginThrowsUserNotFound() {
        assertThrows(UserNotFoundException.class, () ->
                controller.login("anasasa@yahoo.com", "asa"));
    }

    @Test
    void loginThrowsIncorrectPassword() {
        assertThrows(UserNotFoundException.class, () ->
                controller.login("ana@yahoo.com", "asa"));
    }

    @Test
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

    @Test
    void addToCartSuccessful() {
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

    @Test
    void addToCartThrowsProductNotInRepository() {
        User user = controller.userRepository.findByEmail("ana@yahoo.com");
        assertThrows(ProductNotInRepositoryException.class,
                () -> controller.addToCart(user, 30, 2));

    }

    @Test
    void addToCartThrowsNegativeQuantity() {
        User user = controller.userRepository.findByEmail("ana@yahoo.com");
        assertThrows(NegativeQuantityException.class,
                () -> controller.addToCart(user, 3, -2));

    }

    @Test
    void addToCartThrowsInsufficientStock() {
        User user = controller.userRepository.findByEmail("ana@yahoo.com");
        assertThrows(NegativeQuantityException.class,
                () -> controller.addToCart(user, 3, -2));

    }

    @Test
    void createAccount() {
        User user1 = new User("Andrei", "andreiandrei@gmail.con", "0720202020", "andrei",
                new Address("andrei", "andrei", "andrei", "andrei", "2", "2"),
                new ArrayList<>());
        controller.createAccount(user1);
        User user2 = userRepository.findById(user1.getId());
        assertEquals(user1, user2);
    }

    @Test
    void sortByName() {
        //sort by name: 1,2,6,4,3,5
        List<Product> expected = new ArrayList<>();
        expected.add(productRepository.getProductList().get(1));
    }

    @Test
    void sortByPrice() {
    }

    @Test
    void filterByHasInName() {
    }

    @Test
    void filterByType() {
    }

    @Test
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