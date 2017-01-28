/*
 * Player.java
 *
 * Version: 1: Player.java,v 2 10/4/2015 16:34:23
 *
 * Revisions: 1.0 initial version
 *            2.0 Refactored in MVC
 *
 */

import java.io.Serializable;

public class Player implements PlayerInterface, Serializable {
    /**
     * The class Player stores the information about the players. It stores
     * an object WithPlayer.Connect4FieldInterface which the game player is playing.
     * It also stores the name and game-piece character of the player.
     *
     * @author      Pratik kulkarni
     * @author      Kapil dole
     */

    //private Connect4FieldInterface connect4Field; // the game object
    private String name; // player's name
    private char gamePiece; // playe's game-piece
    private String move; // player's move
    //private Scanner inputScanner = new Scanner(System.in);
    // Scanner object to get the input.

    /**
     * Constructor of the WithPlayer.Player class.
     *
     * @param connect4Field    The game object
     * @param name             Name of the player
     * @param gamePiece        Game-piece of the player.
     */
    public Player(String name,
                  char gamePiece) {
        //this.connect4Field = connect4Field;
        this.name = name;
        this.gamePiece = gamePiece;
    }

    /**
     * returns game-piece of the player
     * @return    The game-piece character of the player
     */
    @Override
    public char getGamePiece() {
        return this.gamePiece;
    }

    /**
     * Returns the name of the player
     *
     * @return    The name of the player
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * This method asks player the column number where he wishes to make the
     * next move, then returns the column number.
     *
     * @return    the column number as specified by the use. In-case its invalid
     *            it will return -1.
     */
    @Override
    public int nextMove() {
        System.out.println(this.name + "\'s turn \n Enter column number:");
        //move = inputScanner.next();
        //if (move.length() != 1 && !move.matches("\\d+")) {
         //   return -1;
        //}
        //return Integer.parseInt(move);
        return 1;
    }

    public boolean equals(Object newPlayer) {
        if (newPlayer instanceof Player) {
            return this.name.equals(((Player)newPlayer).getName());
        }
        return false;
    }
}
