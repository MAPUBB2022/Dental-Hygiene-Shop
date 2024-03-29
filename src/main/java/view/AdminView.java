package view;

import controller.Controller;
import model.*;

import java.util.Scanner;

public class AdminView extends View {

    public AdminView(Controller controller) {
        super(controller);
    }

    void showMainMenuPrompt() {
        System.out.println("""
                0. Exit
                1. Use guest menu
                2. Use extended menu""");
    }

    public void useMainMenu() {
        int option = -1;
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        while (!exit) {
            try {
                showMainMenuPrompt();
                option = Integer.parseInt(scanner.next());
                if (option < 0 || option > 2) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid input");
            }
            switch (option) {
                case 0 -> {
                    exit = true;
                }
                case 1 -> useGuestMenu();
                case 2 -> useExtendedMenu();
            }
        }

    }

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
                case 2 -> useFilteringMenu();
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
                4. Modify orders
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
                7. Add product
                8. Delete product
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
                if (modifyProductsMenuOption < 0 || modifyProductsMenuOption > 8) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid input");
            }
            switch (modifyProductsMenuOption) {
                case 0 -> {
                    modifyProductsMenuExit = true;
                }
                case 1 -> modifyProductName();
                case 2 -> modifyProductPrice();
                case 3 -> modifyProductSize();
                case 4 -> modifyProductStock();
                case 5 -> modifyProductType();
                case 6 -> modifyProductUse();
                case 7 -> addProduct();
                case 8 -> deleteProduct();
            }
        }
    }

    void deleteProduct() {
        Integer id = null;
        while (id == null) {
            System.out.println("ID of product to delete:");
            id = readId();
        }
        controller.deleteProduct(id);

    }

    void addProduct() {
        String name, size;
        ProductType type = null;
        ProductUse use = null;
        Integer stock = null;
        Double basePrice = null;

        System.out.println("Enter product details: ");
        System.out.println("name: ");
        name = readString();

        while (basePrice == null) {
            System.out.println("base price: ");
            basePrice = readPrice();
        }

        System.out.println("size: ");
        size = readString();

        while (stock == null) {
            System.out.println("stock: ");
            stock = readStock();
        }

        while (true) {
            try {
                type = readType();
            } catch (IllegalArgumentException e) {
                System.out.println("reenter type");
            }

            try {
                use = readUse();
                Product product = new Product(name, size, type, basePrice, use, stock);
                controller.addProduct(product);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("reenter use");
            }
        }
    }

    public void modifyProductSize() {
        System.out.println("Product ID:");
        Integer productId = readId();
        System.out.println("New product size:");
        String newSize = readString();
        try {
            Product newProduct = new Product(controller.findProductById(productId));
            newProduct.setSize(newSize);
            controller.modifyProduct(productId, newProduct);
        } catch (ProductNotInRepositoryException e) {
            System.out.println("Product does not exist");
        }

    }

    private void modifyProductUse() {
        System.out.println("Product ID:");
        Integer productId = readId();
        System.out.println("Specify new use for product: ");
        ProductUse use = readUse();
        try {
            Product newProduct = new Product(controller.findProductById(productId));
            newProduct.setUse(use);
            controller.modifyProduct(productId, newProduct);
        } catch (ProductNotInRepositoryException e) {
            System.out.println("Product does not exist");
        }
    }

    String readNameAdmin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter new product name:");
        return scanner.nextLine();
    }


    public void modifyProductName() {
        System.out.println("Product ID:");
        Integer productId = readId();
        String newName = readNameAdmin();
        try {
            Product newProduct = new Product(controller.findProductById(productId));
            newProduct.setName(newName);
            controller.modifyProduct(productId, newProduct);
        } catch (ProductNotInRepositoryException e) {
            System.out.println("Product does not exist");
        }
    }

    Double readPrice() {
        Scanner scanner = new Scanner(System.in);
        try {
            return Double.parseDouble(scanner.next());
        } catch (NumberFormatException exc) {
            System.out.println("Input is not a number. Aborting");
            return null;
        }
    }

    public void modifyProductPrice() {
        System.out.println("Product ID:");
        Integer productId = readId();
        System.out.println("New price of product: ");
        double newPrice = readPrice();
        try {
            Product newProduct = new Product(controller.findProductById(productId));
            newProduct.setBasePrice(newPrice);
            controller.modifyProduct(productId, newProduct);
        } catch (ProductNotInRepositoryException e) {
            System.out.println("Product does not exist");
        }
    }

    Integer readStock() {
        int stock = 0;
        Scanner scanner = new Scanner(System.in);
        try {
            stock = Integer.parseInt(scanner.next());
            if (stock < 0) {
                throw new IllegalArgumentException();
            }
        } catch (NumberFormatException exc) {
            System.out.println("Input is not a number. Aborting");
            return null;
        } catch (IllegalArgumentException e) {
            System.out.println("Stock can't be negative");
        }
        return stock;
    }

    public void modifyProductStock() {
        Integer id = readId();
        System.out.println("New stock: ");
        int newStock = readStock();
        Product product;
        try {
            product = controller.findProductById(id);
            product.setStock(newStock);
            controller.modifyProduct(id, product);
        } catch (ProductNotInRepositoryException e) {
            System.out.println("Product does not exist");
        }

    }


    public void modifyProductType() {
        System.out.println("Specify new type for product: ");
        ProductType newType = readType();
        System.out.println("Product ID:");
        Integer productId = readId();
        try {
            Product newProduct = new Product(controller.findProductById(productId));
            newProduct.setType(newType);
            controller.modifyProduct(productId, newProduct);
        } catch (ProductNotInRepositoryException e) {
            System.out.println("Product does not exist");
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
                2. Modify delivery address
                """);
    }

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
                case 1 -> modifyProductList();
                case 2 -> modifyDeliveryAddress();
            }
        }
    }


    private void modifyDeliveryAddress() {
        System.out.println("Order ID:");
        Integer orderId = readId();
        Address newAddress = readAddress();
        try {
            controller.modifyOrderDeliveryAddress(orderId, newAddress);
        } catch (OrderNotInRepositoryException e) {
            System.out.println("Order doesn't exist");
        }
    }

    private void modifyProductList() {
        System.out.println("Order ID:");
        Integer orderId = readId();
        System.out.println("ID of product to remove from list:");
        Integer productId = readId();
        try {
            controller.modifyOrderProductList(orderId, productId);
        } catch (OrderNotInRepositoryException e) {
            System.out.println("Order doesn't exist");
        } catch (ProductNotInRepositoryException e) {
            System.out.println("Product doesn't exist");
        }
    }

    public void showAllOrders() {
        System.out.println(controller.getOrderRepository().getOrderList());
    }

    public void showAllUsers() {
        for (User u : controller.getUserRepository().getUserList()) {
            System.out.println("Name: " + u.getName());
            System.out.println("Email: " + u.getEmail());
            System.out.println();
        }
    }

}