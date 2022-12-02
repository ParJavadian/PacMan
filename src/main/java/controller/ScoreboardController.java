package controller;

import model.User;
import java.util.Comparator;
import java.util.List;


public class ScoreboardController {

    public static String getNames() {
        List<User> allUsers = User.getAllUsers();
        Comparator<User> userComparator = Comparator.comparing(User::getHighScore, Comparator.reverseOrder())
                .thenComparing(User::getHighScoreTime).thenComparing(User::getUsername);
        allUsers.sort(userComparator);
        User previousUser = null;
        int rank = 1;
        String toReturn = "   Name\n\n";
        int i = 1;
        int userCounter = 0;
        for (User eachUser : allUsers) {
            if (userCounter >= 10) {
                return toReturn;
            } else {
                if (previousUser != null) {
                    if (previousUser.getHighScore() == eachUser.getHighScore()) {
                        i++;
                    } else {
                        rank += i;
                        i = 1;
                    }
                }
                if (allUsers.indexOf(eachUser) == allUsers.size() - 1) {
                    toReturn += rank + "- " + eachUser.getUsername();
                } else {
                    toReturn += rank + "- " + eachUser.getUsername() + "\n";
                }
                previousUser = eachUser;
                userCounter++;
            }
        }
        return toReturn;
    }


    public static String getHighScores() {
        List<User> allUsers = User.getAllUsers();
        Comparator<User> userComparator = Comparator.comparing(User::getHighScore, Comparator.reverseOrder()).thenComparing(User::getHighScoreTime).thenComparing(User::getUsername);
        allUsers.sort(userComparator);
        String toReturn = "High Score\n\n";
        int userCounter = 0;
        for (User eachUser : allUsers) {
            if (userCounter >= 10) {
                return toReturn;
            } else {
                if (allUsers.indexOf(eachUser) == allUsers.size() - 1) {
                    toReturn += eachUser.getHighScore();
                } else {
                    toReturn += eachUser.getHighScore() + "\n";
                }
                userCounter++;
            }
        }
        return toReturn;
    }
}
