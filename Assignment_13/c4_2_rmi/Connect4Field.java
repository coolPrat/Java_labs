/*
 * Connect4Field.java
 *
 * Version: 2: Connect4Field.java,v 2 10/4/2015 16:34:23
 *
 * Revisions: 1.0 initial version
 *            2.0 Added methods required by computer-player
 *            3.0 Refactored in MVC
 *
 */

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Connect4Field  extends UnicastRemoteObject implements Connect4FieldInterface {
    /**
     * The class Connect4Field is the main class to play the game Connect-4.
     * It stores the players playing the game and current state of game. It has
     * methods to check if the current state is a draw or the player has won the
     * game.
     *
     * @author      Pratik kulkarni
     * @author      Kapil dole
     */

    private int rows; // number of rows in the game
    private int columns; // number of columns in the game
    private char[][] gameBoard; //the game board.It stores the state of the game
    private PlayerInterface[] thePlayers = new PlayerInterface[2];
    // Players playing the game
    private int piecesForWinning = 4;
    //Number of pieces required to form a winning pattern
    private int lastPlayedRow; // row number of last move
    private int lastPlayedColumn; // column number of last move

    /**
     * Sets the number pieces that are required to be in one line.
     * @param piecesForWinning    number of pieces
     */
    public void setPiecesForWinning(int piecesForWinning) throws RemoteException {
        this.piecesForWinning = piecesForWinning;
    }

    /**
     * Sets the game-board. This is the state of the game
     * @param gameBoard    character array representing state of the game.
     */
    public void setGameBoard(char[][] gameBoard) throws RemoteException {
        this.gameBoard = gameBoard;
    }

    /**
     * sets last played row number of the game
     * @param lastPlayedRow    last played row number.
     */
    public void setLastPlayedRow(int lastPlayedRow) throws RemoteException {
        this.lastPlayedRow = lastPlayedRow;
    }

    /**
     * sets last played column number of the game
     * @param lastPlayedColumn    last played column number
     */
    public void setLastPlayedColumn(int lastPlayedColumn) throws RemoteException {
        this.lastPlayedColumn = lastPlayedColumn;
    }

    /**
     * Returns number of columns of the game-board
     * @return    number of columns
     */
    public int getColumns() throws RemoteException {
        return columns;
    }

    /**
     * Returns last played column of the game
     * @return    number of column
     */
    public int getLastPlayedColumn() throws RemoteException {
        return lastPlayedColumn;
    }

    /**
     * Returns players that are playing the game
     * @return    player array playing the game
     */
    public PlayerInterface[] getThePlayers() throws RemoteException {
        return thePlayers;
    }

    /**
     * Returns game-board
     * @return    character array that represents game-bord
     */
    public char[][] getGameBoard() throws RemoteException {
        return gameBoard;
    }

    /**
     * Returns number of pieces required to be in a line to win the game
     * @return    number of pieces
     */
    public int getPiecesForWinning() throws RemoteException {
        return piecesForWinning;
    }

    /**
     * Returns last played row of the game
     * @return    last played row
     */
    public int getLastPlayedRow() throws RemoteException {
        return lastPlayedRow;
    }

    /**
     * The constructor of Connect4Field class.
     * It sets number of rows and columns and makes the game board.
     */
    public Connect4Field() throws RemoteException{
            this.rows = 9;
            this.columns = 25;
            makeBoard();
    }

    /**
     * This method checks if the current state of the game is a draw.
     *
     * @return     true if the game state is draw else false
     */
    @Override
    public boolean isItaDraw() throws RemoteException {
        return !(String.valueOf(gameBoard[0]).contains("O"));
    }

    /**
     * This method sets the players that are playing the game
     * @param playerA    First player playing the game
     * @param playerB    second player playing the game
     */
    @Override
    public void init(PlayerInterface playerA, PlayerInterface playerB) throws RemoteException {
        this.thePlayers[0] = playerA;
        this.thePlayers[1] = playerB;
    }

    /**
     * This method makes the game board. Its stored in gameBoard character array
     * Empty location is represented by 'O'. Unavailable place is denoted by
     * '' (empty character).
     */
    private void makeBoard() throws RemoteException {
        gameBoard = new char[rows][columns];
        int blank;
        for(int currentRow = 0; currentRow < this.rows; currentRow++) {
            blank = 2 * currentRow;
            for (int currentColumn = 0; currentColumn < columns; currentColumn++) {
                if (currentColumn < blank / 2) {
                    gameBoard[currentRow][currentColumn] = ' ';
                } else if ((currentColumn < columns / 2) ||
                            ((currentColumn >= columns / 2) &&
                                    (currentColumn < columns - blank / 2))) {
                    gameBoard[currentRow][currentColumn] = 'O';
                } else {
                    gameBoard[currentRow][currentColumn] = ' ';
                }

            }
        }
    }

    /**
     * This method checks if the specified column has space for next move.
     * @param column    The column number where player wishes to add new piece.
     * @return          true if the space is available else false.
     */
    @Override
    public boolean checkIfPiecedCanBeDroppedIn(int column) throws RemoteException {
        if ((column - 1 >= 0) && (column <= this.columns) ) {
            return gameBoard[0][column - 1] == 'O';
        } else {
            return false;
        }
    }

    /**
     * This method returns the row number available to add next piece for
     * specified column.
     * @param column    The column number where player wishes to add new piece.
     * @return          The row number available.
     */
    private int getAvailableRow(int column) throws RemoteException {
        int row = -1;
        column = column - 1;
        int lower, upper;
        if (gameBoard[rows/2][column] == 'O') {
            lower = rows / 2;
            upper = rows;
        } else {
            lower = 0;
            upper = rows / 2 ;
        }
        for(int i = lower; i < upper; i++) {
            if (gameBoard[i][column] == 'O')
                row = i;
        }
        return row;
    }

    /**
     * This method updates the state of the game. It adds specified game-piece
     * at specified column.
     * @param column    The column where player wishes to add the game-piece
     * @param gamePiece The game-piece of the player
     */
    @Override
    public void dropPieces(int column, char gamePiece) throws RemoteException {
        if (checkIfPiecedCanBeDroppedIn(column)) {
            int row = getAvailableRow(column);
            lastPlayedRow = row;
            lastPlayedColumn = column - 1;
            gameBoard[row][column - 1] = gamePiece;
        } else {
            System.out.println("Sorry... we can not drop in that column");
        }
    }

    /**
     * This method checks if the last move did win the game.
     * It checks the board for horizontal matches, vertical matches and
     * diagonal matches.
     *
     * @return    true if the match is found else false.
     */
    @Override
    public boolean didLastMoveWin() throws RemoteException {
        return (checkHorizontally() || checkVertically() || checkDiagonally());
    }

    /**
     * This method will return the pattern required to win the game.
     * This is used by other methods to check if there is any winning pattern on
     * the board.
     *
     * @param gamePiece    The game-piece for which winning pattern is required
     * @return             The winning pattern String.
     */
    public String getWinningPattern(char gamePiece, int piecesForWinning) throws RemoteException {
        String pattern = "";
        for(int i = 0; i < piecesForWinning; i++) {
            pattern += gamePiece;
        }
        return pattern;
    }

    /**
     * This method checks for the winning pattern horizontally in the last
     * played row.
     *
     * @return    true if the match is found else false.
     */
    private boolean checkHorizontally() throws RemoteException {
        char gamePiece = gameBoard[lastPlayedRow][lastPlayedColumn];
        String pattern = getWinningPattern(gamePiece, piecesForWinning);
        String playerState = String.valueOf(gameBoard[lastPlayedRow]);
        return (playerState.contains(pattern));
    }

    /**
     * This method checks for the winning pattern vertically in the last
     * played column.
     *
     * @return        true if the match is found else false.
     */
    private boolean checkVertically() throws RemoteException {
        char gamePiece = gameBoard[lastPlayedRow][lastPlayedColumn];
        String pattern = getWinningPattern(gamePiece, piecesForWinning);
        String playerState = "";
        for(int i = 0; i < rows; i++) {
            playerState += gameBoard[i][lastPlayedColumn];
        }
        return (playerState.contains(pattern));
    }

    /**
     * This method returns 2 Strings which are diagonal strings based on last
     * played row and column.
     *
     * @param lastPlayedRow    row number of last move
     * @param lastPlayedColunm column number of last move
     * @return                 two diagonal strings based on last played row
     *                         and column.
     */
    private StringBuffer[] getDiagonalString(int lastPlayedRow, int lastPlayedColunm) throws RemoteException {
        StringBuffer gamePiece = new StringBuffer(String.valueOf(gameBoard[lastPlayedRow][lastPlayedColunm]));
        StringBuffer[] diagonalStrings = new StringBuffer[2];
        diagonalStrings[0] = new StringBuffer();
        diagonalStrings[1] = new StringBuffer();

        int row,counter = 3;
        for(int i = 1; i <= counter; i++) {
            if ((lastPlayedRow + i) < rows) {
                row = lastPlayedRow + i;
                if ((lastPlayedColunm - i) >= 0)
                    diagonalStrings[0].append(gameBoard[row][lastPlayedColunm - i]);
                if ((lastPlayedColunm + i) < columns)
                    diagonalStrings[1].append(gameBoard[row][lastPlayedColunm + i]);
            }
        }
        diagonalStrings[0] = diagonalStrings[0].reverse().append(gamePiece);
        diagonalStrings[1] = diagonalStrings[1].reverse().append(gamePiece);
        for(int i = 1; i <= counter; i++) {
            if ((lastPlayedRow - i) >= 0) {
                row = lastPlayedRow - i;
                if ((lastPlayedColunm - i) >= 0)
                    diagonalStrings[1].append(gameBoard[row][lastPlayedColunm - i]);
                if ((lastPlayedColunm + i) < columns)
                    diagonalStrings[0].append(gameBoard[row][lastPlayedColunm + i]);
            }
        }
        return diagonalStrings;
    }

    /**
     * This method checks if there exists a winning pattern diagonally
     *
     * @return    true if the match is found else false.
     */
    private boolean checkDiagonally() throws RemoteException {
        char gamePiece = gameBoard[lastPlayedRow][lastPlayedColumn];
        String pattern = getWinningPattern(gamePiece, piecesForWinning);
        StringBuffer[] playerState = getDiagonalString(lastPlayedRow, lastPlayedColumn);
        return (String.valueOf(playerState[0]).contains(pattern) || String.valueOf(playerState[1]).contains(pattern));
    }

    @Override
    public String getGame() throws RemoteException {
        String game = "";
        for(char[] row: gameBoard) {
            game = game + "\n";
            for (char element: row) {
                game = game +  element;
            }
        }
        return game;
    }
}
