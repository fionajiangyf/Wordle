package org.cis1200.wordle;

/**
 * CIS 120 HW09 - Wordle
 * (c) University of Pennsylvania
 * Created by Fiona Jiang in Fall 2022
 */

import javax.swing.*;
import java.awt.*;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 * 
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, Game initializes the view,
 * implements a bit of controller functionality through the reset
 * button, and then instantiates a GameBoard. The GameBoard will
 * handle the rest of the game's view and controller functionality, and
 * it will instantiate a TicTacToe object to serve as the game's model.
 */
public class RunWordle implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Wordle");
        frame.setLocation(300, 300);

        final JFrame frameIns = new JFrame("Instruction");
        frame.setLocation(300, 300);
        JOptionPane.showMessageDialog(
                frameIns,
                "Instruction for Wordle: \nWordle is a web-based " +
                        "word game created and " +
                        "developed "
                        +
                        "by Welsh software engineer Josh Wardle, \nand " +
                        "owned and published by The " +
                        "New " +
                        "York Times"
                        +
                        " Company since 2022.\nHow to play? \nPlayers " +
                        "have seven chances to guess a " +
                        "six-letter"
                        +
                        "word with feedback given for each guess in \nthe " +
                        "form of colored tiles " +
                        "indicating when "
                        +
                        "letters match or occupy the correct position." +
                        " \nYou cannot enter an invalid word."
                        +
                        " \nColor of Tiles\nGreen: " +
                        "in the word and"
                        +
                        " in the correct spot. \nYellow: in the word " +
                        "but not in the correct spot. "
                        +
                        "\nGrey: not in the word in any spot."
        );

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        // Game board
        final GameBoard board = new GameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());
        control_panel.add(reset);

        /**
         * 
         * final JButton instruction = new JButton("Instruction");
         * instruction
         * .addActionListener(
         * e -> JOptionPane.showMessageDialog(
         * frameIns,
         * "Instruction for Wordle: \nWordle is a web-based " +
         * "word game created and " +
         * "developed "
         * +
         * "by Welsh software engineer Josh Wardle, \nand " +
         * "owned and published by The " +
         * "New " +
         * "York Times"
         * +
         * " Company since 2022.\nHow to play? \nPlayers " +
         * "have seven chances to guess a " +
         * "six-letter"
         * +
         * "word with feedback given for each guess in \nthe " +
         * "form of colored tiles " +
         * "indicating when "
         * +
         * "letters match or occupy the correct position." +
         * " \nColor of Tiles\nGreen: " +
         * "in the word and"
         * +
         * " in the correct spot. \nYellow: in the word " +
         * "but not in the correct spot. "
         * +
         * "\nGrey: not in the word in any spot."
         * )
         * );
         * control_panel.add(instruction);
         * 
         */

        final JButton save = new JButton("Save");
        save.addActionListener(e -> board.save());
        control_panel.add(save);

        final JButton restore = new JButton("Restore");
        restore.addActionListener(e -> board.restore());
        control_panel.add(restore);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
    }
}