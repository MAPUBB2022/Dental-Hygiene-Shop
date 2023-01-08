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
        assertThrows(IncorrectPasswordException.class, () ->
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
                () -> controller.addToCart(user, 0, 2));

    }

    @Test
    void addToCartThrowsNegativeQuantity() {
        User user = controller.userRepository.findByEmail("ana@yahoo.com");
        assertThrows(NegativeQuantityException.class,
                () -> controller.addToCart(user,
                        productRepository.getProductList().get(2).getId(), -2));

    }

    @Test
    void addToCartThrowsInsufficientStock() {
        User user = controller.userRepository.findByEmail("ana@yahoo.com");
        assertThrows(InsufficientStockException.class,
                () -> controller.addToCart(user,
                        productRepository.getProductList().get(0).getId(), 1000));

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
        expected.add(productRepository.getProductList().get(0));
        expected.add(productRepository.getProductList().get(1));
        expected.add(productRepository.getProductList().get(5));
        expected.add(productRepository.getProductList().get(3));
        expected.add(productRepository.getProductList().get(2));
        expected.add(productRepository.getProductList().get(4));
        List<Product> actual = controller.sortByName(true);
        assertEquals(expected, actual);
    }

    @Test
    void sortByPrice() {
        //1,5,6,3,2,4
        List<Product> expected = new ArrayList<>();
        expected.add(productRepository.getProductList().get(0));
        expected.add(productRepository.getProductList().get(4));
        expected.add(productRepository.getProductList().get(5));
        expected.add(productRepository.getProductList().get(2));
        expected.add(productRepository.getProductList().get(1));
        expected.add(productRepository.getProductList().get(3));
        List<Product> actual = controller.sortByPrice(true);
        assertEquals(expected, actual);
    }

    @Test
    void filterByHasInName() {
        List<Product> actual = controller.filterByHasInName("Colgate");
        assert(actual.contains(productRepository.getProductList().get(0)));
        assert(actual.contains(productRepository.getProductList().get(1)));
        assert(actual.size()==2);
    }

    @Test
    void filterByType() {
        List<Product> actual = controller.filterByType(ProductType.DENTAL_FLOSS);
        assert (actual.contains(productRepository.getProductList().get(2)));
        assert (actual.size()==1);
    }

    @Test
    void filterByUse() {
        List<Product> actual = controller.filterByUse(ProductUse.HOME);
        assert (actual.containsAll(productRepository.getProductList()));
        assert (actual.size()==productRepository.getProductList().size());
    }

    @Test
    void modifyProductSuccessful(){

    }


    @Test
    void modifyProductThrowsProductNotInRepository() {
        assertThrows(ProductNotInRepositoryException.class, ()->
                controller.modifyProduct(0, null));
    }

    @Test
    void findProductByIdThrowsProductNotInRepository() {
        assertThrows(ProductNotInRepositoryException.class, ()->
        controller.findProductById(0));
    }

    @Test
    void findProductByIdSuccessful() throws ProductNotInRepositoryException {
        Product product = controller.findProductById(productRepository.getProductList().get(0).getId());
        assertEquals(product, productRepository.getProductList().get(0));
    }


    @Test
    void filterByHasInSize() {
        List<Product> actual = controller.filterByHasInSize("2p");
        assert(actual.contains(productRepository.getProductList().get(3)));
        assert (actual.size()==1);
    }

    @Test
    void emptyCart(){
        User user = userRepository.getUserList().get(0);
        Product product = productRepository.getProductList().get(0);
        ProductOrder productOrder = new ProductOrder(product.getId(), 17, product.getBasePrice());
        user.getCart().addProduct(productOrder);
        assertFalse(user.getCart().getProducts().isEmpty());
        controller.emptyCart(user);
        assertTrue(user.getCart().getProducts().isEmpty());
    }

}