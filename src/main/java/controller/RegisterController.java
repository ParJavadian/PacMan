package controller;

import controller.exeption.*;
import model.User;
import view.*;

public class RegisterController {

    public static void createAccount(String username, String password, String passwordConfirm) throws Exception {
        if (username.equals("") || password.equals("") || passwordConfirm.equals(""))
            throw new FillAllBoxes();
        if (User.getUserByUsername(username) != null)
            throw new RepetitiveUsername();
        if (!password.equals(passwordConfirm))
            throw new PasswordsDoNotMatch();
        new User(username, password);
        LoginView loginView = LoginView.getInstance();
        loginView.setMuteStatus(RegisterView.getMuteStatus());
        loginView.setMediaPlayer(RegisterView.getMediaPlayer());
        loginView.start(RegisterView.getStage());
    }

}