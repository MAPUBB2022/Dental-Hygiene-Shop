package view;

import controller.RegisteredUserController;
import model.Address;
import model.Order;
import model.RegisteredUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserView implements View {
    private RegisteredUserController controller;

    public void registeredUserViewPrompt() {
        System.out.println("\nMAIN MENU\nOptions:\n0. Exit\n1. Login\n2. Sign up\n3. Continue as guest\n");
    }

    public void registeredUserMenu() {
        int option = -1;
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        while (!exit) {
            try {
                registeredUserViewPrompt();
                option = scanner.nextInt();
                if (option < 0 || option > 7) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid input");
            }
            switch (option) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    String email, password;
                    System.out.println("Enter your email:");
                    email = scanner.next();
                    System.out.println("Enter your password:");
                    password = scanner.next();
                    RegisteredUser user = controller.login(email, password);
                    if (user == null) {
                        System.out.println("Wrong email or password");
                    } else {
                        // next menu
                    }
                case 2:
                    signUp();
                case 3:
                    continueAsGuest();
            }
        }


    }


    public RegisteredUser signUp() {
        String name, email, phoneNumber, password;
        List<Address> addresses = new ArrayList<>();
        Integer defaultAddressId;
        List<Order> orderHistory = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Name: ");
        name = scanner.next();
        System.out.println("Email:");
        email = scanner.next();
        System.out.println("Phone number: ");
        phoneNumber = scanner.next();
        System.out.println("Password: ");
        password = scanner.next();

        RegisteredUser user = new RegisteredUser(name, email, phoneNumber, password, addresses, defaultAddressId, orderHistory);
        return user;
    }

    public void continueAsGuest() {
    }

}
