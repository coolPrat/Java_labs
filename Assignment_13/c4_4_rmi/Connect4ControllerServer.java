/*
 * Connect4ControllerServer.java
 *
 * Version: 2: Connect4ControllerServer.java,v 2 22/11/2015 16:34:23
 *
 * Revisions: 1.0 initial version
 *            2.0 Using RMI
 *
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Vector;


/**
 * The class Connect4ControllerServer represents controller on server's side.
 * It will communicate with client. Response will be a serialized object of type GameToken.
 * This will have String representation of current state of the game, the
 * status of the game and name of next player who is supposed to play its move.
 * Input received will be column number where player wants to place its next move.
 * Client will verify the state and will print corresponding output.
 * If the name of the next player matches with player using this client then
 * client accepts the input and passes it to server.
 *
 * This controller will use a remote model object for maintaining game state.
 *
 * @author      Pratik kulkarni
 * @author      Kapil dole
 */

public class Connect4ControllerServer extends Thread{
    static String serverName;
    GameToken gameToken;
    Connect4FieldInterface theGame;
    private Vector<Player> players = new Vector<>();
    InetAddress ip;
    int port = 35000;
    Socket[] clients = new Socket[4];
    ObjectInputStream[] ins = new ObjectInputStream[4];
    ObjectOutputStream[] outs = new ObjectOutputStream[4];
    static int numberOfPlayers;
    static RMIInterface rmiServer;
    static ServerSocket server;
    String string, status = "on";
    boolean gameIsOn = true;
    static int count = 0;

    /**
     * Constructor for Connect4ControllerServer.
     * It sets the port for running the server.
     * @param port    port on which server should run
     */
    public Connect4ControllerServer(int port, int numberOfPlayers) {
        try {
            ip = InetAddress.getLocalHost();
            System.out.println(ip.toString());
            server = new ServerSocket(port);
            this.numberOfPlayers = numberOfPlayers;
            rmiServer = (RMIInterface) Naming.lookup("rmi://" + serverName + ":35000/server");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Default constructor
     */
    public Connect4ControllerServer() {
    }

    /**
     * The run method.
     * This will be executed per game.
     * this will have accept input from the user and will reply with the gameToken
     * which will have updated game state and name of the player who is supposed
     * to play next.
     */
    public void run() {
        int column, counter = 0, index;
        while (gameIsOn) {
            try {
                index = counter++ % numberOfPlayers;
                this.gameToken = new GameToken(this.theGame.getGame(), this.players.get(index).getName(),
                    status);
                for (int i = 0; i < numberOfPlayers; i++) {
                    outs[i].writeObject(this.gameToken);
                }
                string = (String) ins[index].readObject();
                column = Integer.parseInt(string);
                if (theGame.checkIfPiecedCanBeDroppedIn(column)) {
                    theGame.dropPieces(column, this.players.get(index).getGamePiece());
                    if (theGame.didLastMoveWin()) {
                        gameIsOn = false;
                        status = "won";
                        this.gameToken = new GameToken(this.theGame.getGame(), this.players.get(index).getName(),
                                status);
                        for (int i = 0; i < numberOfPlayers; i++) {
                            outs[i].writeObject(this.gameToken);
                        }
                    } else if (theGame.isItaDraw()) {
                        gameIsOn = false;
                        status = "draw";
                        this.gameToken = new GameToken(this.theGame.getGame(), this.players.get(index).getName(),
                                status);
                        for (int i = 0; i < numberOfPlayers; i++) {
                            outs[i].writeObject(this.gameToken);
                        }
                    }
                } else {
                    outs[index].writeObject("Sorry can not add in that column!!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        try {
            this.clients[0].close();
            this.clients[1].close();
            this.clients[2].close();
            this.clients[3].close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will initialize players.
     * Number of players playing the game is taken as argument.
     */
    private void initTheGame() throws Exception {
        try {
            for (int i = 0; i < numberOfPlayers; i++) {
                this.clients[i] = this.server.accept();
                this.ins[i] = new ObjectInputStream(this.clients[i].getInputStream());
                this.outs[i] = new ObjectOutputStream(this.clients[i].getOutputStream());
                this.players.add((Player) this.ins[i].readObject());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.theGame = (Connect4FieldInterface) Naming.lookup("rmi://" + serverName + ":35000/theGame" + count);
        if (numberOfPlayers == 2) {
            this.theGame.init(this.players.get(0), this.players.get(1));
        } else if (numberOfPlayers == 4) {
            this.theGame.init(this.players.get(0), this.players.get(1), this.players.get(2), this.players.get(3));
        }
        rmiServer.createNewModel();
        count++;
    }

    /**
     * The main program
     * This main create a server and will create several server threads
     * Each thread will handle one game.
     * @param args    command line arguments (ignored)
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws Exception {
        serverName = args[0];
        Connect4ControllerServer mainServer = new Connect4ControllerServer(35000, 4);
        while(true) {
            Connect4ControllerServer gameServer = new Connect4ControllerServer();
            gameServer.initTheGame();
            gameServer.start();
        }
    }
}
