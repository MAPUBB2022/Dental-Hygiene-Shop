
import controller.*;
import repository.memoryRepo.*;

public class Main {

    public static void main(String[] args){
        InMemoryUserRepository userRepo = new InMemoryUserRepository();
        RegisteredUserController controller = new RegisteredUserController(null, null, userRepo);

        Integer x = controller.login("ana@yahoo.com", "psswd");
        System.out.println(x);

        x = controller.login("oana@yahoo.com", "psswd");
        System.out.println(x);
    }

}