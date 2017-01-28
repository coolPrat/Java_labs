import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Connect4FieldInterface extends Remote {
    boolean checkIfPiecedCanBeDroppedIn(int column) throws RemoteException;
    void dropPieces(int column, char gamePiece)throws RemoteException;
    boolean didLastMoveWin()throws RemoteException;
    boolean isItaDraw()throws RemoteException;
    void init(PlayerInterface playerA, PlayerInterface playerB)throws RemoteException;
    String getGame() throws RemoteException;
}