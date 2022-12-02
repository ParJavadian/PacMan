import javafx.application.Application;
import javafx.stage.Stage;
import view.WelcomeView;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        WelcomeView welcomeView = WelcomeView.getInstance();
        welcomeView.playMusic();
        welcomeView.start(primaryStage);
    }
}
