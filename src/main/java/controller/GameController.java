package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.Map;
import transitions.GhostMove;
import view.GameSettingsView;
import view.GameView;
import view.MainView;
import view.WelcomeView;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static view.GameView.*;

public class GameController implements Initializable {
    public static String muteStatus;
    public static MediaPlayer mediaPlayer, mediaPlayerSaver, eatEnergy, chomp, death;
    private static GameController instance;
    private static GhostMove ghostMoveBlue, ghostMoveOrange, ghostMoveRed, ghostMovePink;
    private GameView gameView = GameView.getInstance();
    @FXML
    public GridPane gridPane;
    @FXML
    public Button pausePlay;
    @FXML
    public Label lifeNumber;
    @FXML
    public Label score;
    private int pacmanX, pacmanY, blueGhostX, blueGhostY, redGhostX, redGhostY, pinkGhostX, pinkGhostY, orangeGhostX, orangeGhostY;
    private boolean inGame = true;
    public boolean isNew = true;

    public static GameController getInstance() {
        if (instance == null) instance = new GameController();
        return instance;
    }

    public static void setInstance(GameController gameController) {
        instance = gameController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillGridPane(game.getMap());
        lifeNumber.setText(String.valueOf(GameView.game.getLifeNumber()));
        gameView.setAll();
        setPlace(GameView.ghostBlue, 1, 1);
        setPlace(GameView.ghostRed, 1, 19);
        setPlace(GameView.ghostPink, 29, 1);
        setPlace(GameView.ghostOrange, 29, 19);
        setPlace(pacman, 15, 10);
        inGame = true;
    }

    private void fillGridPane(Map map) {
        for (int i = 0; i < 31; i++) {
            gridPane.addColumn(i);
        }
        for (int j = 0; j < 21; j++) {
            gridPane.addRow(j);
        }
        for (int i = 0; i < 31; i++) {
            for (int j = 0; j < 21; j++) {
                Rectangle rectangle = new Rectangle(15, 15);
                if (map.getMaze()[i][j] == '1') rectangle.setFill(Color.DARKBLUE);
                else
                    rectangle.setFill(Color.BLACK);
                gridPane.add(rectangle, i, j);
                if (map.getMaze()[i][j] != '1') {
                    Circle circle = new Circle(2);
                    circle.setFill(Color.WHEAT);
                    GridPane.setHalignment(circle, HPos.CENTER);
                    gridPane.add(circle, i, j);
                    circle.toFront();
                }
            }
        }
    }

    private void setPlace(ImageView imageView, int column, int row) {
        imageView.setFitHeight(13);
        imageView.setFitWidth(13);
        GridPane.setHalignment(imageView, HPos.CENTER);
        gridPane.add(imageView, column, row);
    }

    public void getKey() {
        GameView.getStage().getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!inGame) {
                    inGame = true;
                    pausePlay.setText("Pause");
                }
                String keyName = event.getCode().getName();
                switch (keyName) {
                    case "Right":
                        pacman.setImage(GameView.right);
                        if (canGo("right")) {
                            removeDotsAndEnergy(GridPane.getColumnIndex(pacman) + 1, GridPane.getRowIndex(pacman));
                            GridPane.setColumnIndex(pacman, GridPane.getColumnIndex(pacman) + 1);
                            eatPacman();
                        }
                        break;
                    case "Left":
                        pacman.setImage(GameView.left);
                        if (canGo("left")) {
                            removeDotsAndEnergy(GridPane.getColumnIndex(pacman) - 1, GridPane.getRowIndex(pacman));
                            GridPane.setColumnIndex(pacman, GridPane.getColumnIndex(pacman) - 1);
                            eatPacman();
                        }
                        break;
                    case "Up":
                        pacman.setImage(GameView.up);
                        if (canGo("up")) {
                            removeDotsAndEnergy(GridPane.getColumnIndex(pacman), GridPane.getRowIndex(pacman) - 1);
                            GridPane.setRowIndex(pacman, GridPane.getRowIndex(pacman) - 1);
                            eatPacman();
                        }
                        break;
                    case "Down":
                        pacman.setImage(GameView.down);
                        if (canGo("down")) {
                            removeDotsAndEnergy(GridPane.getColumnIndex(pacman), GridPane.getRowIndex(pacman) + 1);
                            GridPane.setRowIndex(pacman, GridPane.getRowIndex(pacman) + 1);
                            eatPacman();
                        }
                        break;
                }
            }
        });

    }

    private void removeDotsAndEnergy(int column, int row) {
        Rectangle rectangle = new Rectangle(15, 15);
        rectangle.setFill(Color.BLACK);
        int nodeCounter = 0;
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == column && GridPane.getRowIndex(node) == row) {
                nodeCounter++;
            }
        }
        if (nodeCounter == 2) {
            score.setText(String.valueOf(Integer.parseInt(score.getText()) + 5));
            gridPane.add(rectangle, column, row);
            if (allDotsEaten()) {
                saveImages();
                gridPane.getChildren().clear();
                rePutImages();
                fillGridPane(game.getMap());
                lifeNumber.setText(String.valueOf(Integer.parseInt(lifeNumber.getText()) + 1));
            }
            bringFront();
        }
    }

    private void saveImages() {
        pacmanX = GridPane.getColumnIndex(pacman);
        pacmanY = GridPane.getRowIndex(pacman);
        blueGhostX = GridPane.getColumnIndex(ghostBlue);
        blueGhostY = GridPane.getRowIndex(ghostBlue);
        redGhostX = GridPane.getColumnIndex(ghostRed);
        redGhostY = GridPane.getRowIndex(ghostRed);
        pinkGhostX = GridPane.getColumnIndex(ghostPink);
        pinkGhostY = GridPane.getRowIndex(ghostPink);
        orangeGhostX = GridPane.getColumnIndex(ghostOrange);
        orangeGhostY = GridPane.getRowIndex(ghostOrange);
    }

    private void rePutImages() {
        gridPane.add(pacman, pacmanX, pacmanY);
        gridPane.add(ghostBlue, blueGhostX, blueGhostY);
        gridPane.add(ghostOrange, orangeGhostX, orangeGhostY);
        gridPane.add(ghostRed, redGhostX, redGhostY);
        gridPane.add(ghostPink, pinkGhostX, pinkGhostY);
    }

    private boolean allDotsEaten() {
        int notEatenCounter = 0;
        for (int i = 1; i < 30; i++) {
            for (int j = 1; j < 20; j++) {
                int nodeCounter = 0;
                for (Node node : gridPane.getChildren()) {
                    if ((GridPane.getColumnIndex(node) == i) && (GridPane.getRowIndex(node) == j) && (node instanceof Rectangle || node instanceof Circle)) {
                        nodeCounter++;
                    }
                }
                if (nodeCounter <= 2 && game.getMap().getMaze()[i][j] != '1') {
                    notEatenCounter++;
                }
            }
        }
        return notEatenCounter == 0;
    }

    public void bringFront() {
        pacman.toFront();
        GameView.ghostOrange.toFront();
        GameView.ghostRed.toFront();
        GameView.ghostPink.toFront();
        GameView.ghostBlue.toFront();
    }

    public void moveGhost(ImageView imageView, int dx, int dy) {
        if (inGame) {
            GridPane.setColumnIndex(imageView, GridPane.getColumnIndex(imageView) + dx);
            GridPane.setRowIndex(imageView, GridPane.getRowIndex(imageView) + dy);
        }
    }

    public void playGhostsAnimation() {
        ghostMoveBlue = new GhostMove(GameView.ghostBlue);
        ghostMoveOrange = new GhostMove(GameView.ghostOrange);
        ghostMoveRed = new GhostMove(GameView.ghostRed);
        ghostMovePink = new GhostMove(GameView.ghostPink);
        ghostMoveBlue.play();
        ghostMoveOrange.play();
        ghostMoveRed.play();
        ghostMovePink.play();
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

    private boolean canGo(String destination) {
        Map map = GameView.game.getMap();
        switch (destination) {
            case "right":
                return !(map.getMaze()[GridPane.getColumnIndex(pacman) + 1][GridPane.getRowIndex(pacman)] == '1');
            case "left":
                return !(map.getMaze()[GridPane.getColumnIndex(pacman) - 1][GridPane.getRowIndex(pacman)] == '1');
            case "up":
                return !(map.getMaze()[GridPane.getColumnIndex(pacman)][GridPane.getRowIndex(pacman) - 1] == '1');
            case "down":
                return !(map.getMaze()[GridPane.getColumnIndex(pacman)][GridPane.getRowIndex(pacman) + 1] == '1');
            default:
                return false;
        }
    }

    public void playMusic() throws Exception {
        Media media = new Media(GameController.class.getResource("/music/Theme.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(AudioClip.INDEFINITE);
        if (muteStatus.equals("Mute")) {
            mediaPlayer.setMute(true);
        }
        mediaPlayer.play();
    }

    private void playEatEnergy() {
        try {
            Media media2 = new Media(GameController.class.getResource("/music/eatEnergy.wav").toURI().toString());
            eatEnergy = new MediaPlayer(media2);
            eatEnergy.setCycleCount(1);
            eatEnergy.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void playChomp() {
        try {
            Media media2 = new Media(GameController.class.getResource("/music/chomp.wav").toURI().toString());
            chomp = new MediaPlayer(media2);
            chomp.setCycleCount(1);
            chomp.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void playDeath() {
        try {
            Media media2 = new Media(GameController.class.getResource("/music/death.wav").toURI().toString());
            death = new MediaPlayer(media2);
            death.setCycleCount(1);
            death.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void pauseOrPlay(ActionEvent actionEvent) {
        if (pausePlay.getText().equals("Pause")) {
            inGame = false;
            pausePlay.setText("Play");
        } else if (pausePlay.getText().equals("Play")) {
            inGame = true;
            pausePlay.setText("Pause");
        }
    }

    public void eatPacman() {
        if (isOnPacman(ghostBlue) || isOnPacman(ghostOrange) || isOnPacman(ghostPink) | isOnPacman(ghostRed)) {
            lifeNumber.setText(String.valueOf(Integer.parseInt(lifeNumber.getText()) - 1));
            if (lifeNumber.getText().equals("0")) endGame();
            else {
                if (muteStatus.equals("UnMute")) playChomp();
                GridPane.setColumnIndex(pacman, 15);
                GridPane.setRowIndex(pacman, 10);
            }
        }
    }

    private boolean isOnPacman(ImageView ghost) {
        return (GridPane.getColumnIndex(ghost).equals(GridPane.getColumnIndex(pacman)) && GridPane.getRowIndex(ghost).equals(GridPane.getRowIndex(pacman)));
    }

    private void endGame() {
        if (game.getUser() != null && game.getUser().getHighScore() < Integer.parseInt(score.getText())) {
            game.getUser().setHighScore(Integer.parseInt(score.getText()));
            game.getUser().setHighScoreTime(LocalDateTime.now());
        }
        ghostMoveBlue.stop();
        ghostMoveRed.stop();
        ghostMoveOrange.stop();
        ghostMovePink.stop();
        if (muteStatus.equals("UnMute")) playDeath();
        ButtonType newGame = new ButtonType("Start New Game");
        ButtonType mainMenu = new ButtonType("Go Back");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You lost the game!", newGame, mainMenu);
        alert.setTitle("GAME OVER");
        alert.setHeaderText("");
        alert.show();
        alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
            @Override
            public void handle(DialogEvent event) {
                ButtonType result = alert.getResult();
                if (result.equals(newGame)) {
                    goToStartNewGame();
                } else if (result.equals(mainMenu)) {
                    if (game.getUser() == null) {
                        goToWelcomeMenu();
                    } else {
                        goToMainMenu();
                    }
                }
            }
        });
    }

    private void goToStartNewGame() {
        GameSettingsView gameSettingsView = GameSettingsView.getInstance();
        gameSettingsView.setMap(game.getMap());
        gameSettingsView.setMuteStatus(muteStatus);
        gameSettingsView.setMediaPlayer(mediaPlayerSaver);
        gameSettingsView.setUser(game.getUser());
        mediaPlayer.pause();
        try {
            gameSettingsView.start(GameView.getStage());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void goToWelcomeMenu() {
        WelcomeView welcomeView = WelcomeView.getInstance();
        welcomeView.setMuteStatus(muteStatus);
        welcomeView.setMediaPlayer(mediaPlayerSaver);
        mediaPlayer.pause();
        try {
            welcomeView.start(GameView.getStage());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void goToMainMenu() {
        MainView mainView = MainView.getInstance();
        mainView.setMuteStatus(muteStatus);
        mainView.setMediaPlayer(mediaPlayerSaver);
        mediaPlayer.pause();
        MainView.setUser(game.getUser());
        try {
            mainView.start(GameView.getStage());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void back(ActionEvent actionEvent) {
        ghostMoveBlue.stop();
        ghostMoveOrange.stop();
        ghostMoveRed.stop();
        ghostMovePink.stop();
        goToStartNewGame();
    }
}
