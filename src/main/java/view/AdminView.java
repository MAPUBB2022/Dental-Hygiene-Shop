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

                GUEST MENU
                Options:
                0. Exit
                1. Show all users
                2. Show all orders
                3. Modify products
                4. Modify orders
                """);
    }
    public void useExtendedMenu(){
        int guestMenuOption = -1;
        boolean guestMenuExit = false;
        Scanner scanner = new Scanner(System.in);
        while (!guestMenuExit) {
            try {
                showExtendedMenuPrompt();
                guestMenuOption = Integer.parseInt(scanner.next());
                if (guestMenuOption < 0 || guestMenuOption > 4) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid input");
            }
            switch (guestMenuOption) {
                case 0 -> {
                    guestMenuExit = true;
                }
                case 1 -> showAllUsers();
                case 2 -> showAllOrders();
                case 3 -> modifyProducts();
                case 4 -> modifyOrders();
            }
        }
    }

    public void modifyProducts() {
    }

    public void modifyOrders() {
    }

    public void showAllOrders() {
        System.out.println(controller.getOrderRepository().getOrderList());
    }

    public void showAllUsers(){

    }


}