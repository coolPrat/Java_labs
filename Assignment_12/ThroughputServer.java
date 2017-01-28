/*
 * ThroughputServer.java
 *
 * Version: 1: ThroughputServer.java,v 1 11/14/2015 16:34:23
 *
 * Revisions: 1.0 initial version
 *
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

/**
 * This class serves as server for finding throughput of the network.
 * This will take command line argument as protocol to use and then
 * we receive and send data to client.
 * Frm the time required to transfer the data, network throughput will be calculated.
 * @author Pratik kulkarni
 * @author Kapil dole
 */
public class ThroughputServer {

    static InetAddress host;
    static int port = 35000;

    /**
     * Method for udp connection to calculate network throughput.
     * @throws IOException
     */
    public static void udpTest() throws IOException {
        byte[] receive = new byte[64000];
        byte[] buff = new byte[64000];
        int diff = 0;
        DatagramSocket server = new DatagramSocket(35000);
        DatagramPacket inPacket = new DatagramPacket(receive, receive.length);
        server.receive(inPacket);
        host = inPacket.getAddress();
        port = inPacket.getPort();
        for (int i=0; i< 64000; i++) {
            buff[i] = (byte)0;
        }
        DatagramPacket outputPacket = new DatagramPacket(buff, buff.length, host, port);
            long startTime = System.currentTimeMillis();
            server.send(outputPacket);
          
           DatagramPacket packet = new DatagramPacket(receive, receive.length);
            server.receive(packet);

        outputPacket = new DatagramPacket(buff, buff.length, host, port);
        server.send(outputPacket);

        packet = new DatagramPacket(receive, receive.length);
        server.receive(packet);

        diff += (int)(System.currentTimeMillis() - startTime);
    
        outputPacket = new DatagramPacket("quit".getBytes(), "quit".getBytes().length, host, port);
        server.send(outputPacket);
        System.out.println("Throughput: " + (((256000/diff)*1000)/1048576)+ " mbps");
    }

    /**
     * Method for tcp connection to calculate network throughput.
     * @throws IOException
     */
    public static void tcpTest() throws IOException {
        int diff = 0;
        int counter = 0;
        ServerSocket request = new ServerSocket(35000);
        Socket client = request.accept();
        byte[] message = new byte[64000];
        byte[] message2 = new byte[64000];
        for (int i=0; i< message.length; i++) {
            message[i] = (byte)0;
        }
        DataOutputStream dOut = new DataOutputStream(client.getOutputStream());
        DataInputStream dIn = new DataInputStream(client.getInputStream());
            long startTime = System.currentTimeMillis();
            dOut.write(message);
            dIn.readFully(message2, 0, message2.length);
            dOut.write(message);
            dIn.readFully(message2, 0, message2.length);

        diff = (int)(System.currentTimeMillis() - startTime);
        
        client.close();        
        System.out.println("Throughput: " + (((256000)/diff)*1000)/1048576 + " mbps");
    }

    /**
     * Main program.
     * This will take network protocol as command line argument
     * and will call corresponding method to find network throughput.
     * @param args    Command line argument as protocol to use
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args[0].equals("udp")) {
            udpTest();
        } else if (args[0].equals("tcp")) {
            tcpTest();
        }
    }
    /*
    Explanation: UDP will give better throughput than TCP.
    This is because TCP has overhead of acknowledgements which will
    lower down the throughput.
    In UDP acknowledgements are not transfered so we get heigher throughput
    */
}
