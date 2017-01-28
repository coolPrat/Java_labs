/*
 * GameToken.java
 *
 * Version: 1: GameToken.java,v 1 11/7/2015 16:34:23
 *
 * Revisions: 1.0 initial version
 *
 */

import java.io.Serializable;

/**
 * The class GameToken represents token passed for connect4 game.
 * This class stores three things:
 *      1: String representation of current state of the game
 *      2: status of the game (on, won, draw)
 *      3: name of the player who s supposed to play next move
 *
 * @author Pratik Kulkarni
 * @author Kapil Dole
 */
public class GameToken implements Serializable {
    private String theGame;
    // String representation of current state of the game
    private String playerName;
    // name of the player who s supposed to play next move
    private String status;
    // status of the game (on, won, draw)

    /**
     * Constructor for GameToken class.
     * @param theGame       String representation of current state of the game
     * @param playerName    name of the player who s supposed to play next move
     * @param status        status of the game (on, won, draw)
     */
    public GameToken(String theGame, String playerName, String status) {
        this.theGame = theGame;
        this.playerName = playerName;
        this.status = status;
    }

    /**
     * This method returns String representation of
     * current state of the game
     * @return    String representation of current
     *            state of the game
     */
    public String getTheGame() {
        return theGame;
    }

    /**
     * This method returns name of the player who's
     * supposed to play next move
     * @return    name of the player who s supposed to
     *            play next move
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * this method will return status of the game
     * @return    status of the game (on, won, draw)
     */
    public String getStatus() {
        return status;
    }
}
