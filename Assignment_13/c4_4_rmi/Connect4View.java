/*
 * Connect4View.java
 *
 * Version: 1: Connect4View.java,v 1 11/7/2015 16:34:23
 *
 * Revisions: 1.0 initial version
 *
 */

import java.util.Scanner;

/**
 * The class Connect$View represents view layer for connect4 game.
 * this is used to print responses to users console nad take
 * input from the player.
 * @author Pratik Kulkarni
 * @author Kapil Dole
 */
public class Connect4View {

    Scanner inputScanner = new Scanner(System.in);
    // Scanner object for taking input

    /**
     * This method will print the string input
     * to the console
     * @param input    String to print
     */
    public void print(String input) {
        System.out.println(input);
    }

    /**
     * This method will accept the input from the user and return
     * @return    Input from the user
     */
    public String takeInput() {
        return inputScanner.next();
    }
}
