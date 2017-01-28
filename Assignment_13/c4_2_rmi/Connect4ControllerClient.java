/*
 * Connect4ControllerClient.java
 *
 * Version: 2: Connect4ControllerClient.java,v 2 21/11/2015 16:34:23
 *
 * Revisions: 1.0 Using TCP communication
 *            2.0 Using RMI
 *
 */


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * The class Connect4ControllerClient represents controller on clients side.
 * It will communicate with server about clients inputs and will receive responses.
 * Response will be a serialized object of type GameToken. This will have String
 * representation of current state of the game, the status of the game and name
 * of next player who is supposed to play its move.
 * Client will verify the state and will print corresponding output.
 * If the name of the next player matches with player using this client then
 * client accepts the input and passes it to server.
 *
 * @author      Pratik kulkarni
 * @author      Kapil dole
 */
public class Connect4ControllerClient {

    Connect4View view = new Connect4View();
    // Object of view
    GameToken gameToken;
    // token object
    Player player;
    Socket soc; // socket used for communication
    static String string;
    static String serverName;

    /**
     * Constructor of Connect4ControllerClient
     */
    Connect4ControllerClient() {
        try {
            soc = new Socket(serverName, 35000);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will take input from the player and create a Player object
     * to pass to server.
     */
    private void initPlayer() {
        this.view.print("What's the name of Player :");
        String name = this.view.takeInput();
        if (name.isEmpty()) {
            this.view.print("Illegal name");
            System.exit(1);
        }
        this.view.print("What's the Game piece of Player : ");
        String gamePiece = this.view.takeInput();
        if (gamePiece.length() != 1) {
            this.view.print("Illegal Game piece");
            System.exit(1);
        }
        player = new Player(name, gamePiece.charAt(0));
    }

    /**
     * The main program.
     * This will create a Player object as per users inout and will pass that
     * to server. When all the players are connected, player can play the game.
     *
     * The game will run till someone wins it or players end up in a draw state.
     * As soon as the game is over the socket is closed.
     *
     * Players turn and current state of the game are received using a game-token
     * object.
     *
     * @param args    command line arguments (ignored)
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        serverName = args[0];
        Connect4ControllerClient client = new Connect4ControllerClient();
        client.initPlayer();
        ObjectOutputStream out = new ObjectOutputStream(client.soc.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(client.soc.getInputStream());
        out.writeObject(client.player);
        do {
            client.gameToken = (GameToken)in.readObject();
            System.out.println(client.gameToken.getTheGame());
            if (client.gameToken.getStatus().equals("on")) {
                if (client.gameToken.getPlayerName().equals(client.player.getName())) {
                    client.view.print("Your turn...");
                    string = client.view.takeInput();
                    out.writeObject(string);
                } else {
                    client.view.print(client.gameToken.getPlayerName() + "\'s turn...");
                }
            } else if(client.gameToken.getStatus().equals("draw")) {
                client.view.print("Its a draw...!");
            } else {
                client.view.print(client.gameToken.getPlayerName() + " won!!");
            }
        }while(client.gameToken.getStatus().equals("on"));
        client.soc.close();
    }
}