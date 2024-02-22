package org.cis1200.wordle;

public class Tile {
    char s;
    int state;
    int currRow;
    int currCol;

    public Tile() {
        this.s = ' ';
        this.state = 0;
        this.currRow = 0;
        this.currCol = 0;
    }

    public char getS() {
        return s;
    }

    public int getState() {
        return state;
    }

    public void setS(char s) {
        this.s = s;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCurrCol() {
        return currCol;
    }

    public void setCurrCol(int currCol) {
        this.currCol = currCol;
    }

    public int getCurrRow() {
        return currRow;
    }

    public void setCurrRow(int currRow) {
        this.currRow = currRow;
    }
}
