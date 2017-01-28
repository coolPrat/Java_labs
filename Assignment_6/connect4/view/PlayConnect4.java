/*
 * PlayConnect4.java
 *
 * Version: 1: PlayConnect4.java,v 1 10/4/2015 16:34:23
 *
 * Revisions: 1.0 initial version
 *
 */

package connect4.view;

import connect4.controller.Connect4Controller;
import java.util.Scanner;

public class PlayConnect4 {
    /**
     * This class is used to play the connect-4 game. It initializes the game
     * object and players.
     *
     * @author      Pratik kulkarni
     * @author      Kapil dole
     */

    /**
     * The main method.
     * It will ask players to enter their names and game-piece character.
     * And initialize the game object.
     * @param args    (ignored)
     */
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        Connect4Controller controller = new Connect4Controller();
        String name, gamePiece;
        System.out.println("Welcome to Connect-4.");
        System.out.println("Press 1 if you want to play with computer." +
                "\n Press 2 if you would like to play against someone.");
        int numberOfPlayers = Integer.parseInt(inputScanner.next());
        if (numberOfPlayers < 0 && numberOfPlayers > 2) {
            System.out.println("Invalid input... Switching off the game.");
            System.exit(0);
        }
        for(int i = 0; i < numberOfPlayers; i++) {
            System.out.println("What's the name of Player " + (i+1) + ": ");
            name = inputScanner.next();
            if (name.isEmpty()) {
                System.out.println("Illegal name");
                System.exit(1);
            }
            System.out.println("What's the Game piece of Player " + (i+1) + ": ");
            gamePiece = inputScanner.next();
            if (gamePiece.length() != 1) {
                System.out.println("Illegal Game piece");
                System.exit(1);
            }
            controller.initPlayer("Player", name, gamePiece.charAt(0));
        }
        if (numberOfPlayers == 1) {
            controller.initPlayer("Computer", "Computer", '+');
        }
        controller.initGame();
        controller.playGame();
    }

    /**
     * This method will print the response received from controller.
     * @param string    Response from the controller
     */
    public void print(String string) {
        System.out.println(string);
    }
}
