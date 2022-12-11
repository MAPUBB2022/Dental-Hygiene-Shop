package view;

import controller.Controller;

import java.util.Scanner;

public class AdminView extends View {

    public AdminView(Controller controller) {
        super(controller);
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
                }
                case 1 -> showAllProducts();
                case 2 -> filterProducts();
                case 3 -> sortProducts();
            }
        }
    }

    public void showExtendedMenuPrompt() {
        System.out.println("""

                EXTENDED MENU
                Options:
                0. Exit
                1. Show all users
                2. Show all orders
                3. Modify products
                4. Filter orders
                """);
    }

    public void useExtendedMenu() {
        int extendedMenuOption = -1;
        boolean extendedMenuExit = false;
        Scanner scanner = new Scanner(System.in);
        while (!extendedMenuExit) {
            try {
                showExtendedMenuPrompt();
                extendedMenuOption = Integer.parseInt(scanner.next());
                if (extendedMenuOption < 0 || extendedMenuOption > 4) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid input");
            }
            switch (extendedMenuOption) {
                case 0 -> {
                    extendedMenuExit = true;
                }
                case 1 -> showAllUsers();
                case 2 -> showAllOrders();
                case 3 -> modifyProducts();
                case 4 -> modifyOrders();
            }
        }
    }

    public void modifyProducts() {
        showModifyProductsMenuPrompt();
        useModifyProductsMenu();
    }

    private void showModifyProductsMenuPrompt() {
        System.out.println("""

                MODIFY PRODUCTS MENU
                Options:
                0. Exit
                1. Modify product name
                2. Modify product base price
                3. Modify product size
                4. Modify product stock
                5. Modify product type
                6. Modify product use
                """);
    }

    private void useModifyProductsMenu() {
        int modifyProductsMenuOption = -1;
        boolean modifyProductsMenuExit = false;
        Scanner scanner = new Scanner(System.in);
        while (!modifyProductsMenuExit) {
            try {
                showModifyProductsMenuPrompt();
                modifyProductsMenuOption = Integer.parseInt(scanner.next());
                if (modifyProductsMenuOption < 0 || modifyProductsMenuOption > 6) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid input");
            }
            switch (modifyProductsMenuOption) {
                case 0 -> {
                    modifyProductsMenuExit = true;
                }
                case 1 -> showAllUsers();
                case 2 -> showAllOrders();
                case 3 -> modifyProducts();
                case 4 -> modifyOrders();
                case 5 -> modifyOrders();
                case 6 -> modifyOrders();
            }
        }
    }

    public void modifyOrders() {
        showModifyOrdersMenuPrompt();
        useModifyOrdersMenu();
    }

    private void showModifyOrdersMenuPrompt() {
        System.out.println("""

                MODIFY ORDERS MENU
                Options:
                0. Exit
                1. Modify product list
                2. Modify delivery adress
                """);
    }//modifica pretul comenzii dupa ce modifici lista de produse comandate

    private void useModifyOrdersMenu() {
        int modifyOrdersMenuOption = -1;
        boolean modifyOrdersMenuExit = false;
        Scanner scanner = new Scanner(System.in);
        while (!modifyOrdersMenuExit) {
            try {
                showModifyOrdersMenuPrompt();
                modifyOrdersMenuOption = Integer.parseInt(scanner.next());
                if (modifyOrdersMenuOption < 0 || modifyOrdersMenuOption > 2) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid input");
            }
            switch (modifyOrdersMenuOption) {
                case 0 -> {
                    modifyOrdersMenuExit = true;
                }
                case 1 -> showAllUsers();
                case 2 -> showAllOrders();
            }
        }
    }

    public void showAllOrders() {
        System.out.println(controller.getOrderRepository().getOrderList());
    }

    public void showAllUsers() {
        System.out.println(controller.getUserRepository().getUserList());
    }


}