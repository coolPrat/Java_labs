import java.rmi.*;
import java.net.*;

public class HelloServer {

    public static void main(String args[])
    {
        // System.setSecurityManager(new RMISecurityManager());

        try {
            HelloInterface obj = new HelloImplementation();
            Naming.rebind("rmi://" + InetAddress.getLocalHost().getHostAddress() + ":35001/IamAhelloImplementationObject", obj);
            //System.out.println("HelloServer bound in registry");
        } catch (Exception e) {
            System.out.println("HelloImpl err: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
