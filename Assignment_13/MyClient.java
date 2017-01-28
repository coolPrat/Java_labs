import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.net.URL;

/**
 * Created by Pratik on 11/19/2015.
 */
public class MyClient {
    public static void main(String[] args) {
        try {
        //URL u = new URL("rmi://kansas.cs.rit.edu/myClass");
            myInterface o = (myInterface) Naming.lookup("rmi://kansas.cs.rit.edu/myClass");
            String[] str = {"p"};
            int i = o.lengthOfString("pppp");
            System.out.println(i);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
