
import controller.*;
import model.*;
import repository.databaseRepo.JdbcOrderRepository;
import repository.databaseRepo.JdbcProductRepository;
import repository.databaseRepo.JdbcUserRepository;
import repository.memoryRepo.*;
import view.AdminView;
import view.UserView;
import view.View;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;

import static model.ProductType.*;
import static model.ProductType.MOUTHWASH;
import static model.ProductUse.HOME;

public class Main {

    /**
     *
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * Implement get cart products and empty cart methods in userRepo, 'cause they're implemented in View :"")
     * */

    public static void main(String[] args) {

        InMemoryOrderRepository inMemoryOrderRepository = new InMemoryOrderRepository();
       // InMemoryProductRepository inMemoryProductRepository = new InMemoryProductRepository();
//        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
//        Controller controller = new Controller(inMemoryOrderRepository, inMemoryProductRepository, inMemoryUserRepository);
       JdbcUserRepository ur = new JdbcUserRepository();
        JdbcOrderRepository or = new JdbcOrderRepository();
        JdbcProductRepository pr = new JdbcProductRepository();
        Controller ctrl = new Controller(or, pr, ur);

        int type = View.askAdminLogin();
        if (type == 1){
            AdminView session = new AdminView(ctrl);
            session.useMainMenu();
        } else if (type==0) {
            UserView session = new UserView(ctrl);
            session.useMainMenu();
        }
//
//        Product p1 = new Product("Colgate Max White", "150ml", TOOTHPASTE, 3.5, HOME, 500);
//        Product p2 = new Product("Colgate Max White 3D", "200ml", MOUTHWASH, 13.5, HOME, 150);
//        Product p3 = new Product("Oral-B Silky", "50m", DENTAL_FLOSS, 13.5, HOME, 150);
//        Product p4 = new Product("Oral-B Pro 2", "2pcs", TOOTHBRUSH, 13.5, HOME, 200);
//        Product p5 = new Product("Sensodyne Repair & Protect", "100ml", TOOTHPASTE, 4, HOME, 300);
//        Product p6 = new Product("Listerine Cool", "100ml", MOUTHWASH, 8, HOME, 43);
//
//        pr.add(p1);
//        System.out.println(p1.getId());
//        pr.add(p2);
//        pr.add(p3);
//        pr.add(p4);
//        pr.add(p5);
//        pr.add(p6);
    }

}