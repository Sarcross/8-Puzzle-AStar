package edu.awhaddox;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Puzzle pu;

        int selection = 0;
        String input;

        while(selection != 3)
        {
            switch(selection)
            {
                case 1:

                    printHeuristicMenu();
                    selection = in.nextInt();

                    if(selection == 1) {
                        pu = new Puzzle();
                        pu.h1();
                    }
                    else if(selection == 2) {
                        pu = new Puzzle();
                        pu.h2();
                    }
                    else if(selection == 3) {
                        pu = new Puzzle();
                        pu.h1();
                        pu.h2();
                    }
                    selection = 0;
                    break;
                case 2:
                    printHeuristicMenu();
                    selection = in.nextInt();
                    printInputPuzzleMenu();
                    in.nextLine();

                    input = in.nextLine();
                    input += ' ' + in.nextLine();
                    input += ' ' + in.nextLine();

                    if(selection == 1) {
                        try
                        {
                            pu = new Puzzle(input);
                            pu.h1();
                        }
                        catch(InvalidPuzzleException ipe)
                        {
                            System.out.println("The puzzle you have entered is not solvable");
                        }
                    }
                    else if(selection == 2) {
                        try
                        {
                            pu = new Puzzle(input);
                            pu.h2();
                        }
                        catch(InvalidPuzzleException ipe)
                        {
                            System.out.println("The puzzle you have entered is not solvable");
                        }
                    }
                    else if(selection == 3) {
                        try
                        {
                            pu = new Puzzle(input);
                            pu.h1();
                            pu.h2();
                        }
                        catch(InvalidPuzzleException ipe)
                        {
                            System.out.println("The puzzle you have entered is not solvable");
                        }
                    }
                    selection = 0;
                    break;
                case 3:
                    break;
                case 4:
                default:
                    printSelectionMenu();
                    selection = in.nextInt();
                    break;
            }
        }
        //testPuzzles();
    }

    public static void printSelectionMenu() {
        System.out.println("1) Generate Random Puzzle");
        System.out.println("2) Enter own puzzle");
        System.out.println("3) Quit");
        System.out.print("> ");
    }

    public static void printHeuristicMenu() {
        System.out.println("1) H1");
        System.out.println("2) H2");
        System.out.println("3) Both");
        System.out.print("> ");
    }

    public static void printInputPuzzleMenu() {
        System.out.println("Input puzzle:");
    }

    public static void testPuzzles() {
        try
        {
            Puzzle pu;
            PrintStream out;

            out = new PrintStream("h1.txt");
            System.setOut(out);
            for(int ndx = 0; ndx < 200; ndx++) {
                pu = new Puzzle();
                pu.h1();
            }

            out = new PrintStream("h2.txt");
            System.setOut(out);
            for(int ndx = 0; ndx < 200; ndx++) {
                pu = new Puzzle();
                pu.h2();
            }

        }
        catch(FileNotFoundException e)
        {

        }
    }
}
