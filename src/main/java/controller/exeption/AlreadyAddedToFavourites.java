package controller.exeption;

public class AlreadyAddedToFavourites extends Exception {
    public AlreadyAddedToFavourites() {
        super("You Have Already Added This Map To Your Favourites");
    }
}
