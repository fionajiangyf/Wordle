package org.cis1200.wordle;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class WordleGameTest {

    @Test
    public void testIsValidInput() {
        Wordle wd = new Wordle();
        assertTrue(wd.isValidWord("better"));
    }

    @Test
    public void testIsNonValidInput() {
        Wordle wd = new Wordle();
        assertFalse(wd.isValidWord("abcdef"));
    }

    @Test
    public void testAddTile() {
        Wordle wd = new Wordle();
        wd.addTile('a', 0, 0);
        assertEquals(wd.getBoard()[0][0].getS(), 'a');
        assertNotEquals(wd.getBoard()[0][0].getS(), 'b');
        assertEquals(wd.getBoard()[0][1].getS(), ' ');
    }

    @Test
    public void testAddNumber() {
        Wordle wd = new Wordle();
        assertThrows(IllegalArgumentException.class, () -> {
            wd.addTile('2', 0, 0);
        });
    }

    @Test
    public void testAddSymbol() {
        Wordle wd = new Wordle();
        assertThrows(IllegalArgumentException.class, () -> {
            wd.addTile('!', 0, 0);
        });
    }

    @Test
    public void testWin() {
        Wordle wd = new Wordle();
        wd.reset();
        wd.setAnswer("viable");
        wd.addTile('v', 0, 0);
        wd.addTile('i', 0, 1);
        wd.addTile('a', 0, 2);
        wd.addTile('b', 0, 3);
        wd.addTile('l', 0, 4);
        wd.addTile('e', 0, 5);
        wd.setState(wd.getBoard()[0]);
        assertTrue(wd.checkWin());
    }

    @Test
    public void testLose() {
        Wordle wd = new Wordle();
        wd.reset();
        wd.setAnswer("viable");
        wd.addTile('b', 0, 0);
        wd.addTile('e', 0, 1);
        wd.addTile('t', 0, 2);
        wd.addTile('t', 0, 3);
        wd.addTile('e', 0, 4);
        wd.addTile('r', 0, 5);
        wd.setState(wd.getBoard()[0]);
        assertFalse(wd.checkWin());
    }

    @Test
    public void testSetState() {
        Wordle wd = new Wordle();
        wd.reset();
        wd.setAnswer("butter");
        wd.addTile('b', 0, 0);
        wd.addTile('e', 0, 1);
        wd.addTile('t', 0, 2);
        wd.addTile('t', 0, 3);
        wd.addTile('e', 0, 4);
        wd.addTile('r', 0, 5);
        wd.setState(wd.getBoard()[0]);
        assertEquals(wd.getBoard()[0][0].getState(), 3);
        assertEquals(wd.getBoard()[0][1].getState(), 2);
        assertEquals(wd.getBoard()[0][2].getState(), 3);
        assertEquals(wd.getBoard()[0][3].getState(), 3);
        assertEquals(wd.getBoard()[0][4].getState(), 3);
        assertEquals(wd.getBoard()[0][5].getState(), 3);
        assertEquals(wd.getBoard()[1][0].getState(), 0);
    }

    @Test
    public void testNumGreen() {
        Wordle wd = new Wordle();
        wd.reset();
        wd.setAnswer("butter");
        wd.addTile('b', 0, 0);
        wd.addTile('e', 0, 1);
        wd.addTile('t', 0, 2);
        wd.addTile('t', 0, 3);
        wd.addTile('e', 0, 4);
        wd.addTile('r', 0, 5);
        wd.setState(wd.getBoard()[0]);
        assertEquals(wd.getNumGreen(), 5);
    }

    @Test
    public void testSetStateNotAWord() {
        Wordle wd = new Wordle();
        wd.reset();
        wd.setAnswer("butter");
        wd.addTile('b', 0, 0);
        wd.addTile('e', 0, 1);
        wd.addTile('t', 0, 2);
        wd.addTile('t', 0, 3);
        wd.addTile('e', 0, 4);
        assertThrows(IllegalArgumentException.class, () -> {
            wd.setState(wd.getBoard()[0]);
        });
    }

    @Test
    public void testSetStateEmptyRow() {
        Wordle wd = new Wordle();
        wd.reset();
        wd.setAnswer("butter");
        assertThrows(IllegalArgumentException.class, () -> {
            wd.setState(wd.getBoard()[0]);
        });
    }

    @Test
    public void testReset() {
        Wordle wd = new Wordle();
        wd.reset();
        wd.setAnswer("butter");
        wd.addTile('b', 0, 0);
        wd.addTile('e', 0, 1);
        wd.addTile('t', 0, 2);
        wd.addTile('t', 0, 3);
        wd.addTile('e', 0, 4);
        wd.addTile('r', 0, 5);
        wd.reset();
        assertEquals(wd.getBoard()[0][0].getS(), ' ');
        assertEquals(wd.getBoard()[0][0].getState(), 0);
        assertEquals(wd.getNumTurns(), 1);
        assertFalse(wd.getGameOver());
        assertEquals(wd.getCurrCol(), 0);
        assertEquals(wd.getCurrRow(), 0);
        assertEquals(wd.getNumGreen(), 0);

    }

}
