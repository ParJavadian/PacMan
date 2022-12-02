package view;

import controller.SelectMapController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.Map;
import model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class SelectMapView extends Application implements Initializable {
    private static SelectMapView instance;
    private static String muteStatus;
    private static MediaPlayer mediaPlayer;
    private static Stage stage;
    private static User user;
    private static Map map;
    private static boolean isFromFavourite;
    private int favouriteCounter;
    @FXML
    private GridPane gridPane;

    public static SelectMapView getInstance() {
        if (instance == null) instance = new SelectMapView();
        return instance;
    }

    @Override
    public void start(Stage stage) throws Exception {
        SelectMapView.stage = stage;
        URL url = getClass().getResource("/SelectMapMenu.fxml");
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (isFromFavourite) {
            map = user.getFavouriteMaps().get(0);
            favouriteCounter = 1;
        } else
            map = new Map();
        gridPane = SelectMapController.fillGridPane(gridPane, map);
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

    public void setIsFromFavourite(boolean isFromFavourite) {
        SelectMapView.isFromFavourite = isFromFavourite;
    }

    public void setUser(User user) {
        SelectMapView.user = user;
    }

    public void setMuteStatus(String status) {
        muteStatus = status;
    }

    public void setMediaPlayer(MediaPlayer mediaplayer) {
        mediaPlayer = mediaplayer;
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        GameSettingsView gameSettingsView = GameSettingsView.getInstance();
        gameSettingsView.setUser(user);
        gameSettingsView.setMuteStatus(muteStatus);
        gameSettingsView.setMediaPlayer(mediaPlayer);
        gameSettingsView.start(stage);
    }

    public void next(ActionEvent actionEvent) {
        if (isFromFavourite) {
            if (favouriteCounter < user.getFavouriteMaps().size()) {
                map = user.getFavouriteMaps().get(favouriteCounter);
                favouriteCounter++;
            } else return;
        } else
            map = new Map();
        gridPane = SelectMapController.fillGridPane(gridPane, map);
    }

    public void select(ActionEvent actionEvent) throws Exception {
        GameSettingsView gameSettingsView = GameSettingsView.getInstance();
        gameSettingsView.setUser(user);
        gameSettingsView.setMuteStatus(muteStatus);
        gameSettingsView.setMediaPlayer(mediaPlayer);
        gameSettingsView.setMap(map);
        gameSettingsView.start(stage);
    }

    public void addToFavourites(ActionEvent actionEvent) {
        try {
            SelectMapController.addToFavourites(map, user);
        } catch (Exception exception) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("");
            error.setContentText(exception.getMessage());
            error.showAndWait();
        }
    }
}