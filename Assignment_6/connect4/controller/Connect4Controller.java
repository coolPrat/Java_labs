/*
 * Connect4Controller.java
 *
 * Version: 1: Connect4Controller.java,v 1 10/4/2015 16:34:23
 *
 * Revisions: 1.0 initial version
 *
 */
package connect4.controller;

import connect4.model.*;
import connect4.view.PlayConnect4;
import java.util.Vector;

public class Connect4Controller {
    /**
     * This is the controller class for Connect4. It will take input from user
     * and will pass it to model. As the state of model changes it will respond
     * according to the change in state.
     */
    private Vector<PlayerInterface> players = new Vector<>();
    // Players playing the game
    private Connect4FieldInterface theGame = new Connect4Field();
    // The model object
    private PlayConnect4 view = new PlayConnect4();
    // The view object
    private boolean gameIsOn = true;
    // boolean flag to check if game is still running


    /**
     * This will initialize a player object depending upon the type of the
     * player.
     * @param playerType    String type of player (computer or player)
     * @param playerName    Name of the the player
     * @param gamePiece     Game-piece character of the player
     */

    public void initPlayer(String playerType, String playerName, char gamePiece) {
        if (playerType.equals("Computer")) {
            Computer computer = new Computer(this.theGame, playerName, gamePiece);
            players.add(computer);
        } else {
            Player player = new Player(theGame, playerName, gamePiece);
            players.add(player);
        }
    }

    /**
     * This method will initialize the game (model object) so game can be played
     */
    public void initGame() {
        theGame.init(players.get(0), players.get(1));
    }

    /**
     * This method will continuously interact with view and model.
     * This method will take the input from the user and pass it to model.
     */
    public void playGame() {
        int column, counter = 0, index;
        while (gameIsOn) {
            view.print(theGame.toString());
            index = counter++ % 2;
            column = players.get(index).nextMove();
            if (theGame.checkIfPiecedCanBeDroppedIn(column)) {
                theGame.dropPieces(column, players.get(index).getGamePiece());
                if (theGame.didLastMoveWin()) {
                    gameIsOn = false;
                    view.print(theGame.toString());
                    view.print(players.get(index).getName() + " won!");
                } else if (theGame.isItaDraw()) {
                    gameIsOn = false;
                    view.print(theGame.toString());
                    view.print("Draw!!");
                }
            } else {
                view.print("Can not add... Sorry");
            }
        }
    }
}
