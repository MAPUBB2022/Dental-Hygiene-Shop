package view;

import controller.Controller;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class UserView implements IView {
    private final Controller controller;

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public UserView(Controller controller) {
        this.controller = controller;
    }

    public void showMainMenuPrompt() {
        System.out.println("""

                MAIN MENU
                Options:
                0. Exit
                1. Login
                2. Sign up
                3. Continue as guest
                """);
    }

    public void useMainMenu() {
        int mainMenuOption = -1;
        boolean mainMenuExit = false;
        Scanner scanner = new Scanner(System.in);
        while (!mainMenuExit) {
            try {
                showMainMenuPrompt();
                mainMenuOption = scanner.nextInt();
                if (mainMenuOption < 0 || mainMenuOption > 3) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid input");
            }
            switch (mainMenuOption) {
                case 0 -> mainMenuExit = true;
                case 1 -> logIntoAccount(scanner);
                case 2 -> finishSignUp();
                case 3 -> continueAsGuest();
            }
        }
    }

    public void logIntoAccount(Scanner scanner) {
        User user;
        String email, password;
        System.out.println("Enter your email:");
        email = scanner.next();
        System.out.println("Enter your password:");
        password = scanner.next();
        user = controller.login(email, password);
        if (user == null) {
            System.out.println("Wrong email or password");
        } else {
            setUser(user);
            useUserMenu();
        }
    }

    public void showUserMenuPrompt() {
        System.out.println("""

                USER MENU
                Options:
                0. Exit
                1. Show all products
                2. Filter products
                3. Sort products
                4. Add to cart
                5. View cart
                6. Place order
                7. View orders
                """);
    }

    private void useUserMenu() {
        int userMenuOption = -1;
        boolean userMenuExit = false;
        Scanner scanner = new Scanner(System.in);
        while (!userMenuExit) {
            try {
                showUserMenuPrompt();
                userMenuOption = scanner.nextInt();
                if (userMenuOption < 0 || userMenuOption > 7) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid input");
            }
            switch (userMenuOption) {
                case 0 -> {
                    userMenuExit = true;
                    this.user = null;
                }
                case 1 -> showAllProducts();
                case 2 -> filterProducts();
                case 3 -> sortProducts();
                case 4 -> addProductsToCart();
                case 5 -> viewCart();
                case 6 -> placeOrder();
                case 7 -> viewOrders();
            }
        }
    }

    public Integer readProductId() {
        System.out.println("ID of product to add to cart: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public Integer readProductQuantity() {
        System.out.println("Quantity to add to cart (adding negative quantity will remove pieces): ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private void addProductsToCart() {
        //should be rewritten so that operations are handled by controller
        //check if product exists in productRepo
        Integer productId = readProductId();
        Integer qtyToAdd = readProductQuantity();
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
                user.getCart().addProduct(new ProductOrder(productId, qtyToAdd,
                        controller.getProductRepository().findById(productId).getBasePrice()));
            }
            else {
                System.out.println("nothing was added to cart");
            }
        }
    }

    private void viewCart() {
        List<ProductOrder> productOrders = this.user.getCart().getProducts();
        if (productOrders.isEmpty()) {
            System.out.println("Cart is empty");
        } else {
            for (ProductOrder product : productOrders) {
                System.out.println(controller.getProductRepository().findById(product.getProductId()).getName());
                System.out.println(product);
            }
            emptyCart();
        }
    }

    public String readOptionEmptyCart() {
        //returns lowercase letter(s)
        System.out.println("Empty cart? Y/N");
        Scanner s = new Scanner(System.in);
        return s.nextLine().toLowerCase();
    }

    public void emptyCart() {
        String option = readOptionEmptyCart();
        if ((!option.equals("y")) && (!option.equals("n"))) {
            System.out.println("\ninvalid option");
        } else {
            if (option.equals("y")) {
                user.getCart().getProducts().clear();
                System.out.println("\nCart emptied");
            } else {
                System.out.println("\nCart not emptied");
            }
        }

    }

    private void placeOrder() {
        Order newOrder = controller.createOrderWithUser((User) user);
        controller.placeOrderWithUser((User) user, newOrder);
        System.out.println("\nOrder placed. Cart emptied");
    }

    private void viewOrders() {
        System.out.println(((User) user).getOrderHistory());
    }

    public void finishSignUp() {
        //prints a successful sign up message and sends new user data to controller
        User user = readNewUser();
        controller.createAccount(user);
        System.out.println("Successfully created an account");
    }

    public User readNewUser() {
        Scanner scanner = new Scanner(System.in);

        String name, email, phoneNumber, password, country, region, city, street, number, postalCode;
        System.out.print("Name: ");
        name = scanner.next();
        System.out.print("Email: ");
        email = scanner.next();
        System.out.print("Phone number: ");
        phoneNumber = scanner.next();
        System.out.print("Password: ");
        password = scanner.next();
        System.out.print("Country: ");
        country = scanner.next();
        System.out.print("Region: ");
        region = scanner.next();
        System.out.print("City: ");
        city = scanner.next();
        System.out.print("Street: ");
        street = scanner.next();
        System.out.print("Number: ");
        number = scanner.next();
        System.out.print("Postal code: ");
        postalCode = scanner.next();

        Address address = new Address(country, region, city, street, number, postalCode);
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        Integer defaultAddressId = address.getId();
        List<Order> orderHistory = new ArrayList<>();

        return new User(name, email, phoneNumber, password, addresses, defaultAddressId, orderHistory);
    }

    public void continueAsGuest() {
        useGuestMenu();
    }

    public void showGuestMenuPrompt() {
        System.out.println("""

                GUEST MENU
                Options:
                0. Exit
                1. Show all products
                2. Filter products
                3. Sort products
                """);
    }

    public void useGuestMenu() {
        int guestMenuOption = -1;
        boolean guestMenuExit = false;
        Scanner scanner = new Scanner(System.in);
        while (!guestMenuExit) {
            try {
                showGuestMenuPrompt();
                guestMenuOption = scanner.nextInt();
                if (guestMenuOption < 0 || guestMenuOption > 3) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid input");
            }
            switch (guestMenuOption) {
                case 0 -> {
                    guestMenuExit = true;
                    this.user = null;
                }
                case 1 -> showAllProducts();
                case 2 -> filterProducts();
                case 3 -> sortProducts();
            }
        }
    }

    public void showAllProducts() {
        for (Product p : controller.getProductRepository().getProductList()) {
            if (p.getStock() == 0) {
                System.out.println("\nout of stock");
            }
            System.out.println(p);
        }
    }

    public void filterProducts() {
        System.out.println("Coming soon");
        //useFilteringMenu();
    }

    public void showFilteringMenuPrompt() {
        System.out.println("""

                FILTERING MENU
                Options:
                0. Exit
                1. Filter by name
                2. Filter by type
                3. Filter by use
                4. Filter by size
                """);
    }

    public void useFilteringMenu() {
        int filteringMenuOption = -1;
        boolean filteringMenuExit = false;
        Scanner scanner = new Scanner(System.in);
        while (!filteringMenuExit) {
            try {
                showFilteringMenuPrompt();
                filteringMenuOption = scanner.nextInt();
                if (filteringMenuOption < 0 || filteringMenuOption > 4) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid input");
            }
            switch (filteringMenuOption) {
                case 0 -> filteringMenuExit = true;
                case 1 -> filterProductsByName();
                case 2 -> filterProductsByType();
                case 3 -> filterProductsByUse();
                case 4 -> filterProductsBySize();
            }
        }
    }

    private void filterProductsByName() {

    }

    private void filterProductsByType() {

    }

    private void filterProductsByUse() {

    }

    private void filterProductsBySize() {
    }

    public void sortProducts() {
        System.out.println("Coming soon");
        //        useSortingMenu();
    }

    public void showSortingMenuPrompt() {
        System.out.println("""

                SORTING MENU
                Options:
                0. Exit
                1. Sort by price (ascending)
                2. Sort by price (descending)
                3. Sort by name (ascending)
                4. Sort by name (descending)
                """);
    }

    public void useSortingMenu() {
        int sortingMenuOption = -1;
        boolean sortingMenuExit = false;
        Scanner scanner = new Scanner(System.in);
        while (!sortingMenuExit) {
            try {
                showSortingMenuPrompt();
                sortingMenuOption = scanner.nextInt();
                if (sortingMenuOption < 0 || sortingMenuOption > 4) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid input");
            }
            switch (sortingMenuOption) {
                case 0 -> sortingMenuExit = true;
                case 1 -> sortByPriceAscending();
                case 2 -> sortByPriceDescending();
                case 3 -> sortByNameAscending();
                case 4 -> sortByNameDescending();
            }
        }
    }

    private void sortByPriceAscending() {
    }

    private void sortByPriceDescending() {
    }

    private void sortByNameAscending() {
    }

    private void sortByNameDescending() {
    }
}
