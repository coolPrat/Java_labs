/*
 * HelloImplementation.java
 *
 * Version: 1: HelloImplementation.java,v 1 11/22/2015 16:34:23
 *
 * Revisions: 1.0 initial version
 *
 */
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

/**
 * HelloImplementation class is the implementation of
 * HelloInterface. It has the test method that will help
 * us decide whether a call is local or remote.
 */
public class HelloImplementation
        extends UnicastRemoteObject
        implements HelloInterface {

    /**
     * Constructor for HelloImplementation class.
     * @throws RemoteException
     */
    public HelloImplementation() throws RemoteException {
    }

    /**
     * This method will return a string "Hello World my friend."
     * @return    string "Hello World my friend."
     * @throws RemoteException
     */
    public String sayHello() throws RemoteException {
        return  "Hello World my friend.";
    }

    /**
     * The test method. It will accept a string as input and
     * will return the same string.
     * @param i    String as input
     * @return     String taken as input
     * @throws RemoteException
     */
    @Override
    public String test(String i) throws RemoteException {
        return i;
    }
}