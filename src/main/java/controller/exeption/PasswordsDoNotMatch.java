package controller.exeption;

public class PasswordsDoNotMatch extends Exception {
    public PasswordsDoNotMatch() {
        super("Passwords don't match!");
    }
}
