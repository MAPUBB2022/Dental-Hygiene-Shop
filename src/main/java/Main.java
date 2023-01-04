
import controller.*;
import model.*;
import repository.databaseRepo.JdbcOrderRepository;
import repository.databaseRepo.JdbcProductRepository;
import repository.databaseRepo.JdbcUserRepository;
import repository.memoryRepo.*;
import view.AdminView;
import view.UserView;
import view.View;

import java.util.ArrayList;

import static model.ProductType.*;
import static model.ProductType.MOUTHWASH;
import static model.ProductUse.HOME;

public class Main {

    /**
     *
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * user should be able to edit own data
     * 3. Order id: 3userId: 1
     * date, time: 2022-12-19T15:32:42.571498700
     * make this prettier :)
     * Admin: filter orders by date
     * */

    public static void main(String[] args) {

        InMemoryOrderRepository inMemoryOrderRepository = new InMemoryOrderRepository();
        InMemoryProductRepository inMemoryProductRepository = new InMemoryProductRepository();
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        Controller controller = new Controller(inMemoryOrderRepository, inMemoryProductRepository, inMemoryUserRepository);
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

    }

}