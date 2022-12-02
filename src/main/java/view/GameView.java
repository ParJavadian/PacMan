package view;

import controller.GameController;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.Game;


public class GameView extends Application {
    private static GameView instance;
    private static Stage stage;
    public static Game game;
    public static GameController gameController;
    public static ImageView ghostBlue, ghostPink, ghostRed, ghostOrange, pacman;
    public static final Image up = new Image("/images/up.gif"),
            down = new Image("/images/down.jpg"),
            right = new Image("/images/right.jpg"),
            left = new Image("/images/left.jpg");

    public static GameView getInstance() {
        if (instance == null) instance = new GameView();
        return instance;
    }

    public static Stage getStage() {
        return GameView.stage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        GameView.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("/GameMenu.fxml").openStream());
        if (game == null || game.getUser() == null || game.getUser().getGameController() == null) {
            gameController = fxmlLoader.getController();
            gameController.isNew = true;
        } else {
            System.out.println(game.getUser().getGameController());
            gameController = game.getUser().getGameController();
            gameController.isNew = false;
        }
        GameController.setInstance(gameController);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        pacman.requestFocus();
        stage.show();
        gameController.playMusic();
        gameController.playGhostsAnimation();
        gameController.getKey();
    }

    public void setGame(Game game) {
        GameView.game = game;
    }

    public void setMuteStatus(String status) {
        GameController.muteStatus = status;
    }

    public void setMediaPlayer(MediaPlayer mediaplayer) {
        GameController.mediaPlayerSaver = mediaplayer;
    }

    public void setAll() {
        ghostBlue = new ImageView("/images/ghost_blue.gif");
        ghostPink = new ImageView("/images/ghost_pink.gif");
        ghostRed = new ImageView("/images/ghost_red.gif");
        ghostOrange = new ImageView("/images/ghost_orange.gif");
        pacman = new ImageView(right);
    }

}
