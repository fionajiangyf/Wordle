package org.cis1200.wordle;

import java.util.*;
import java.io.*;
// import java.io.FileNotFoundException;

/**
 * CIS 120 HW09 - Wordle
 * (c) University of Pennsylvania
 * Created by Fiona Jiang in Fall 2022
 */

/**
 * This class is a model for TicTacToe.
 * 
 * This game adheres to a Model-View-Controller design framework.
 * This framework is very effective for turn-based games. We
 * STRONGLY recommend you review these lecture slides, starting at
 * slide 8, for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec36.pdf
 * 
 * This model is completely independent of the view and controller.
 * This is in keeping with the concept of modularity! We can play
 * the whole game from start to finish without ever drawing anything
 * on a screen or instantiating a Java Swing object.
 * 
 * Run this file to see the main method play a game of TicTacToe,
 * visualized with Strings printed to the console.
 */

public class Wordle {

    private Tile[][] board;
    private int numTurns;
    private boolean gameOver;
    private String answer;
    private String input = "";
    private TreeSet<String> wordBank;
    private static int numGreen;

    private Tile t;
    private int currRow = 0;
    private int currCol = 0;
    private LinkedList<String> guess = new LinkedList<>();
    private boolean isValidWord;

    /**
     * Constructor sets up game state.
     */

    public Wordle() {
        reset();
    }

    /**
     * playTurn allows players to play a turn. Returns true if the move is
     * successful and false if a player tries to play in a location that is
     * taken or after the game has ended. If the turn is successful and the game
     * has not ended, the player is changed. If the turn is unsuccessful or the
     * game has ended, the player is not changed.
     *
     * @param s the input string
     * @return whether the turn was successful
     */
    public boolean playTurn(String s) {
        return (s.length() == 6);
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String s) {
        this.answer = s;
    }

    public int getNumTurns() {
        return this.numTurns;
    }

    public int getCurrRow() {
        return this.currRow;
    }

    public int getCurrCol() {
        return this.currCol;
    }

    public void setCurrRow(int r) {
        this.currRow = r;
    }

    public void setCurrCol(int c) {
        this.currCol = c;
    }

    public Tile[][] getBoard() {
        return this.board;
    }

    public boolean getGameOver() {
        return this.gameOver;
    }

    public static int getNumGreen() {
        return numGreen;
    }

    public static void setNumGreen(int g) {
        numGreen = g;
    }

    public void clearInput() {
        this.input = "";
    }

    /**
     * getCell is a getter for the contents of the cell specified by the method
     * arguments.
     *
     * @param c column to retrieve
     * @param r row to retrieve
     * @return an integer denoting the contents of the corresponding cell on the
     *         game board.
     */
    public Tile getCell(int c, int r) {
        return board[r][c];
    }

    public TreeSet<String> createWordBank() {
        TreeSet<String> wb = new TreeSet<>();
        try {
            FileReader words = new FileReader("files/word.txt");
            BufferedReader br = new BufferedReader(words);
            String line = br.readLine();
            String[] word = line.split(" ");
            for (String w : word) {
                wb.add(w);
            }

        } catch (IOException e) {
            System.out.println("File Doesn't Exist");
            // throw new IllegalArgumentException("File not found");
        }
        return wb;
    }

    public String pickWord() {
        String[] wordList = wordBank.toArray(new String[wordBank.size()]);
        String word = wordList[(int) (Math.random() * (wordBank.size() - 1))];
        this.answer = word;
        System.out.println("The answer is: " + word);
        return word;
    }

    public boolean isValidWord(String s) {
        return wordBank.contains(s);
    }

    public void addTile(char c, int row, int col) {
        if (Character.isLetter(c) || c == ' ') {
            if (row > 6 || row < 0 || col > 6 || col < 0) {
                throw new IllegalArgumentException("Cannot Type this");
            } else {
                Tile newT = new Tile();
                newT.setS(c);
                newT.setCurrRow(row);
                newT.setCurrCol(col);
                if (c != ' ') {
                    this.currCol = currCol + 1;
                } else {
                    this.currCol = currCol - 1;
                }
                input += c;
                board[row][col] = newT;
            }
        } else {
            throw new IllegalArgumentException("Cannot Type this");
        }

    }

    public void setState(Tile[] tRow) {
        for (int i = 0; i < tRow.length; i++) {
            // input += tRow[i];
            if (tRow[i].getS() == ' ') {
                throw new IllegalArgumentException();
            }
            if (answer.contains("" + tRow[i].getS())) {
                if (answer.charAt(i) == tRow[i].getS()) {
                    tRow[i].setState(3);
                    numGreen += 1;
                } else {
                    tRow[i].setState(2);
                }
            } else {
                tRow[i].setState(1);
            }
        }
        numTurns += 1;
        guess.add(input);
    }

    /**
     * checkWinner checks whether the game has reached a win condition.
     * which means that the input is exactly the same as the answer
     *
     * @return whether the string is equal or not.
     */
    public boolean checkWin() {
        // System.out.println(numGreen);

        if (numGreen == 6) {
            // System.out.println("here");
            this.gameOver = true;
            return true;
        } else if (numTurns == 8 && currCol == 0) {
            this.gameOver = true;
        }
        return false;
    }

    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {
        System.out.println("\n\nTurn " + numTurns + ":\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].equals(' ')) {
                    System.out.println("_ ");
                } else {
                    System.out.println(board[i][j] + " ");
                }
            }
        }
    }

    public void save() {
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter("files/gamestate.txt"));
            bw.write(this.answer);
            bw.newLine();
            bw.write(String.valueOf(gameOver));
            bw.newLine();
            bw.write(input);
            bw.newLine();
            bw.write(String.valueOf(numTurns));
            bw.newLine();
            bw.write(String.valueOf(currRow));
            bw.newLine();
            bw.write(String.valueOf(currCol));
            bw.newLine();
            for (int i = 0; i < this.board.length; i++) {
                // bw.write(numTurns);
                int right = 0;
                for (int j = 0; j < this.board[i].length; j++) {
                    bw.write(board[i][j].getS());
                    bw.write(',');
                    bw.write(String.valueOf(board[i][j].getState()));
                    bw.write('/');
                    if (board[i][j].getState() == 3) {
                        right++;
                    }
                }
                bw.write(String.valueOf(right));
                bw.newLine();
            }

            // for (String guess: this.guess){
            // bw.write(guess + " ");
            // }
            bw.close();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public void restore() {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("files/gamestate.txt"));
            this.answer = br.readLine();
            this.gameOver = Boolean.parseBoolean(br.readLine());
            this.input = br.readLine();
            this.numTurns = Integer.parseInt(br.readLine());
            this.currRow = Integer.parseInt(br.readLine());
            this.currCol = Integer.parseInt(br.readLine());
            Tile[][] tempBoard = new Tile[8][6];
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 6; col++) {
                    tempBoard[row][col] = new Tile();
                }
            }
            for (int i = 0; i < 8; i++) {
                String line = br.readLine();
                // System.out.println(line);
                String[] tiles = line.split("/");
                int counter = 0;
                for (String block : tiles) {
                    if (counter == 6) {
                        this.numGreen = Integer.parseInt(tiles[6]);
                        // System.out.println(numGreen);
                    } else {
                        String[] letters = block.split(",");
                        if (letters[0] != null && !letters[0].equals(" ")) {
                            tempBoard[i][counter].setS(letters[0].charAt(0));
                        }
                        tempBoard[i][counter].setState(Integer.parseInt(letters[1]));
                        counter++;
                    }
                }
            }
            this.board = tempBoard;
            if (currCol == 6) {
                this.t = tempBoard[currRow][currCol - 1];
            } else if (currRow == 7 && currCol == 0) {
                this.t = tempBoard[7][5];
            } else {
                this.t = tempBoard[currRow][currCol];
            }
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        board = new Tile[8][6];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 6; col++) {
                board[row][col] = new Tile();
            }
        }
        wordBank = createWordBank();
        numTurns = 1;
        gameOver = false;
        // answer = "viable";
        currCol = 0;
        currRow = 0;
        t = board[0][0];
        answer = pickWord();
        numGreen = 0;
        clearInput();
        isValidWord = false;
    }

    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     *
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     *
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {
        Wordle w = new Wordle();
        w.playTurn("viable");
        w.printGameState();

        System.out.println();
        System.out.println();
        System.out.println("Is this word correct: " + w.checkWin());
    }
}
