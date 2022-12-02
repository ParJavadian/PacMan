package view;

import controller.GameSettingsController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.*;

import java.net.URL;

public class GameSettingsView extends Application {
    private static GameSettingsView instance;
    private static String muteStatus;
    private static MediaPlayer mediaPlayer;
    private static Stage stage;
    private static User user;
    private static Map map;
    @FXML
    private Label lifeNumber;

    public static GameSettingsView getInstance() {
        if (instance == null) instance = new GameSettingsView();
        return instance;
    }

    @Override
    public void start(Stage stage) throws Exception {
        GameSettingsView.stage = stage;
        URL url = getClass().getResource("/GameSettingsMenu.fxml");
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        mediaPlayer.play();
        if (muteStatus.equals("Mute")) mediaPlayer.setMute(true);
    }

    public void setUser(User user) {
        GameSettingsView.user = user;
    }

    public void setMuteStatus(String status) {
        muteStatus = status;
    }

    public void setMediaPlayer(MediaPlayer mediaplayer) {
        mediaPlayer = mediaplayer;
    }

    public void setMap(Map map) {
        GameSettingsView.map = map;
    }

    public void increaseLifeNumber(ActionEvent actionEvent) {
        int lifeInt = Integer.parseInt(lifeNumber.getText());
        if (lifeInt < 5 && lifeInt > 1) {
            lifeNumber.setText(String.valueOf(lifeInt + 1));
        }
    }

    public void decreaseLifeNumber(ActionEvent actionEvent) {
        int lifeInt = Integer.parseInt(lifeNumber.getText());
        if (lifeInt < 6 && lifeInt > 2) {
            lifeNumber.setText(String.valueOf(lifeInt - 1));
        }
    }

    public void changeSoundStatus(MouseEvent mouseEvent) {
        if (muteStatus.equals("UnMute")) {
            mediaPlayer.setMute(true);
            muteStatus = "Mute";
        } else {
            mediaPlayer.setMute(false);
            muteStatus = "UnMute";
        }
    }

    public void back(ActionEvent actionEvent) throws Exception {
        if (user != null) {
            MainView mainView = MainView.getInstance();
            MainView.setUser(user);
            mainView.setMuteStatus(muteStatus);
            mainView.start(stage);
        } else {
            WelcomeView welcomeView = WelcomeView.getInstance();
            welcomeView.setMuteStatus(muteStatus);
            welcomeView.start(stage);
        }
    }

    public void selectNewMap(MouseEvent mouseEvent) throws Exception {
        SelectMapView selectMapView = SelectMapView.getInstance();
        selectMapView.setUser(user);
        selectMapView.setMediaPlayer(mediaPlayer);
        selectMapView.setMuteStatus(muteStatus);
        selectMapView.setIsFromFavourite(false);
        selectMapView.start(stage);
    }

    public void selectMapFromFavourites(MouseEvent mouseEvent) {
        try {
            GameSettingsController.selectMapFromFavourites(user);
            SelectMapView selectMapView = SelectMapView.getInstance();
            selectMapView.setUser(user);
            selectMapView.setMediaPlayer(mediaPlayer);
            selectMapView.setMuteStatus(muteStatus);
            selectMapView.setIsFromFavourite(true);
            selectMapView.start(stage);
        } catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("");
            error.setContentText(e.getMessage());
            error.showAndWait();
        }
    }

    public void startNewGame(ActionEvent actionEvent){
        try {
        GameSettingsController.newGame(map);
        Game game = new Game(map, user, Integer.parseInt(lifeNumber.getText()));
        GameView gameView = GameView.getInstance();
        gameView.setGame(game);
        gameView.setMuteStatus(muteStatus);
        gameView.setMediaPlayer(mediaPlayer);
        mediaPlayer.pause();
        gameView.start(stage);
        } catch (Exception exception) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("");
            error.setContentText(exception.getMessage());
            error.showAndWait();
        }
    }
}
