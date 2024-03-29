package view;

import controller.Controller;
import model.Address;
import model.Product;
import model.ProductType;
import model.ProductUse;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public abstract class View {

    protected final Controller controller;

    public View(Controller controller) {
        this.controller = controller;
    }

    static boolean checkAdminLogin(String username, String password) {
        return (username.equals("admin") && password.equals("admin"));
    }

    static public int askAdminLogin() {
        System.out.println("Login as admin? y/n");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.next();
        if (answer.equalsIgnoreCase("y")) {
            System.out.println("username: ");
            String username = scanner.next();
            System.out.println("password: ");
            String password = scanner.next();
            if (checkAdminLogin(username, password)) {
                return 1;
            }
        } else {
            return 0;
        }
        return -1;
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

    public Integer readId() {
        Scanner scanner = new Scanner(System.in);
        try {
            return Integer.parseInt(scanner.next());
        } catch (NumberFormatException exc) {
            System.out.println("Input is not a number. Aborting");
            return null;
        }
    }

    public Integer readProductQuantity() {
        System.out.println("Quantity to add to cart (adding negative quantity will remove pieces): ");
        Scanner scanner = new Scanner(System.in);
        try {
            return Integer.parseInt(scanner.next());
        } catch (NumberFormatException exc) {
            System.out.println("Input is not a number. Aborting");
            return null;
        }
    }

    public abstract void useGuestMenu();

    public void showAllProducts() {
        for (Product p : controller.getProductRepository().getProductList()) {
            System.out.println(p);
            if (p.getStock() == 0) {
                System.out.println("OUT OF STOCK");
            }
        }
    }

    public void filterProducts() {
        useFilteringMenu();
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
                filteringMenuOption = Integer.parseInt(scanner.next());
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

    private void filterProductsBySize() {
        System.out.println("Enter (part of) product size: ");
        String size = readString();
        List<Product> filtered = controller.filterByHasInSize(size);
        printProductList(filtered);
    }

    String readProductName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter (part of) product name:");
        return scanner.nextLine();
    }

    Address readAddress() {
        String country, region, city, street, number, postalCode;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Address details:");
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
        return new Address(country, region, city, street, number, postalCode);
    }


    void printProductList(List<Product> list) {
        if (list.isEmpty()) {
            System.out.println("Nothing found");
        } else {
            for (Product p : list) {
                System.out.println(p);
            }
        }
    }

    private void filterProductsByName() {
        String name = readProductName();
        List<Product> filtered = controller.filterByHasInName(name);
        printProductList(filtered);

    }

    ProductType readType() throws IllegalArgumentException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Types: ");
        System.out.println(Arrays.toString(ProductType.values()));
        System.out.println("Enter EXACT product type:");
        String type = scanner.nextLine();
        if (!Arrays.toString(ProductType.values()).contains(type)) {
            throw new IllegalArgumentException();
        }

        return ProductType.valueOf(type);
    }

    private void filterProductsByType() {
        try {
            ProductType type = readType();
            List<Product> filtered = controller.filterByType(type);
            printProductList(filtered);
        } catch (IllegalArgumentException exception) {
            System.out.println("Type does not exist");
        }
    }

    ProductUse readUse() throws IllegalArgumentException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Uses: ");
        System.out.println(Arrays.toString(ProductUse.values()));
        System.out.println("Enter EXACT product use:");
        String type = scanner.nextLine();
        if (!Arrays.toString(ProductUse.values()).contains(type)) {
            throw new IllegalArgumentException();
        }

        return ProductUse.valueOf(type);
    }

    private void filterProductsByUse() {
        try {
            ProductUse use = readUse();
            List<Product> filtered = controller.filterByUse(use);
            printProductList(filtered);
        } catch (IllegalArgumentException exception) {
            System.out.println("Use does not exist");
        }
    }

    public void sortProducts() {
        useSortingMenu();
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
                sortingMenuOption = Integer.parseInt(scanner.next());
                if (sortingMenuOption < 0 || sortingMenuOption > 4) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid option");
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
        List<Product> sorted = controller.sortByPrice(true);
        printProductList(sorted);
    }

    private void sortByPriceDescending() {
        List<Product> sorted = controller.sortByPrice(false);
        printProductList(sorted);
    }

    private void sortByNameAscending() {
        List<Product> sorted = controller.sortByName(true);
        printProductList(sorted);
    }

    private void sortByNameDescending() {
        List<Product> sorted = controller.sortByName(false);
        printProductList(sorted);
    }

    String readString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
