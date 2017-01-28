
public interface Connect4FieldInterface {
    boolean checkIfPiecedCanBeDroppedIn(int column);
    void dropPieces(int column, char gamePiece);
    boolean didLastMoveWin();
    boolean isItaDraw();
    void init(PlayerInterface playerA, PlayerInterface playerB);
    String toString();
}