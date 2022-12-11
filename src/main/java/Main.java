
import controller.*;
import model.*;
import repository.memoryRepo.*;
import view.AdminView;
import view.UserView;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) throws UserNotFoundException, IncorrectPasswordException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
        EntityManager manager = factory.createEntityManager();

        //manager.getTransaction().begin();

        InMemoryOrderRepository inMemoryOrderRepository = new InMemoryOrderRepository();
        InMemoryProductRepository inMemoryProductRepository = new InMemoryProductRepository();
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        Controller controller = new Controller(inMemoryOrderRepository, inMemoryProductRepository, inMemoryUserRepository);
        UserView userView = new UserView(controller);

        userView.useMainMenu();
//        AdminView aview = new AdminView(controller);
//        aview.showAllOrders();
//
//
//        System.out.println("");
//        userView.useMainMenu();
//        AdminView adminView = new AdminView(controller);
//        adminView.showAllOrders();


       // manager.getTransaction().commit();

    }

}