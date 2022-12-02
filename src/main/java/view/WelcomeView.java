package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.media.Media;

import java.net.URL;

public class WelcomeView extends Application {
    private static WelcomeView instance;
    private static MediaPlayer mediaPlayer;
    private static Stage stage;
    private static String muteStatus;

    public static WelcomeView getInstance() {
        if (instance == null) instance = new WelcomeView();
        return instance;
    }

    @Override
    public void start(Stage stage) throws Exception {
        WelcomeView.stage = stage;
        URL loginUrl = getClass().getResource("/WelcomeMenu.fxml");
        Parent root = FXMLLoader.load(loginUrl);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Pac-Man");
        stage.getIcons().add(new Image("/images/icon.jpg"));
        stage.show();
        mediaPlayer.play();
        if (muteStatus.equals("Mute")) mediaPlayer.setMute(true);
    }

    public void playMusic() throws Exception {
        muteStatus = "UnMute";
        Media media = new Media(WelcomeView.class.getResource("/music/Music.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(AudioClip.INDEFINITE);
        mediaPlayer.play();
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

    public void setMuteStatus(String status) {
        muteStatus = status;
    }

    public void startNewGame(ActionEvent actionEvent) throws Exception {
        GameSettingsView gameSettingsView = GameSettingsView.getInstance();
        gameSettingsView.setMediaPlayer(mediaPlayer);
        gameSettingsView.setMuteStatus(muteStatus);
        gameSettingsView.setUser(null);
        gameSettingsView.setMap(null);
        gameSettingsView.start(stage);
    }

    public void openScoreboard(ActionEvent actionEvent) throws Exception {
        ScoreboardView scoreboardView = ScoreboardView.getInstance();
        scoreboardView.setUser(null);
        scoreboardView.setMuteStatus(muteStatus);
        scoreboardView.setMediaPlayer(mediaPlayer);
        scoreboardView.start(stage);
    }

    public void openRegister(ActionEvent actionEvent) throws Exception {
        RegisterView registerView = RegisterView.getInstance();
        registerView.setMediaPlayer(mediaPlayer);
        registerView.setMuteStatus(muteStatus);
        registerView.start(stage);
    }

    public void exit(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void setStage(Stage stage1) {
        stage = stage1;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        WelcomeView.mediaPlayer = mediaPlayer;
    }
}
