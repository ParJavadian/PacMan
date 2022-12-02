package controller;

import controller.exeption.*;
import model.*;

public class GameSettingsController {
    public static void selectMapFromFavourites(User user) throws Exception {
        if (user == null) throw new LoginFirst();
        if (user.getFavouriteMaps().isEmpty()) throw new NoFavouriteMap();
    }

    public static void newGame(Map map) throws Exception {
        if (map == null) {
            throw new NoMapSelected();
        }
    }
}
