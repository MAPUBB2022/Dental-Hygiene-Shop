package view;

import controller.RegisteredUserController;
import model.Address;
import model.Order;
import model.RegisteredUser;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserView implements View {
    private final RegisteredUserController controller;

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public UserView(RegisteredUserController controller) {
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
                case 1 -> menuLogin(scanner);
                case 2 -> finishSignUp();
                case 3 -> continueAsGuest();
            }
        }
    }

    public void menuLogin(Scanner scanner) {
        RegisteredUser user;
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
        }
    }

    public void finishSignUp() {
        //prints a successful sign up message and sends new user data to controller
        RegisteredUser user = readNewUser();
        controller.createAccount(user);
        System.out.println("Successfully created an account");
    }

    public RegisteredUser readNewUser() {
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

        return new RegisteredUser(name, email, phoneNumber, password, addresses, defaultAddressId, orderHistory);
    }

    public void continueAsGuest() {
        useGuestMenu();
    }

    public void showGuestMenuPrompt() {
        System.out.println("""

                MAIN MENU
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
                case 0 -> guestMenuExit = true;
                case 1 -> showAllProducts();
                case 2 -> filterProducts();
                case 3 -> sortProducts();
            }
        }
    }

    public void showAllProducts() {
    }

    public void filterProducts() {
        useFilteringMenu();
    }

    public void showFilteringMenuPrompt() {
        System.out.println("""

                MAIN MENU
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
                if (filteringMenuOption < 0 || filteringMenuOption > 3) {
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
        useSortingMenu();
    }

    public void showSortingMenuPrompt() {
        System.out.println("""

                MAIN MENU
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
                if (sortingMenuOption < 0 || sortingMenuOption > 3) {
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
