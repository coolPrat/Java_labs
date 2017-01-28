/*
 * Computer.java
 *
 * Version: 1: Computer.java,v 2 10/4/2015 16:34:23
 *
 * Revisions: 1.0 initial version
 *            2.0 Refactored in MVC
 *            3.0 Refactored in multi-threaded game
 *
 */

import java.util.Random;

public class Computer implements PlayerInterface {
    /**
     * The class Computer is actually a Computer-player. It stores
     * an object Connect4FieldInterface which the game it is playing.
     * It will manipulate the current state of the board to decide the next move.
     *
     * @author      Pratik kulkarni
     * @author      Kapil dole
     */

    private char gamePiece; // Game-piece of the computer
    private String name; // name of the computer-player
    private Connect4FieldInterface theGame; // game object
    private Connect4Field cloneOfTheGame = new Connect4Field();
    // clone of the game object. used to manipulate the state.
    private char[][] gameBoard ;
    // game-board. used to manipulate the state of the game.


    /**
     * Constructor of the Computer class.
     * @param connect4Field    The game it is playing
     * @param name             Name of the computer-player
     * @param gamePiece        game-piece of the computer-player
     */
    public Computer(Connect4FieldInterface connect4Field,
                    String name,
                    char gamePiece) {
        this.gamePiece = gamePiece;
        this.name = name;
        this.theGame = connect4Field;
    }

    /**
     * Returns game piece of the computer.
     * @return    game-piece character
     */
    @Override
    public char getGamePiece() {
        return this.gamePiece;
    }

    /**
     * returns the name of the computer-player.
     * @return    String name of the computer-player
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Used to replicate the game. Initializes the clone object with current
     * players.
     * @param theGame    Player.Player object (human player)
     */
    private char[][] replicateTheGame(Connect4Field theGame) {
        cloneOfTheGame.init(theGame.getThePlayers()[0], theGame.getThePlayers()[0]);
        return replicateCurrentState(theGame.getGameBoard());
    }

    /**
     * This method replicates the current state of the game. current state is
     * stored in char array "gameBoard". This method copies and returns this
     * array.
     * @param currentState    The current state of the gaem that needs to be copied
     * @return                Copy of current game state
     */
    private char[][] replicateCurrentState(char[][] currentState) {
        gameBoard = new char[currentState.length][currentState[0].length];
        for(int row = 0; row < currentState.length; row++) {
            for(int column = 0; column < currentState[row].length; column++) {
                gameBoard[row][column] = currentState[row][column];
            }
        }
        cloneOfTheGame.setGameBoard(gameBoard);
        return gameBoard;
    }

    /**
     * This method decides the next move of the computer-player. For this it uses
     * copy of current game state and tries to find out if we have any of
     * following situations:
     *     1. Computer can win:        In this situation it returns the column number
     *                                 where computer can win the game.
     *     2. player can win:          In this case it returns the column number which
     *                                 needs to be blocked (i.e. the column number
     *                                 where player might play next move and win)
     *     3. player has two pieces:   In this situation computer will drop the
     *                                 piece in that column which might led to
     *                                 victory of player. As player has 2 pieces
     *                                 computer blocks it right away reducing
     *                                 player's chances to win.
     *     4. Computer has multiple
     *        pieces               :   In this case computer will drop the piece
     *                                 in that column where he can have multiple
     *                                 pieces. So its a move trying to make
     *                                 winning pattern.
     *
     * If we do not come across any-of the above situations computer will drop
     * the piece in any randomly selected column.
     *
     * @return    column number where computer should play next move.
     */
    @Override
    public int nextMove() {
        System.out.println(this.name + "\'s turn.");
        int column;
        if(ComputerCanWin()) {
            column = getColumnNumber();
        } else if (playerIsWinning()) {
            column = getColumnNumber();
        } else if (playerHasTwoPieces()) {
            column = getColumnNumber();
        } else if (computersNextMove()) {
            column = getColumnNumber();
        } else {
            Random rand = new Random();
            column = rand.nextInt(25) + 1;
        }
        System.out.println(column);
        return column;
    }

    /**
     * The method checks if computer can win the game in next move.
     *
     * @return    true if computer can win the game else false.
     */
    private boolean ComputerCanWin() {
        replicateTheGame((Connect4Field) theGame);
        return checkWholeBoard(this.gamePiece);
    }

    /**
     * The method returns the column number where computer should play next move.
     *
     * @return    column number
     */
    private int getColumnNumber() {
        return (cloneOfTheGame.getLastPlayedColumn() + 1);
    }

    /**
     * The method checks if computer can form a pattern with multiple game-pieces
     *
     * @return    true if computer can form the pattern else false.
     */
    private boolean computersNextMove() {
        replicateTheGame((Connect4Field) theGame);
        char gamePiece = this.gamePiece;
        boolean nextMovePossible = false;
        int piecesForWinning = cloneOfTheGame.getPiecesForWinning();
        cloneOfTheGame.setPiecesForWinning(piecesForWinning - 2);
        nextMovePossible = checkWholeBoard(gamePiece);
        cloneOfTheGame.setPiecesForWinning(piecesForWinning);
        return nextMovePossible;
    }

    /**
     * The method checks if player can win the game in next move.
     *
     * @return    true if player can win in next move else false.
     */
    private boolean playerIsWinning() {
        replicateTheGame((Connect4Field) theGame);
        cloneOfTheGame.setLastPlayedRow(((Connect4Field) theGame).getLastPlayedRow());
        cloneOfTheGame.setLastPlayedColumn(((Connect4Field) theGame).getLastPlayedColumn());
        char opponentsGamePiece = cloneOfTheGame.getThePlayers()[0].getGamePiece();
        return checkWholeBoard(opponentsGamePiece);
    }

    /**
     * The method checks if player has multiple pieces in a line.
     *
     * @return    true if player has multiple pieces else false.
     */
    private boolean playerHasTwoPieces() {
        replicateTheGame((Connect4Field) theGame);
        cloneOfTheGame.setLastPlayedRow(((Connect4Field) theGame).getLastPlayedRow());
        cloneOfTheGame.setLastPlayedColumn(((Connect4Field) theGame).getLastPlayedColumn());
        char opponentsGamePiece = cloneOfTheGame.getThePlayers()[0].getGamePiece();
        int piecesForWinning = cloneOfTheGame.getPiecesForWinning();
        cloneOfTheGame.setPiecesForWinning(3);
        boolean opponentCanWin = checkWholeBoard(opponentsGamePiece);
        cloneOfTheGame.setPiecesForWinning(piecesForWinning);
        return opponentCanWin;
    }

    /**
     * This method checks the game board for required pattern for given gamepiece
     * by dropping the piece at every column.
     *
     * @param gamePiece    the game-piece character
     * @return             true if the pattern if found else false.
     */
    private boolean checkWholeBoard(char gamePiece) {
        boolean condition = false;
        for(int column = 1; column <= cloneOfTheGame.getColumns(); column++) {
            if(cloneOfTheGame.checkIfPiecedCanBeDroppedIn(column)) {
                cloneOfTheGame.dropPieces(column, gamePiece);
                if (cloneOfTheGame.didLastMoveWin()) {
                    condition = true;
                    break;
                }
            }
            cloneOfTheGame.setGameBoard(replicateCurrentState(((Connect4Field) theGame).getGameBoard()));
        }
        return condition;
    }
}
