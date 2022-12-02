package model;

import controller.GameController;
import view.GameView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    private static List<User> allUsers = new ArrayList<>();
    private final String username;
    private String password;
    private int highScore;
    private GameController gameController;
    private LocalDateTime highScoreTime;
    private ArrayList<Map> favouriteMaps;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.highScore = 0;
        this.gameController = null;
        this.highScoreTime = LocalDateTime.parse("0001-01-01T00:00:00");
        this.favouriteMaps = new ArrayList<>();
        allUsers.add(this);
    }

    public static User getUserByUsername(String username) {
        if (allUsers != null) {
            for (User user : allUsers) {
                if (user.getUsername().equals(username))
                    return user;
            }
        }
        return null;
    }

    public static void removeUser(String username) {
        allUsers.remove(getUserByUsername(username));
    }

    public static List<User> getAllUsers() {
        return allUsers;
    }

    public GameController getGameController() {
        return gameController;
    }

    public ArrayList<Map> getFavouriteMaps() {
        return this.favouriteMaps;
    }

    public LocalDateTime getHighScoreTime() {
        return this.highScoreTime;
    }

    public void setHighScoreTime(LocalDateTime highScoreTime) {
        this.highScoreTime = highScoreTime;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public int getHighScore() {
        return this.highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(this.username, user.username);
    }
}
