
import controller.*;
import model.*;
import repository.databaseRepo.JdbcUserRepository;
import repository.memoryRepo.*;
import view.AdminView;
import view.UserView;
import view.View;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        InMemoryOrderRepository inMemoryOrderRepository = new InMemoryOrderRepository();
        InMemoryProductRepository inMemoryProductRepository = new InMemoryProductRepository();
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        Controller controller = new Controller(inMemoryOrderRepository, inMemoryProductRepository, inMemoryUserRepository);
        JdbcUserRepository ur = new JdbcUserRepository();


//        Address address = new Address("USA", "NY", "NY", "5h Ave", "12", "12345");
//        address.setId(1);
//        User user1 = new User("Ana", "ana@yahoo.com", "01234",
//                "psswd", address, new ArrayList<Order>());
        ur.modifyName(2, "Ana Banana");

//        int type = View.askAdminLogin();
//        if (type == 1){
//            AdminView session = new AdminView(controller);
//            session.useMainMenu();
//        } else if (type==0) {
//            UserView session = new UserView(controller);
//            session.useMainMenu();
//        }

    }

}