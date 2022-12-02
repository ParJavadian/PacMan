package controller.exeption;

public class WrongPassword extends Exception {
    public WrongPassword() {
        super("Wrong current password!");
    }
}
