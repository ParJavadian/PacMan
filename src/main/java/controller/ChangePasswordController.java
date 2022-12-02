package controller;

import controller.exeption.*;
import javafx.stage.Stage;
import model.User;
import view.*;

public class ChangePasswordController {
    public void changePassword(Stage stage, User user, String currentPassword, String newPassword, String newPasswordConfirm) throws Exception {
        if (currentPassword.equals("") || newPassword.equals("") || newPasswordConfirm.equals(""))
            throw new FillAllBoxes();
        if (!user.getPassword().equals(currentPassword))
            throw new WrongPassword();
        if (!newPassword.equals(newPasswordConfirm))
            throw new PasswordsDoNotMatch();
        if (newPassword.equals(user.getPassword()))
            throw new SamePassword();
        user.setPassword(newPassword);
        MainView mainView = MainView.getInstance();
        MainView.setUser(user);
        mainView.setMuteStatus(ChangePasswordView.getMuteStatus());
        mainView.start(stage);
    }
}
