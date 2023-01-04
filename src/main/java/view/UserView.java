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

    public void useMainMenu() {
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
                case 3 -> useGuestMenu();
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
                7. View order history
                8. Modify account information
                """);
    }

    private void useUserMenu() {
        int userMenuOption = -1;
        boolean userMenuExit = false;
        Scanner scanner = new Scanner(System.in);
        while (!userMenuExit) {
            try {
                showUserMenuPrompt();
                userMenuOption = Integer.parseInt(scanner.next());
                if (userMenuOption < 0 || userMenuOption > 8) {
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
                case 2 -> useFilteringMenu();
                case 3 -> sortProducts();
                case 4 -> addProductsToCart();
                case 5 -> viewCart();
                case 6 -> placeOrder();
                case 7 -> viewOrders();
                case 8 -> modifyAccountInfo();
            }
        }
    }

    private void showModifyAccountMenuPrompt() {
        showAccountInformation();
        System.out.println("""

                ACCOUNT INFORMATION MENU
                Options:
                0. Exit
                1. Modify name
                2. Modify delivery address
                3. Modify phone number
                4. Modify email
                5. Modify password
                6. Delete account
                """);

    }

    private void showAccountInformation(){
        System.out.println("Current account details: ");
        System.out.println(this.user);
    }

    private void modifyAccountInfo() {
        int option = -1;
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        while (!exit) {
            try {
                showModifyAccountMenuPrompt();
                option = Integer.parseInt(scanner.next());
                if (option < 0 || option > 6) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid input");
            }
            switch (option) {
                case 0 -> exit = true;
                case 1 -> modifyName();
                case 2 -> modifyDeliveryAddress();
                case 3 -> modifyPhoneNumber();
                case 4 -> modifyEmail();
                case 5 -> modifyPassword();
                case 6 -> deleteAccount();
            }
        }

    }

    private void deleteAccount() {
        System.out.println("Are you sure? You will lose all data and have no way of undoing the deletion.");
        System.out.println("yes/no");
        String option = readString();
        if (option.equals("yes")){
            System.out.println("Your account will be deleted. The application will close.");
            Integer id = this.user.getId();
            this.user = null;
            controller.deleteAccountById(id);
            System.exit(0);
        }
        else {
            System.out.println("Your account will not be deleted. Returning to previous menu");
        }
    }

    private void modifyEmail() {
        System.out.println("new email: ");
        String email = readString();
        controller.modifyUserEmail(this.user, email);
        this.user = controller.getUserRepository().findById(user.getId());
    }

    private void modifyPassword() {
        System.out.println("new password: ");
        String password = readString();
        controller.modifyUserPassword(this.user, password);
        this.user = controller.getUserRepository().findById(user.getId());
    }

    private void modifyPhoneNumber() {
        System.out.println("new phone number: ");
        String phoneNumber = readString();
        controller.modifyUserPhoneNumber(this.user, phoneNumber);
        this.user = controller.getUserRepository().findById(user.getId());
    }

    private void modifyName() {
        System.out.println("new name: ");
        String name = readString();
        controller.modifyUserName(this.user, name);
        this.user = controller.getUserRepository().findById(user.getId());
    }

    private void modifyDeliveryAddress() {
        Address newAddress = readAddress();
        controller.modifyUserDeliveryAddress(this.user, newAddress);
        this.user = controller.getUserRepository().findById(user.getId());
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
        List<ProductOrder> productOrders = controller.getUserCart(user);
        double price = 0.0;
        if (productOrders.isEmpty()) {
            System.out.println("Cart is empty");
        } else {
            for (ProductOrder product : productOrders) {
                System.out.println(controller.getProductRepository().findById(product.getProductId()).getName());
                System.out.println(product);
                price += product.getPrice() * product.getQuantity();
            }
            System.out.println("Total: " + price);
            emptyCart();
        }
    }

    public String readOptionEmptyCart() {
        //returns lowercase letter(s)
        System.out.println("Empty cart? y/n");
        Scanner s = new Scanner(System.in);
        return s.nextLine().toLowerCase();
    }

    public void emptyCart() {
        String option = readOptionEmptyCart();
        if ((!option.equals("y")) && (!option.equals("n"))) {
            System.out.println("\ninvalid option");
        } else {
            if (option.equals("y")) {
                controller.emptyCart(user);
                System.out.println("\nCart emptied");
            } else {
                System.out.println("\nCart not emptied");
            }
        }

    }

    private void placeOrder() {
        Order newOrder = controller.createOrderWithUser(user);
        controller.placeOrderWithUser(user, newOrder);
        System.out.println("\nOrder placed. Cart emptied");
    }

    private void viewOrders() {
        System.out.println(controller.getOrderHistoryOfUser(user));
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
                case 2 -> useFilteringMenu();
                case 3 -> sortProducts();
            }
        }
    }

}
