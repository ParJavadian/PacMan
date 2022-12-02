package controller.exeption;

public class RepetitiveUsername extends Exception {
    public RepetitiveUsername() {
        super("User with this username already exists");
    }
}
