package edu.awhaddox;

public class Position {
    public int row;
    public int col;

    public Position() {
        row = 0;
        col = 0;
    }

    public Position(int r, int c) {
        row = r;
        col = c;
    }

    public String toString() {
        return "[ " + row + " ] [ " + col + " ]";
    }
}
