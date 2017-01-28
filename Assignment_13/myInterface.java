import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Pratik on 11/18/2015.
 */
public interface myInterface extends Remote {

    int lengthOfString(String string) throws RemoteException;
    String toString();
}
