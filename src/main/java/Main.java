
import controller.*;
import model.Product;
import model.ProductType;
import model.ProductUse;
import repository.memoryRepo.*;
import view.AdminView;
import view.UserView;

public class Main {

    public static void main(String[] args) {

        InMemoryOrderRepository inMemoryOrderRepository = new InMemoryOrderRepository();
        InMemoryProductRepository inMemoryProductRepository = new InMemoryProductRepository();
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        Controller controller = new Controller(inMemoryOrderRepository, inMemoryProductRepository, inMemoryUserRepository);
        UserView userView = new UserView(controller);

        //userView.useMainMenu();
        AdminView aview = new AdminView(controller);
        aview.showAllOrders();

//        userView.showMenu();

    }

}