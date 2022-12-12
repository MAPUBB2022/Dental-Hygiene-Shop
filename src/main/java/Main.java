
import controller.*;
import model.*;
import repository.memoryRepo.*;
import view.AdminView;
import view.UserView;
import view.View;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {

        InMemoryOrderRepository inMemoryOrderRepository = new InMemoryOrderRepository();
        InMemoryProductRepository inMemoryProductRepository = new InMemoryProductRepository();
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        Controller controller = new Controller(inMemoryOrderRepository, inMemoryProductRepository, inMemoryUserRepository);

        int type = View.askAdminLogin();
        if (type == 1){
            AdminView session = new AdminView(controller);
            session.useMainMenu();
        } else if (type==0) {
            UserView session = new UserView(controller);
            session.useMainMenu();
        }
        //UserView userView = new UserView(controller);

        //userView.useMainMenu();
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