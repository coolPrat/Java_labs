/*
 * HelloC.java
 *
 * Version: 1: HelloC.java,v 1 11/22/2015 16:34:23
 *
 * Revisions: 1.0 initial version
 *
 */

import java.rmi.*;
import java.net.*;

/**
 * HelloC class will test whether a call is remote or local.
 */
public class HelloC {

    /**
     * This method will call test method on object accepted as input
     * and on comparing the response it will decide whether it was a
     * local call or remote call.
     * @param obj    object on which test method is called.
     */
    public static void localRemoteTest(HelloInterface obj)	{
        String message = new String("newMessageString");
        try {
            String message2 = obj.test(message);
            if (message == message2) {
                System.out.println("local");
            } else {
                System.out.println("remote");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The main program.
     * @param args    String array for command line arguments(ignored)
     */
    public static void main(String args[] ) {

        try {
            localRemoteTest(  (HelloInterface)Naming.lookup("rmi://" + InetAddress.getLocalHost().getHostAddress() + ":35001/IamAhelloImplementationObject") );
            System.out.println("---------------------------------------");
            localRemoteTest( new HelloImplementation() );
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}