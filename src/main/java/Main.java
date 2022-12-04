
import controller.*;
import repository.memoryRepo.*;
import view.UserView;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
        EntityManager manager = factory.createEntityManager();

        manager.getTransaction().begin();

//        InMemoryUserRepository userRepo = new InMemoryUserRepository();
//        Controller controller = new Controller(null, null, userRepo);
//
//        Integer x = controller.login("ana@yahoo.com", "psswd");
//        System.out.println(x);
//
//        x = controller.login("oana@yahoo.com", "psswd");
//        System.out.println(x);
        InMemoryOrderRepository inMemoryOrderRepository = new InMemoryOrderRepository();
        InMemoryProductRepository inMemoryProductRepository = new InMemoryProductRepository();
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        Controller controller = new Controller(inMemoryOrderRepository, inMemoryProductRepository, inMemoryUserRepository);
        UserView userView = new UserView(controller);
        userView.useMainMenu();

//        userView.showMenu();

        manager.getTransaction().commit();

    }

}