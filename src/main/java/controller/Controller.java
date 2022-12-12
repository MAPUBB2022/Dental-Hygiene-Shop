package controller;

import model.*;
import repository.IOrderRepository;
import repository.IProductRepository;
import repository.IUserRepository;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

//exceptii: produse comandate>nr produse in stoc, alte cheztii
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


    public Order createOrderWithUser(@NotNull User user) {
        List<ProductOrder> products = List.copyOf(user.getCart().getProducts());
        return new Order(LocalDateTime.now(), user.getId(), user.getAddress(), products);
    }

    public void placeOrderWithUser(@NotNull User user, Order order) {
        orderRepository.add(order);
        user.getOrderHistory().add(order);
        user.getCart().getProducts().clear();
    }

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

    public void addNewProductToCart(@NotNull User user, Integer productId, Integer quantity) {
        ProductOrder product = new ProductOrder(productId, quantity, productRepository.findById(productId).getBasePrice());
        user.getCart().addProduct(product);
    }

    public ProductOrder addToCart(@NotNull User user, Integer productId, Integer qtyToAdd) throws ProductNotInRepositoryException, NegativeQuantityException {

        Product searched = productRepository.findById(productId);
        if (searched == null) {
            throw new ProductNotInRepositoryException("Product does not exist");
        }
        ProductOrder product = user.getCart().findById(productId);
        if (product != null) {
            int qtyInCart = product.getQuantity();
            if (qtyInCart + qtyToAdd <= 0) {
                user.getCart().getProducts().remove(product);
            } else {
                product.setQuantity(qtyInCart + qtyToAdd);
            }
        } else {
            if (qtyToAdd >= 0) {
                addNewProductToCart(user, productId, qtyToAdd);
            } else {
                throw new NegativeQuantityException("Nothing added to cart");
            }
        }
        return product;
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
        sorted.sort((Product a, Product b) -> (int) (a.getBasePrice() - b.getBasePrice()));
        if (!ascending) {
            Collections.reverse(sorted);
        }
        return sorted;
    }

    public List<Product> filterByHasInName(String text) {
        return new java.util.ArrayList<>(List.copyOf(productRepository.getProductList())).
                stream().filter((Product a) -> a.getName().toLowerCase().contains(text.toLowerCase())).toList();
    }

    public List<Product> filterByType(ProductType type) {
        return new java.util.ArrayList<>(List.copyOf(productRepository.getProductList())).
                stream().filter((Product a) -> a.getType().equals(type)).toList();
    }

    public List<Product> filterByUse(ProductUse use) {
        return new java.util.ArrayList<>(List.copyOf(productRepository.getProductList())).
                stream().filter((Product a) -> a.getUse().equals(use)).toList();
    }


    public void modifyProduct(int productId, Product newProduct) throws ProductNotInRepositoryException {
        Product searched = productRepository.findById(productId);
        if (searched == null) {
            throw new ProductNotInRepositoryException("Product does not exist");
        }
        productRepository.modify(productId, newProduct);
    }

    public Product findProductById (int productId) throws ProductNotInRepositoryException {
        Product searched = productRepository.findById(productId);
        if (searched == null) {
            throw new ProductNotInRepositoryException("Product does not exist");
        }
        return searched;
    }

    public void modifyOrderDeliveryAddress(Integer orderId, Address address) throws OrderNotInRepositoryException {
        Order searched = orderRepository.findById(orderId);
        if (searched == null) {
            throw new OrderNotInRepositoryException("Order does not exist");
        }
        orderRepository.modifyDeliveryAddress(orderId, address);
    }

    public void modifyOrderProductList(Integer orderId, Integer productId) throws OrderNotInRepositoryException, ProductNotInRepositoryException {
        Order searchedOrder = orderRepository.findById(orderId);
        if (searchedOrder == null) {
            throw new OrderNotInRepositoryException("Order does not exist");
        }
        Product searchedProduct = productRepository.findById(productId);
        if (searchedProduct == null) {
            throw new ProductNotInRepositoryException("Product does not exist");
        }
        searchedOrder.deleteProduct(productId);
        searchedOrder.setPrice(searchedOrder.calculatePrice());
    }
}
