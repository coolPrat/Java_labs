import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Pratik on 11/6/2015.
 */
public class echoServer extends Thread {

    private ServerSocket request;
    BufferedReader br;
    String str = "";

    public echoServer() {
        try {
            request = new ServerSocket(35000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (!str.equals("quit")) {
            try {
                Socket client = request.accept();
                br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                str = br.readLine();
                System.out.println(str);
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        echoServer es = new echoServer();
        es.start();
    }
}
