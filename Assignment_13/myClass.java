import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Pratik on 11/18/2015.
 */
public class myClass extends UnicastRemoteObject implements myInterface {


    public myClass() throws RemoteException {

    }

    @Override
    public int lengthOfString(String string) throws RemoteException {
        return string.length();
    }
    
    @Override
    public String toString() {
        String str = "In toString";
        return str;
    }
}
