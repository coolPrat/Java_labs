/*
 * Connect4ControllerClient.java
 *
 * Version: 1: Connect4ControllerClient.java,v 1 11/7/2015 16:34:23
 *
 * Revisions: 1.0 initial version
 *
 */


import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

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
    DatagramSocket soc; // socket used for communication
    DatagramPacket input;
    DatagramPacket responce;
    byte[] send;
    byte[] recieve = new byte[1024];
    static String string;
    InetAddress ip;
    int port = 35000;

    /**
     * Constructor of Connect4ControllerClient
     */
    Connect4ControllerClient() {
        try {
            soc = new DatagramSocket();
            ip = InetAddress.getByName("kansas.cs.rit.edu");
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
        Connect4ControllerClient client = new Connect4ControllerClient();
        client.initPlayer();
//        ObjectOutputStream out = new ObjectOutputStream(client.soc.getOutputStream());
//        ObjectInputStream in = new ObjectInputStream(client.soc.getInputStream());
//        out.writeObject(client.player);
//        ByteArrayInputStream in = new ByteArrayInputStream(client.recieve);
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        ObjectOutputStream objOut = new ObjectOutputStream(out);
//        objOut.writeObject(client.player);
        client.send = client.player.toString().getBytes();
        client.responce = new DatagramPacket(client.send, client.send.length, client.ip, client.port);
        client.soc.send(client.responce);
        client.input = new DatagramPacket(client.recieve, client.recieve.length);
//        client.soc.receive(client.input);
//        ObjectInputStream objIn = new ObjectInputStream(in);
//        int bufSize = Integer.parseInt((String)objIn.readObject());
//        client.recieve = new byte[bufSize];
//        ObjectInputStream objIn = null;
        do {
            client.input = new DatagramPacket(client.recieve, client.recieve.length);
            client.soc.receive(client.input);
//            if (objIn == null) {
//                objIn = new ObjectInputStream(in);
//            }
            String data = new String(client.input.getData());
            //TODO
            String[] tokens = data.split(",");
            client.gameToken = new GameToken(tokens[0].trim(), tokens[1].trim(), tokens[2].trim());
            System.out.println(client.gameToken.getTheGame());
            if (client.gameToken.getStatus().equals("on")) {
                if (client.gameToken.getPlayerName().equals(client.player.getName())) {
                    client.view.print("Your turn...");
                    string = client.view.takeInput();
//                    objOut.writeObject(string);
                    client.send = string.getBytes();
                    client.responce = new DatagramPacket(client.send, client.send.length, client.ip, client.port);
                    client.soc.send(client.responce);
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