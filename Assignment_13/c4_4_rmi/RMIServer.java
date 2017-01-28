/*
 * RMIServer.java
 *
 * Version: 1: RMIServer.java,v 1 21/11/2015 16:34:23
 *
 * Revisions: 1.0 initial version
 *
 */

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
* Class RMIServer implements RMIInterface.
* It will first bind an object of itself so server can notify RMIserver for creating
* new Model object.
* Once a request for creating new model obejct comes it will create new model and
* bind it to name theGame<count> where count is count of model object.
*
* @author      Pratik kulkarni
* @author      Kapil dole
*/
public class RMIServer extends UnicastRemoteObject implements RMIInterface {
    private static int count = 0;
    // static counter for counting number of models created
    static InetAddress ip;

    /**
    * Constructor for RMIServer
    */
    protected RMIServer() throws RemoteException {
    }

    /**
    * main rogram.
    * It will bind object of itself for game-server to use it
    * @params args    Command line argument (ignored)
    */
    public static void main(String[] args) throws Exception {
        ip = InetAddress.getLocalHost();
        System.out.println(ip.toString());
        RMIServer server = new RMIServer();
        Naming.bind("rmi://" + ip.getHostAddress() + ":35000/server", server);
        server.createNewModel();
    }


    /**
    * This method will be called by game-server to request new object
    * of model. New model will be bind to name "theGame<count>"
    */
    @Override
    public void createNewModel() throws Exception {
        Naming.bind("rmi://" + ip.getHostAddress() + ":35000/theGame" + count, new Connect4Field());
        count++;
    }
}
