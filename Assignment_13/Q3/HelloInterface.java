
public interface HelloInterface extends java.rmi.Remote {
    String sayHello() throws java.rmi.RemoteException;
    String test(String i) throws java.rmi.RemoteException;
}