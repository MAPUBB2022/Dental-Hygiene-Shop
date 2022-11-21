package model;

public class UnregisteredUser extends User {

    private static int idCounter = 0;

    public UnregisteredUser(String name, String email, String phoneNumber) {
        super(name, email, phoneNumber);
        idCounter++;
        this.setId(idCounter);
    }

    public void createAccount() {

    }

}
