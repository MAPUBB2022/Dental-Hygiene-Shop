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

    public UserView(RegisteredUserController controller) {
        this.controller = controller;
    }

    public void showPrompt() {
        System.out.println("\nMAIN MENU\nOptions:\n0. Exit\n1. Login\n2. Sign up\n3. Continue as guest\n");
    }

    public void showMenu() {
        int option = -1;
        boolean exit = false;
        RegisteredUser user;
        Scanner scanner = new Scanner(System.in);
        while (!exit) {
            try {
                showPrompt();
                option = scanner.nextInt();
                if (option < 0 || option > 3) {
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
                    user = controller.login(email, password);
                    if (user == null) {
                        System.out.println("Wrong email or password");
                    } else {
                        System.out.println(user.toString());
                    }
                    break;
                case 2:
                    user = signUp();
                    controller.createAccount(user);
                    System.out.println("Successfully created an account");
                    System.out.println(user.toString());
                    break;
                case 3:
                    continueAsGuest();
                    break;
            }
        }


    }


    public RegisteredUser signUp() {
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
    }

}
