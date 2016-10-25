package edu.awhaddox;

import java.util.*;

public class Puzzle {
    public int[][] board;
    private int[] goal;
    private Position zeroPosition = new Position();

    public Puzzle() {
        generateBoard();

        while (!isSolvable(board)) {
            System.out.println("Unsolvable puzzle generated. Generating new puzzle...");
            generateBoard();
        }
        generateGoal();
    }

    public Puzzle(String input) throws InvalidPuzzleException{
        String[] bd = input.split(" ");
        int counter = 0;
        board = new int[3][3];
        int ndx, mdx;
        for(ndx = 0; ndx < board.length; ndx++) {
            for(mdx = 0; mdx < board.length; mdx++) {
                board[ndx][mdx] = Integer.parseInt(bd[counter]);
                counter++;
            }
        }

        if(!isSolvable(board))
        {
            throw new InvalidPuzzleException();
        }
        else
            generateGoal();
    }

    private void generateGoal() {
        goal = new int[board.length * board.length];
        for (int ndx = 0; ndx < goal.length; ndx++) {
            goal[ndx] = ndx;
        }
    }

    private void generateBoard() {
        Random rand = new Random(System.nanoTime());
        boolean[] placement = new boolean[9];
        int selection;

        board = new int[3][3];

        int ndx;
        int mdx;
        for (ndx = 0; ndx < board.length; ndx++) {
            for (mdx = 0; mdx < board.length; mdx++) {
                selection = rand.nextInt(9);

                if (!placement[selection]) {
                    placement[selection] = true;
                    board[ndx][mdx] = selection;
                    if (selection == 0)
                        zeroPosition = new Position(ndx, mdx);
                }
                else {
                    while (placement[selection]) {
                        selection = rand.nextInt(9);

                        if (!placement[selection]) {
                            placement[selection] = true;
                            board[ndx][mdx] = selection;

                            if (selection == 0)
                                zeroPosition = new Position(ndx, mdx);
                            break;
                        }
                    }

                }
            }
        }
    }

    private boolean isSolvable(int[][] target) {
        int[] boardList = boardToArray(target);
        int inversions = 0;
        int ndx;
        int mdx;
        for (ndx = 0; ndx < boardList.length; ndx++) {
            for (mdx = ndx + 1; mdx < boardList.length; mdx++) {
                if (boardList[ndx] > boardList[mdx] && (boardList[ndx] != 0 && boardList[mdx] != 0))
                    inversions++;
            }
        }

        return (inversions % 2 == 0);
    }

    private int[] boardToArray(int[][] target) {
        int[] boardList = new int[target.length * target.length];
        int position = 0;
        int ndx;
        int mdx;
        for (ndx = 0; ndx < target.length; ndx++) {
            for (mdx = 0; mdx < target.length; mdx++) {
                boardList[position] = target[ndx][mdx];
                position++;
            }
        }

        return boardList;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        int ndx;
        int mdx;
        for (ndx = 0; ndx < board.length; ndx++) {
            for (mdx = 0; mdx < board.length; mdx++) {
                builder.append("[ " + board[ndx][mdx] + " ] ");
            }
            builder.append('\n');
        }

        return builder.toString();
    }


    public void h1() {
        if (checkGoal(board)) {
            System.out.println("This puzzle is already solved.");
            System.out.println(this);
            return;
        }

        PriorityQueue<Node> frontier = new PriorityQueue<>();
        HashMap<String, int[]> explored = new HashMap<>();
        Node current = new Node(board, 0, getMisplaced(board));
        Node transition;
        int[][] tempBoard;
        int counter = 0;

        frontier.add(current);

        while (current.hn != 0) {
            current = frontier.poll();
            if (explored.containsKey(arrayToString(boardToArray(current.board)))) {

            }
            else {
                //TryMoveUp

                try {
                    tempBoard = moveUp(current.board);
                    if (!explored.containsKey(arrayToString(boardToArray(tempBoard)))) {
                        transition = new Node(tempBoard, current.gn + 1, getMisplaced(tempBoard));
                        transition.parent = current;

                        frontier.add(transition);
                        counter++;
                    }

                } catch (InvalidMoveException ime) {

                }


                //TryMoveDown

                try {
                    tempBoard = moveDown(current.board);
                    if (!explored.containsKey(arrayToString(boardToArray(tempBoard)))) {
                        transition = new Node(tempBoard, current.gn + 1, getMisplaced(tempBoard));
                        transition.parent = current;

                        frontier.add(transition);
                        counter++;
                    }
                } catch (InvalidMoveException ime) {

                }

                //TryMoveLeft

                try {
                    tempBoard = moveLeft(current.board);
                    if (!explored.containsKey(arrayToString(boardToArray(tempBoard)))) {
                        transition = new Node(tempBoard, current.gn + 1, getMisplaced(tempBoard));
                        transition.parent = current;

                        frontier.add(transition);
                        counter++;
                    }
                } catch (InvalidMoveException ime) {

                }

                //TryMoveRight

                try {
                    tempBoard = moveRight(current.board);
                    if (!explored.containsKey(arrayToString(boardToArray(tempBoard)))) {
                        transition = new Node(tempBoard, current.gn + 1, getMisplaced(tempBoard));
                        transition.parent = current;

                        frontier.add(transition);
                        counter++;
                    }
                } catch (InvalidMoveException ime) {

                }

                explored.put(arrayToString(boardToArray(current.board)), boardToArray(current.board));
            }


        }

        Stack<Node> history = new Stack<>();
        {
            Node position = current;
            while (position != null) {
                history.add(position);
                position = position.parent;
            }
        }

        while (!history.isEmpty())
            System.out.println(history.pop());

        System.out.println("Nodes generated: " + counter);

    }

    public int getMisplaced(int[][] target) {
        int[] boardList = boardToArray(target);
        int counter = 0;
        for (int ndx = 1; ndx < boardList.length; ndx++) {
            if (boardList[ndx] != goal[ndx])
                counter++;
        }
        return counter;
    }

    public void h2() {
        if (checkGoal(board)) {
            System.out.println("This puzzle is already solved.");
            System.out.println(this);
            return;
        }

        PriorityQueue<Node> frontier = new PriorityQueue<>();
        HashMap<String, int[]> explored = new HashMap<>();
        Node current = new Node(board, 0, getManhattanCount(board));
        Node transition;
        int[][] tempBoard;
        int counter = 0;

        frontier.add(current);

        while (current.hn != 0) {
            current = frontier.poll();
            if (explored.containsKey(arrayToString(boardToArray(current.board)))) {

            }
            else {
                //TryMoveUp

                try {
                    tempBoard = moveUp(current.board);
                    if (!explored.containsKey(arrayToString(boardToArray(tempBoard)))) {
                        transition = new Node(tempBoard, current.gn + 1, getManhattanCount(tempBoard));
                        transition.parent = current;

                        frontier.add(transition);
                        counter++;
                    }

                } catch (InvalidMoveException ime) {

                }


                //TryMoveDown

                try {
                    tempBoard = moveDown(current.board);
                    if (!explored.containsKey(arrayToString(boardToArray(tempBoard)))) {
                        transition = new Node(tempBoard, current.gn + 1, getManhattanCount(tempBoard));
                        transition.parent = current;

                        frontier.add(transition);
                        counter++;
                    }
                } catch (InvalidMoveException ime) {

                }

                //TryMoveLeft

                try {
                    tempBoard = moveLeft(current.board);
                    if (!explored.containsKey(arrayToString(boardToArray(tempBoard)))) {
                        transition = new Node(tempBoard, current.gn + 1, getManhattanCount(tempBoard));
                        transition.parent = current;

                        frontier.add(transition);
                        counter++;
                    }
                } catch (InvalidMoveException ime) {

                }

                //TryMoveRight

                try {
                    tempBoard = moveRight(current.board);
                    if (!explored.containsKey(arrayToString(boardToArray(tempBoard)))) {
                        transition = new Node(tempBoard, current.gn + 1, getManhattanCount(tempBoard));
                        transition.parent = current;

                        frontier.add(transition);
                        counter++;
                    }
                } catch (InvalidMoveException ime) {

                }

                explored.put(arrayToString(boardToArray(current.board)), boardToArray(current.board));
            }


        }

        Stack<Node> history = new Stack<>();
        {
            Node position = current;
            while (position != null) {
                history.add(position);
                position = position.parent;
            }
        }

        while (!history.isEmpty())
            System.out.println(history.pop());

        System.out.println("Nodes generated: " + counter);
    }

    public int getManhattanCount(int[][] board) {
        int value = 0;
        int mdx;
        int counter = 0;
        HashMap<Integer, Position> positions = new HashMap<>();
        HashMap<Integer, Position> goal = new HashMap<>();
        for (int ndx = 0; ndx < board.length; ndx++) {
            for (mdx = 0; mdx < board.length; mdx++) {
                positions.put(board[ndx][mdx], new Position(ndx, mdx));
                goal.put(counter, new Position(ndx, mdx));
                counter++;


            }
        }

        for (int ndx = 1; ndx < positions.size(); ndx++) {
            value += Math.abs(positions.get(ndx).row - goal.get(ndx).row) + Math.abs(positions.get(ndx).col - goal.get(ndx).col);
        }

        return value;
    }

    private boolean checkGoal(int[][] target) {
        int[] boardList = boardToArray(target);
        for (int ndx = 0; ndx < boardList.length; ndx++)
            if (goal[ndx] != boardList[ndx])
                return false;
        return true;
    }

    public int[][] moveUp(int[][] target) throws InvalidMoveException {
        Position zero = findZeroPosition(target);
        if (zero.row == 0)
            throw new InvalidMoveException();

        int[][] ret = new int[target.length][target.length];
        for (int ndx = 0; ndx < target.length; ndx++)
            for (int mdx = 0; mdx < target.length; mdx++)
                ret[ndx][mdx] = target[ndx][mdx];
        int temp = ret[zero.row - 1][zero.col];
        ret[zero.row - 1][zero.col] = ret[zero.row][zero.col];
        ret[zero.row][zero.col] = temp;

        return ret;
    }

    public int[][] moveDown(int[][] target) throws InvalidMoveException {
        Position zero = findZeroPosition(target);
        if (zero.row == target.length - 1)
            throw new InvalidMoveException();
        int[][] ret = new int[target.length][target.length];
        for (int ndx = 0; ndx < target.length; ndx++)
            for (int mdx = 0; mdx < target.length; mdx++)
                ret[ndx][mdx] = target[ndx][mdx];
        int temp = ret[zero.row + 1][zero.col];
        ret[zero.row + 1][zero.col] = ret[zero.row][zero.col];
        ret[zero.row][zero.col] = temp;

        return ret;
    }

    public int[][] moveLeft(int[][] target) throws InvalidMoveException {
        Position zero = findZeroPosition(target);
        if (zero.col == 0)
            throw new InvalidMoveException();
        int[][] ret = new int[target.length][target.length];
        for (int ndx = 0; ndx < target.length; ndx++)
            for (int mdx = 0; mdx < target.length; mdx++)
                ret[ndx][mdx] = target[ndx][mdx];
        int temp = ret[zero.row][zero.col - 1];
        ret[zero.row][zero.col - 1] = ret[zero.row][zero.col];
        ret[zero.row][zero.col] = temp;

        return ret;
    }

    public int[][] moveRight(int[][] target) throws InvalidMoveException {
        Position zero = findZeroPosition(target);
        if (zero.col == target.length - 1)
            throw new InvalidMoveException();
        int[][] ret = new int[target.length][target.length];
        for (int ndx = 0; ndx < target.length; ndx++)
            for (int mdx = 0; mdx < target.length; mdx++)
                ret[ndx][mdx] = target[ndx][mdx];
        int temp = ret[zero.row][zero.col + 1];
        ret[zero.row][zero.col + 1] = ret[zero.row][zero.col];
        ret[zero.row][zero.col] = temp;

        return ret;
    }

    private Position findZeroPosition(int[][] target) {
        int ndx, mdx;
        for (ndx = 0; ndx < target.length; ndx++) {
            for (mdx = 0; mdx < target.length; mdx++)
                if (target[ndx][mdx] == 0)
                    return new Position(ndx, mdx);
        }
        return null;
    }

    private String arrayToString(int[] array) {
        String string = "";
        for (int ndx = 0; ndx < array.length; ndx++)
            string += array[ndx];
        return string;
    }
}
