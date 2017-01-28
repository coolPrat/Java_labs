/*
 * Connect4Controller.java
 *
 * Version: 2: Connect4Controller.java,v 2 11/6/2015 16:34:23
 *
 * Revisions: 1.0 initial version
 *            2.0 Refactored in multi-threaded game
 *
 */

import java.util.Scanner;
import java.util.Vector;

public class Connect4Controller extends Thread {
    /**
     * This is the controller class for Connect4. It will take input from user
     * and will pass it to model. As the state of model changes it will respond
     * according to the change in state.
     */
    private Vector<PlayerInterface> players = new Vector<>();
    // Players playing the game
    private static Connect4FieldInterface theGame = new Connect4Field();
    // The model object
    private Connect4View view = new Connect4View();
    // The view object
    private static boolean gameIsOn = true;
    // boolean flag to check if game is still running
    Object lock;
    private PlayerInterface player;

    Connect4Controller() {

    }

    /**
     * Constructor for Connect4Controller. It sets
     * player and lock object for multi-threading
     * @param player
     * @param lock
     */
    Connect4Controller (PlayerInterface player, Object lock) {
        this.player = player;
        this.lock = lock;
    }

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
            Player player = new Player(this.theGame, playerName, gamePiece);
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
        synchronized (this.lock) {
            int column, counter = 0, index;
            while (gameIsOn) {
                lock.notify();
                view.print(theGame.toString());
                //index = counter++ % 2;
                column = this.player.nextMove();
                if (theGame.checkIfPiecedCanBeDroppedIn(column)) {
                    theGame.dropPieces(column, this.player.getGamePiece());
                    if (theGame.didLastMoveWin()) {
                        gameIsOn = false;
                        view.print(theGame.toString());
                        view.print(this.player.getName() + " won!");
                    } else if (theGame.isItaDraw()) {
                        gameIsOn = false;
                        view.print(theGame.toString());
                        view.print("Draw!!");
                    }
                } else {
                    view.print("Can not add... Sorry");
                }
                if (gameIsOn) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void run() {
        this.playGame();
    }

    /**
     * The main method.
     * It will ask players to enter their names and game-piece character.
     * And initialize the game object.
     * @param args    (ignored)
     */
    public static void main(String[] args) {
        Object o = new Object();
        Scanner inputScanner = new Scanner(System.in);
        Connect4Controller controller = new Connect4Controller();
        String name, gamePiece;
        controller.view.print("Welcome to Connect-4.");
        controller.view.print("Press 1 if you want to play with computer." +
                "\n Press 2 if you would like to play against someone.");
        int numberOfPlayers = Integer.parseInt(inputScanner.next());
        if (numberOfPlayers < 0 && numberOfPlayers > 2) {
            controller.view.print("Invalid input... Switching off the game.");
            System.exit(0);
        }
        for(int i = 0; i < numberOfPlayers; i++) {
            controller.view.print("What's the name of Player " + (i + 1) + ": ");
            name = controller.view.takeInput();
            if (name.isEmpty()) {
                controller.view.print("Illegal name");
                System.exit(1);
            }
            controller.view.print("What's the Game piece of Player " + (i + 1) + ": ");
            gamePiece = controller.view.takeInput();
            if (gamePiece.length() != 1) {
                controller.view.print("Illegal Game piece");
                System.exit(1);
            }
            controller.initPlayer("Player", name, gamePiece.charAt(0));
        }
        if (numberOfPlayers == 1) {
            controller.initPlayer("Computer", "Computer", '+');
        }
        controller.initGame();
        Connect4Controller player1Controller = new Connect4Controller(controller.players.get(0), o);
        Connect4Controller player2Controller = new Connect4Controller(controller.players.get(1), o);
        player1Controller.start();
        player2Controller.start();
    }

}
