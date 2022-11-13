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
        Scanner scanner = new Scanner(System.in);

        String name, email, phoneNumber, password, country, region, city, street, number, postalCode;
        System.out.println("Name: ");
        name = scanner.next();
        System.out.println("Email:");
        email = scanner.next();
        System.out.println("Phone number: ");
        phoneNumber = scanner.next();
        System.out.println("Password: ");
        password = scanner.next();
        System.out.println("Country: ");
        country = scanner.next();
        System.out.println("Region:");
        region = scanner.next();
        System.out.println("City:");
        city = scanner.next();
        System.out.println("Street: ");
        street = scanner.next();
        System.out.println("Number: ");
        number = scanner.next();
        System.out.println("Postal code: ");
        postalCode = scanner.next();

        Address address = new Address(country, region, city, street, number, postalCode);
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        Integer defaultAddressId = address.getId();
        List<Order> orderHistory = new ArrayList<>();

        return new RegisteredUser(name, email, phoneNumber, password, addresses, defaultAddressId, orderHistory);
    }

    public void continueAsGuest() {
    }

}
