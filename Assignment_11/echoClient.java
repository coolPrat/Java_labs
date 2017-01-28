import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Pratik on 11/6/2015.
 */
public class echoClient {

    static Scanner input = new Scanner(System.in);
    static String str;
    public static void main(String[] args) throws IOException {
        InetAddress ip = InetAddress.getLocalHost();
        System.out.println(ip);
        Socket soc;
        PrintWriter br;
        do {
            str = input.next();
            soc = new Socket("kansas.cs.rit.edu", 35000);
            br = new PrintWriter(soc.getOutputStream(), true);
            br.println(str);
            soc.close();
        }while (!str.equals("quit"));
    }
}
