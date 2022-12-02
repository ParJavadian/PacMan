package controller;

import controller.exeption.*;
import model.User;
import view.*;

public class LoginController {
    public static void loginUser(String username, String password) throws Exception {
        if (username.equals("") || password.equals(""))
            throw new FillAllBoxes();
        if (User.getUserByUsername(username) == null || !User.getUserByUsername(username).getPassword().equals(password))
            throw new DontMatch();
        MainView mainView = MainView.getInstance();
        MainView.setUser(User.getUserByUsername(username));
        mainView.setMuteStatus(LoginView.getMuteStatus());
        mainView.setMediaPlayer(LoginView.getMediaPlayer());
        mainView.start(LoginView.getStage());
    }
}
