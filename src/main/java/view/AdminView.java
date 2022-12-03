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
                3. Modify product info
                4. 
                """);
    }
    public void useExtendedMenu(){
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
}