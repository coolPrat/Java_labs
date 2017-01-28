public interface PlayerInterface {

// this is how your constructor has to be
// Player(Connect4FieldInterface theField, String name, char gamePiece)

    char getGamePiece();
    String getName();
    int  nextMove();
}