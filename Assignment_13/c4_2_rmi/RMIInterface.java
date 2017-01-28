/*
 * RMIInterface.java
 *
 * Version: 1: RMIInterface.java,v 1 21/11/2015 16:34:23
 *
 * Revisions: 1.0 initial version
 *
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
* The RMI iterface.
*
* @author      Pratik kulkarni
* @author      Kapil dole
*/

public interface RMIInterface extends Remote {
    void createNewModel() throws Exception;
}
