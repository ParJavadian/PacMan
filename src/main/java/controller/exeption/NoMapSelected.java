package controller.exeption;

public class NoMapSelected extends Exception {
    public NoMapSelected() {
        super("Select a map to start");
    }
}
