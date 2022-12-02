package model;

public class Game {
    private Map map;
    private User user;
    private int score;
    private int lifeNumber;

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLifeNumber() {
        return lifeNumber;
    }

    public Game(Map map, User user, int lifeNumber) {
        this.map = map;
        this.user = user;
        this.lifeNumber = lifeNumber;
        this.score = 0;
    }
}
