
import controller.*;
import model.Product;
import repository.memoryRepo.*;
import view.UserView;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
//        InMemoryUserRepository userRepo = new InMemoryUserRepository();
//        RegisteredUserController controller = new RegisteredUserController(null, null, userRepo);
//
//        Integer x = controller.login("ana@yahoo.com", "psswd");
//        System.out.println(x);
//
//        x = controller.login("oana@yahoo.com", "psswd");
//        System.out.println(x);
        InMemoryOrderRepository inMemoryOrderRepository = new InMemoryOrderRepository();
        InMemoryProductRepository inMemoryProductRepository = new InMemoryProductRepository();
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        RegisteredUserController controller = new RegisteredUserController(inMemoryOrderRepository, inMemoryProductRepository, inMemoryUserRepository);
        //userView.showMenu();
        //String x = .get(0).toString();
        System.out.println(inMemoryProductRepository.getProductList());
    }

}