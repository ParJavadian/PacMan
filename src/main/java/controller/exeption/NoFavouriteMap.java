package controller.exeption;

public class NoFavouriteMap extends Exception {
    public NoFavouriteMap() {
        super("You Have No Favourite Map");
    }
}
