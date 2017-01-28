/*
 * PlayConnect4WithComputer.java
 *
 * Version: 1: PlayConnect4.java,v 1 9/19/2015 16:34:23
 *
 * Revisions: 1.0 initial version
 *
 */

import java.util.Scanner;


public class PlayConnect4WithComputer {
    /**
     * This class is used to play the connect-4 game with computer as second
     * player. It initializes the game object and player.
     *
     * @author      Pratik kulkarni
     */

    /**
     * The main program. Here we take user input as player's name nad game-piece.
     * Then initialize the game with one player and one computer-player object.
     * @param args    Command line arguments(ignored)
     */
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        String name, gamePiece;
        Connect4Field theGame = new Connect4Field();
        // Computer player.
        Computer computer = new Computer(theGame, "Computer", '+');

        // taking players name and game-piece as input.
        System.out.println("What's the name of the player: ");
        name = inputScanner.next();
        if (name.isEmpty()) {
            System.out.println("Illegal name");
            System.exit(1);
        }
        System.out.println("What's the Game piece of the player " +
                          "(Computer uses +, Select any other character you like): ");
        gamePiece = inputScanner.next();
        if (gamePiece.length() != 1) {
            System.out.println("Illegal Game piece");
            System.exit(1);
        }
        Player player = new Player(theGame, name, gamePiece.charAt(0));
        theGame.init(player, computer); // initializing the game.
        computer.replicateTheGame(player, computer); // replicating starting state of the game.
                                    // This is used by computer to decide moves.
        theGame.playTheGame(); // The game begins.
    }
}
