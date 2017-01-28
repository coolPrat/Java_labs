/*
 * ThroughputClient.java
 *
 * Version: 1: ThroughputClient.java,v 1 11/14/2015 16:34:23
 *
 * Revisions: 1.0 initial version
 *
 */


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;


/**
 * This class serves as client for finding throughput of the network.
 * This will take command line argument as protocol to use and then
 * we receive and send data to server.
 * Frm the time required to transfer the data, network throughput will be calculated.
 * @author Pratik kulkarni
 * @author Kapil dole
 */
public class ThroughputClient {

    static String host = "kansas.cs.rit.edu";
    static int port = 35000;
    static byte[] buff = new byte[64000];

    /**
     * Method for udp connection to calculate network throughput.
     * @throws IOException
     */
    public static void udpTest() throws IOException {
        InetAddress ip = InetAddress.getByName(host);
        DatagramSocket cSocket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(buff, buff.length);
        packet.setAddress(ip);
        packet.setPort(port);
        cSocket.send(packet);
        do {
            DatagramPacket rPacket = new DatagramPacket(buff, buff.length);
            cSocket.receive(rPacket);
            ip = rPacket.getAddress();
            port = rPacket.getPort();
            buff = rPacket.getData();
            packet = new DatagramPacket(buff, buff.length);
            packet.setAddress(ip);
            packet.setPort(port);
            cSocket.send(packet);
        } while (!((new String(buff)).trim().equals("quit")));
    }


    /**
     * Method for tcp connection to calculate network throughput.
     * @throws IOException
     */
    public static void tcpTest() throws IOException {
        Socket client = new Socket("kansas.cs.rit.edu", 35000);
        DataOutputStream dOut = new DataOutputStream(client.getOutputStream());
        DataInputStream dIn = new DataInputStream(client.getInputStream());
        byte[] message = new byte[64000];
        dIn.readFully(message);
        dOut.write(message);

        dIn.readFully(message);
        dOut.write(message);

        client.close();
    }


    /**
     * Main program.
     * This will take network protocol as command line argument
     * and will call corresponding method to find network throughput.
     * @param args    Command line argument as protocol to use
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if (args[0].equals("udp")) {
            udpTest();
        } else if (args[0].equals("tcp")) {
            tcpTest();
        }
    }
}
