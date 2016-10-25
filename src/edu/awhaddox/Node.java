package edu.awhaddox;

public class Node implements Comparable<Node>{
    int[][] board;
    public int gn;
    public int hn;

    Node parent;

    public Node(int[][] b, int g, int h) {
        board = new int[b.length][b.length];
        int mdx;
        for(int ndx = 0; ndx < b.length; ndx++) {
            for(mdx = 0; mdx < b.length; mdx++)
                board[ndx][mdx] = b[ndx][mdx];
        }

        gn = g;
        hn = h;
        parent = null;
    }

    public int compareTo(Node n) {
        if(hn+gn < n.hn + n.gn)
            return -1;
        if(hn+gn > n.hn + n.gn)
            return 1;
        return 0;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        int ndx;
        int mdx;
        for(ndx = 0; ndx < board.length; ndx++) {
            for(mdx = 0 ; mdx < board.length; mdx++) {
                builder.append("[ " + board[ndx][mdx] + " ] ");
            }
            builder.append('\n');
        }

        builder.append("G(n): " + gn);
        builder.append(" | H(n): " + hn + '\n');


        return builder.toString();
    }

}
