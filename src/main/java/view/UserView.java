package view;

import controller.Controller;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class UserView extends View {

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public UserView(Controller controller) {
        super(controller);
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

    public void useMainMenu()  {
        int mainMenuOption = -1;
        boolean mainMenuExit = false;
        Scanner scanner = new Scanner(System.in);
        while (!mainMenuExit) {
            try {
                showMainMenuPrompt();
                mainMenuOption = Integer.parseInt(scanner.next());
                if (mainMenuOption < 0 || mainMenuOption > 3) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid input");
            }
            switch (mainMenuOption) {
                case 0 -> mainMenuExit = true;
                case 1 -> {
                    try {
                        logIntoAccount(scanner);
                    } catch (UserNotFoundException e) {
                        System.out.println("User not found");
                    } catch (IncorrectPasswordException e) {
                        System.out.println("Incorrect password");
                    }
                }
                case 2 -> finishSignUp();
                case 3 -> continueAsGuest();
            }
        }
    }

    public void logIntoAccount(Scanner scanner) throws UserNotFoundException, IncorrectPasswordException {
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
        //modify address!!!!!!!!!!!!!!!!!!!!!!!!
    }

    private void useUserMenu() {
        int userMenuOption = -1;
        boolean userMenuExit = false;
        Scanner scanner = new Scanner(System.in);
        while (!userMenuExit) {
            try {
                showUserMenuPrompt();
                userMenuOption = Integer.parseInt(scanner.next());
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

    private void addProductsToCart() {
        //should be rewritten so that operations are handled by controller
        //check if product exists in productRepo
        System.out.println("Product ID:");
        Integer productId = readId();
        if (productId != null) {
            Integer qtyToAdd = readProductQuantity();
            if (qtyToAdd != null) {
                try {
                    try {
                        controller.addToCart(user, productId, qtyToAdd);
                    } catch (ProductNotInRepositoryException e) {
                        System.out.println("Product does not exist");
                    } catch (NegativeQuantityException e) {
                        System.out.println("Nothing added to cart");
                    }
                } catch (InsufficientStockException e) {
                    System.out.println("Insufficient stock");
                }

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
        System.out.println(user.getOrderHistory());
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
        List<Order> orderHistory = new ArrayList<>();

        return new User(name, email, phoneNumber, password, address, orderHistory);
    }

    @Override
    public void useGuestMenu() {
        int guestMenuOption = -1;
        boolean guestMenuExit = false;
        Scanner scanner = new Scanner(System.in);
        while (!guestMenuExit) {
            try {
                showGuestMenuPrompt();
                guestMenuOption = Integer.parseInt(scanner.next());
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

}
