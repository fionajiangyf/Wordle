package org.cis1200.wordle;

// import java.io.BufferedWriter;
// import java.io.FileNotFoundException;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.util.LinkedList;

/**
 * CIS 120 HW09 - Wordle
 * (c) University of Pennsylvania
 * Created by Fiona Jiang in Fall 2022
 */

import javax.swing.*;
import java.awt.*;
// import java.awt.event.MouseAdapter;
// import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * This class instantiates a Wordle object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 * 
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private Wordle wordle; // model for the game
    private JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 600;
    public static final int BOARD_HEIGHT = 700;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        wordle = new Wordle(); // initializes model for the game
        status = statusInit; // initializes the status JLabel
        updateStatus();

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                requestFocusInWindow();
                if (!wordle.getGameOver()) {
                    int code = e.getKeyCode();
                    int col = wordle.getCurrCol();
                    int row = wordle.getCurrRow();
                    char c = e.getKeyChar();
                    Tile[] thisRow = wordle.getBoard()[row];
                    String input = "";
                    for (Tile t : thisRow) {
                        input += t.getS();
                    }
                    // System.out.println(input);
                    if (code >= 65 && code <= 90 && wordle.getCurrCol() <= 5) {
                        wordle.addTile(c, row, col);
                        updateStatus();
                        repaint();
                    }
                    if (code == KeyEvent.VK_ENTER && wordle.getCurrCol() == 6
                            && wordle.isValidWord(input)) {
                        wordle.setCurrRow(row + 1);
                        wordle.setCurrCol(0);
                        wordle.setState(wordle.getBoard()[row]);
                        repaint();
                        updateStatus();
                        wordle.clearInput();
                    }
                    if (code == KeyEvent.VK_BACK_SPACE && wordle.getCurrCol() > 0) {
                        if (col == 0) {
                            wordle.addTile(' ', row, col);
                        } else {
                            wordle.addTile(' ', row, col - 1);
                        }

                        repaint();
                    }

                    repaint();
                }
            }
        });

    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        wordle.reset();
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void save() {
        wordle.save();
        requestFocusInWindow();
    }

    public void restore() {
        wordle.restore();
        repaint();
        requestFocusInWindow();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        int attempt = wordle.getNumTurns();
        boolean win = wordle.checkWin();
        if (win) {
            status.setText("YOU GOT THE WORD!!!");
        } else if (!win && attempt < 8) {
            Wordle.setNumGreen(0);
            status.setText("Attempt: " + attempt);
        } else {
            // String ans = wordle.pickWord();
            status.setText("TOO BAD...the answer is " + wordle.getAnswer());
        }
    }

    /**
     * Draws the game board.
     * 
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws board grid
        g.drawLine(100, 0, 100, 700);
        g.drawLine(200, 0, 200, 700);
        g.drawLine(300, 0, 300, 700);
        g.drawLine(400, 0, 400, 700);
        g.drawLine(500, 0, 500, 700);
        g.drawLine(600, 0, 600, 700);
        g.drawLine(0, 100, 700, 100);
        g.drawLine(0, 200, 700, 200);
        g.drawLine(0, 300, 700, 300);
        g.drawLine(0, 400, 700, 400);
        g.drawLine(0, 500, 700, 500);
        g.drawLine(0, 600, 700, 600);

        // Print Word
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                Tile c = wordle.getCell(j, i);
                int state = c.getState();
                if (state == 1) {
                    g.setColor(new Color(120, 124, 127));
                    g.fillRect(j * 100, i * 100, 100, 100);
                    g.setColor(Color.black);
                    g.setFont(new Font("HelveticaNeue", Font.PLAIN, 40));
                    g.drawString(
                            String.valueOf(Character.toUpperCase(wordle.getCell(j, i).getS())),
                            j * 100 + 35, i * 100 + 60
                    );
                } else if (state == 3) {
                    g.setColor(new Color(108, 169, 101));
                    g.fillRect(j * 100, i * 100, 100, 100);
                    g.setColor(Color.black);
                    g.setFont(new Font("HelveticaNeue", Font.PLAIN, 40));
                    g.drawString(
                            String.valueOf(Character.toUpperCase(wordle.getCell(j, i).getS())),
                            j * 100 + 35, i * 100 + 60
                    );
                } else if (state == 2) {
                    g.setColor(new Color(200, 182, 83));
                    g.fillRect(j * 100, i * 100, 100, 100);
                    g.setColor(Color.black);
                    g.setFont(new Font("HelveticaNeue", Font.PLAIN, 40));
                    g.drawString(
                            String.valueOf(Character.toUpperCase(wordle.getCell(j, i).getS())),
                            j * 100 + 35, i * 100 + 60
                    );
                } else {
                    g.setColor(Color.black);
                    g.setFont(new Font("HelveticaNeue", Font.PLAIN, 40));
                    g.drawString(
                            String.valueOf(Character.toUpperCase(wordle.getCell(j, i).getS())),
                            j * 100 + 35, i * 100 + 60
                    );
                }
            }
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
