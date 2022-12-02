package controller.exeption;

public class SamePassword extends Exception {
    public SamePassword() {
        super("Please enter a new password");
    }
}
