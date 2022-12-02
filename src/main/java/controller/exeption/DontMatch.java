package controller.exeption;

public class DontMatch extends Exception {
    public DontMatch() {
        super("Username and password don’t match!");
    }
}
