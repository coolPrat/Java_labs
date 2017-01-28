/*
 * Connect4ControllerServer.java
 *
 * Version: 1: Connect4ControllerServer.java,v 1 11/7/2015 16:34:23
 *
 * Revisions: 1.0 initial version
 *
 */

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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
 * @author      Pratik kulkarni
 * @author      Kapil dole
 */

public class Connect4ControllerServer extends Thread{
    GameToken gameToken;
    Connect4Field theGame;
    private Vector<Player> players = new Vector<>();
    InetAddress[] hosts = new InetAddress[2];
    int[] ports = new int[2];
    DatagramPacket input;
    DatagramPacket responce;
    byte[] send;
    byte[] recieve;
//    ByteArrayInputStream ins[] = new ByteArrayInputStream[2];
//    ByteArrayOutputStream outs[] = new ByteArrayOutputStream[2];
//    ObjectOutputStream objOuts[] = new ObjectOutputStream[2];
//    ObjectInputStream objIns[] = new ObjectInputStream[2];
//    Socket[] clients = new Socket[2];
//    ObjectInputStream[] ins = new ObjectInputStream[2];
//    ObjectOutputStream[] outs = new ObjectOutputStream[2];

    static DatagramSocket server;
    String string, status = "on";
    boolean gameIsOn = true;

    /**
     * Constructor for Connect4ControllerServer.
     * It sets the port for running the server.
     * @param port
     */
    public Connect4ControllerServer(int port) {
        try {
            server = new DatagramSocket(port);
        } catch (IOException e) {
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
            recieve = new byte[1024];
            index = counter++ % 2;
            this.gameToken = new GameToken(this.theGame.toString(), this.players.get(index).getName(),
                    status);
            try {
                for (int i = 0; i < 2; i++) {
//                    objOuts[i].writeObject(gameToken);
                    send = this.gameToken.toString().getBytes();
                    responce = new DatagramPacket(send, send.length, hosts[i], ports[i]);
                    server.send(responce);
                }
                input = new DatagramPacket(recieve, recieve.length);
                server.receive(input);
                string = new String(input.getData());
                column = Integer.parseInt(string.trim());
                if (theGame.checkIfPiecedCanBeDroppedIn(column)) {
                    theGame.dropPieces(column, this.players.get(index).getGamePiece());
                    if (theGame.didLastMoveWin()) {
                        gameIsOn = false;
                        status = "won";
                        this.gameToken = new GameToken(this.theGame.toString(), this.players.get(index).getName(),
                                status);
                        for (int i = 0; i < 2; i++) {
//                            objOuts[i].writeObject(gameToken);
                            send = this.gameToken.toString().getBytes();
                            responce = new DatagramPacket(send, send.length, hosts[i], ports[i]);
                            server.send(responce);
                        }
                    } else if (theGame.isItaDraw()) {
                        gameIsOn = false;
                        status = "draw";
                        this.gameToken = new GameToken(this.theGame.toString(), this.players.get(index).getName(),
                                status);
                        for (int i = 0; i < 2; i++) {
//                            objOuts[i].writeObject(gameToken);
                            send = this.gameToken.toString().getBytes();
                            responce = new DatagramPacket(send, send.length, hosts[i], ports[i]);
                            server.send(responce);
                        }
                    }
                } else {
//                    objOuts[index].writeObject("Sorry can not add in that column!!");
                    send = "Sorry can not add in that column!!".getBytes();
                    responce = new DatagramPacket(send, send.length, hosts[index], ports[index]);
                    server.send(responce);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method will initialize players.
     * Number of players playing the game is taken as argument.
     * @param numberOfPlayers    Number of players playing the game
     */
    private void initTheGame(int numberOfPlayers) {
        try {
            for (int i = 0; i < numberOfPlayers; i++) {
                byte[] recieve = new byte[1024];
                this.input = new DatagramPacket(recieve, recieve.length);
                server.receive(this.input);
                String data = new String(this.input.getData());
                String[] tokens = data.split(",");
                String name = tokens[0];
                String gamePiece = tokens[1];
                this.hosts[i] = this.input.getAddress();
                this.ports[i] = this.input.getPort();
                this.players.add(new Player(name, gamePiece.charAt(0)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.theGame = new Connect4Field();
        this.theGame.init(this.players.get(0), this.players.get(1));
    }

    /**
     * The main program
     * This main create a server and will create several server threads
     * Each thread will handle one game.
     * @param args    command line arguments (ignored)
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Connect4ControllerServer mainServer = new Connect4ControllerServer(35000);
        while(true) {
            Connect4ControllerServer gameServer = new Connect4ControllerServer();
            gameServer.initTheGame(2);
            gameServer.start();
            gameServer.join();
        }
    }
}
