package view;

import controller.ScoreboardController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class ScoreboardView extends Application implements Initializable {
    private static ScoreboardView instance;
    private static String muteStatus;
    private static MediaPlayer mediaPlayer;
    private static Stage stage;
    private static User user;
    @FXML
    Label scores1 = new Label();
    @FXML
    Label scores2 = new Label();

    public static ScoreboardView getInstance() {
        if (instance == null) instance = new ScoreboardView();
        return instance;
    }

    public static Stage getStage() {
        return ScoreboardView.stage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        ScoreboardView.stage = stage;
        URL url = getClass().getResource("/ScoreboardMenu.fxml");
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String toShow = ScoreboardController.getNames();
        scores1.setText(toShow);
        toShow = ScoreboardController.getHighScores();
        scores2.setText(toShow);
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

    public void setUser(User user) {
        ScoreboardView.user = user;
    }

    public void setMuteStatus(String status) {
        muteStatus = status;
    }

    public void setMediaPlayer(MediaPlayer mediaplayer) {
        mediaPlayer = mediaplayer;
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        if (user != null) {
            MainView mainView = MainView.getInstance();
            MainView.setUser(user);
            mainView.setMuteStatus(muteStatus);
            mainView.start(stage);
        } else {
            WelcomeView welcomeView = new WelcomeView();
            welcomeView.setMuteStatus(muteStatus);
            welcomeView.start(stage);
        }
    }
}
