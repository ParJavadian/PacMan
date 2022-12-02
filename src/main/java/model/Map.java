package model;

import java.util.ArrayList;
import java.util.Collections;

public class Map {
    private char[][] maze;

    public Map() {
        getRandomMaze();
    }

    public char[][] getMaze() {
        return this.maze;
    }

    private void getRandomMaze() {
        maze = new char[31][21];
        int currentX = 1, currentY = 1;
        int[][] xOfOrigin = new int[31][21];
        int[][] yOfOrigin = new int[31][21];
        int[][] wasUsedInBacktracking = new int[31][21];
        int[][] wasPassedBy = new int[31][21];
        makeMazesEmpty();
        createNewBoard(maze, xOfOrigin, yOfOrigin, wasUsedInBacktracking, wasPassedBy);
        makeMaze(maze, currentY, currentX, xOfOrigin, yOfOrigin, wasUsedInBacktracking, wasPassedBy);
        removeSomeFromMaze();
        maze[15][10] = '0';
    }

    private void removeSomeFromMaze() {
        ArrayList<Integer> randomNumber31s = new ArrayList<>();
        for (int i = 1; i < 30; i++)
            randomNumber31s.add(i);
        Collections.shuffle(randomNumber31s);
        ArrayList<Integer> randomNumber21s = new ArrayList<>();
        for (int i = 1; i < 20; i++)
            randomNumber21s.add(i);
        Collections.shuffle(randomNumber21s);
        int k = 0;
        while (k <= 75) {
            if (maze[randomNumber31s.get(0)][randomNumber21s.get(0)] == '1') {
                if (maze[randomNumber31s.get(0) - 1][randomNumber21s.get(0)] == '0' ||
                        maze[randomNumber31s.get(0) + 1][randomNumber21s.get(0)] == '0' ||
                        maze[randomNumber31s.get(0)][randomNumber21s.get(0) - 1] == '0' ||
                        maze[randomNumber31s.get(0)][randomNumber21s.get(0) + 1] == '0') {
                    maze[randomNumber31s.get(0)][randomNumber21s.get(0)] = '0';
                    k++;
                }
            }
            Collections.shuffle(randomNumber31s);
            Collections.shuffle(randomNumber21s);
        }
    }

    private void makeMazesEmpty() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                maze[i][j] = '0';
            }
        }
    }

    private void createNewBoard(char[][] board, int[][] xOfOrigin, int[][] yOfOrigin,
                                int[][] wasUsedInBacktracking, int[][] wasPassedBy) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (i % 2 == 0 || j % 2 == 0) board[i][j] = '1';
                else {
                    board[i][j] = '*';
                    xOfOrigin[i][j] = 0;
                    yOfOrigin[i][j] = 0;
                    wasUsedInBacktracking[i][j] = 0;
                    wasPassedBy[i][j] = 0;
                }
            }
        }
        wasPassedBy[1][1] = 1;
    }

    private Integer[] generateRandomDirections() {
        ArrayList<Integer> randomNumbers = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            randomNumbers.add(i + 1);
        Collections.shuffle(randomNumbers);
        return randomNumbers.toArray(new Integer[4]);
    }

    private void updateBoard(int thisYOfOrigin, int thisXOfOrigin, char[][] board, int currentY,
                             int currentX, int[][] xOfOrigin, int[][] yOfOrigin, int[][] wasUsedInBacktracking, int[][] wasPassedBy) {
        xOfOrigin[thisYOfOrigin][thisXOfOrigin] = currentX;
        yOfOrigin[thisYOfOrigin][thisXOfOrigin] = currentY;
        wasPassedBy[thisYOfOrigin][thisXOfOrigin] = 1;
        makeMaze(board, thisYOfOrigin, thisXOfOrigin, xOfOrigin, yOfOrigin, wasUsedInBacktracking, wasPassedBy);
    }

    private void makeMaze(char[][] board, int currentY, int currentX, int[][] xOfOrigin, int[][] yOfOrigin,
                          int[][] wasUsedInBacktracking, int[][] wasPassedBy) {
        Integer[] randomDirections = generateRandomDirections();
        for (int i = 0; i < 4; i++) {
            switch (randomDirections[i]) {
                case 1:
                    if (currentY == 1)
                        continue;
                    if (board[currentY - 1][currentX] == '1' && wasPassedBy[currentY - 2][currentX] == 0) {
                        board[currentY - 1][currentX] = '0';
                        updateBoard(currentY - 2, currentX, board, currentY, currentX, xOfOrigin, yOfOrigin, wasUsedInBacktracking, wasPassedBy);
                    }
                    break;
                case 2:
                    if (currentY == board.length - 2)
                        continue;
                    if (board[currentY + 1][currentX] == '1' && wasPassedBy[currentY + 2][currentX] == 0) {
                        board[currentY + 1][currentX] = '0';
                        updateBoard(currentY + 2, currentX, board, currentY, currentX, xOfOrigin, yOfOrigin, wasUsedInBacktracking, wasPassedBy);

                    }
                    break;
                case 3:
                    if (currentX == board[0].length - 2)
                        continue;
                    if (board[currentY][currentX + 1] == '1' && wasPassedBy[currentY][currentX + 2] == 0) {
                        board[currentY][currentX + 1] = '0';
                        updateBoard(currentY, currentX + 2, board, currentY, currentX, xOfOrigin, yOfOrigin, wasUsedInBacktracking, wasPassedBy);

                    }
                    break;
                case 4:
                    if (currentX == 1)
                        continue;
                    if (board[currentY][currentX - 1] == '1' && wasPassedBy[currentY][currentX - 2] == 0) {
                        board[currentY][currentX - 1] = '0';
                        updateBoard(currentY, currentX - 2, board, currentY, currentX, xOfOrigin, yOfOrigin, wasUsedInBacktracking, wasPassedBy);

                    }
                    break;
            }
        }
        wasUsedInBacktracking[currentY][currentX] = 1;
        if (wasUsedInBacktracking[1][1] == 1) return;
        makeMaze(board, yOfOrigin[currentY][currentX], xOfOrigin[currentY][currentX], xOfOrigin, yOfOrigin, wasUsedInBacktracking, wasPassedBy);
    }
}