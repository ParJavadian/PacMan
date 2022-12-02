package controller;

import controller.exeption.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.*;

public class SelectMapController {

    public static GridPane fillGridPane(GridPane gridPane, Map map) {
        for (int i = 0; i < 31; i++) {
            gridPane.addColumn(i);
        }
        for (int j = 0; j < 21; j++) {
            gridPane.addRow(j);
        }
        for (int i = 0; i < 31; i++) {
            for (int j = 0; j < 21; j++) {
                Rectangle rectangle = new Rectangle(10, 10);
                if (map.getMaze()[i][j] == '1') rectangle.setFill(Color.DARKBLUE);
                else rectangle.setFill(Color.BLACK);
                gridPane.add(rectangle, i, j);
            }
        }
        return gridPane;
    }

    public static void addToFavourites(Map map, User user) throws Exception {
        if (user == null) throw new LoginFirst();
        if (user.getFavouriteMaps().contains(map)) throw new AlreadyAddedToFavourites();
        user.getFavouriteMaps().add(map);
    }
}
