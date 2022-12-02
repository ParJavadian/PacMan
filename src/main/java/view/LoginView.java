package view;

import controller.LoginController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.net.URL;

public class LoginView extends Application {
    private static LoginView instance;
    private static MediaPlayer mediaPlayer;
    private static Stage stage;
    private static String muteStatus;
    @FXML
    TextField username, password;

    public static LoginView getInstance() {
        if (instance == null) instance = new LoginView();
        return instance;
    }

    public static String getMuteStatus() {
        return muteStatus;
    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public static Stage getStage() {
        return LoginView.stage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        LoginView.stage = stage;
        URL url = getClass().getResource("/LoginMenu.fxml");
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void loginUser(MouseEvent mouseEvent) {
        try {
            LoginController.loginUser(username.getText(), password.getText());
        } catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("");
            error.setContentText(e.getMessage());
            error.showAndWait();
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

    public void setMuteStatus(String status) {
        muteStatus = status;
    }

    public void setMediaPlayer(MediaPlayer mediaplayer) {
        mediaPlayer = mediaplayer;
    }

    public void openRegisterMenu(MouseEvent mouseEvent) throws Exception {
        RegisterView registerView = RegisterView.getInstance();
        registerView.setMuteStatus(muteStatus);
        registerView.start(stage);
    }

    public void back(ActionEvent actionEvent) throws Exception {
        WelcomeView welcomeView = new WelcomeView();
        welcomeView.setMuteStatus(muteStatus);
        welcomeView.start(stage);
    }
}
