import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * Created by Pratik on 11/18/2015.
 */
public class MyServer {
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        myClass mc = new myClass();
        Naming.rebind("myClass", mc);
    }
}
