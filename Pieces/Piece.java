package RetrogradeChess.Pieces;

import RetrogradeChess.Board;
import RetrogradeChess.Move;
import RetrogradeChess.Square;

/**
 * The general Piece object
 * Pieces have:
 * a name
 * notation (one or 0 letter abbreviation) (I actually didn't do anything with notation in the class lol, so right now its useless)
 * color (white or black)
 * if they have moved (for castling)
 * 
 * They also have a symbol which is what gets printed out the the terminal.
 */
public class Piece{
    private String name;
    private char notation;
    private GColor color;
    private boolean hasMoved;
    private String symbol;
    private int value;
    public Piece(String name, GColor color) {
        this.name = name;
        this.color = color;
    }

    /**
     * Checks to see if two pieces are equal
     * @param piece
     * @return boolean
     */
    public boolean equals(Piece piece) {
        return name.equals(piece.getName()) && color == piece.getColor(); //&& hasMoved == piece.getHasMoved();
    }

    /**
     * Enter a piece object to create a new Piece object with the same information
     * @param p
     */
    public Piece(Piece p) {
        name = p.name;
        notation = p.getNotation();
        color = p.getColor();
        hasMoved = p.getHasMoved();
        symbol = p.getSymbol();
        value = p.getValue();
    }

    /**
     * Enter a name to specify the piece (for creating the board)
     * @param name
     */
    public Piece(String name){
        this.name = name;
    }
    

    //getters
    public String getName(){return name;}
    public char getNotation() {return notation;}
    public GColor getColor(){return color;}
    public String getSymbol(){return symbol;} 
    public boolean getHasMoved(){return hasMoved;}
    public void hasMoved(){hasMoved = true;}
    public int getValue(){return value;} // it should NEVER return this tho


    public boolean controllingSquare(Board board, Move move) {
        return true;
    }

    /**
     * This will be testing general things about moving a piece
     * @param b
     * @param move
     * @return
     */
    public boolean canMove(Board board, Move move) {
        GColor turn = board.getTurn();
        Square startSquare = move.getStartSquare();
        Square endSquare = move.getEndSquare();
        Piece piece = move.getPiece();

        // if the the start and end square are the same or if the turn and piece are not the same color 
        //and if the piece on the endSquare is the player's own piece
        if (startSquare.equals(endSquare)) {
            board.debugger("Piece not moving.");
            return false;
        }
        if (piece.getColor() != turn) {
            board.debugger("Unexpected piece color.");
            return false;
        }
        if (endSquare.getPiece().getColor() == turn) {
            board.debugger("Cannot move onto square that contains a piece.");
            return false;
        }
        return true;
    }
    
    /**
     * Prints out the name of the piece and the color
     */
    public String toString() {
        return getName() + " " + getColor();
    }

    public String getInfo() {
        return toString() + " | Has moved: " + hasMoved;
    }
    /**
     * Checks if not moving, not turn, piece on end square
     * @param board
     * @param move
     * @return can't move if fails conditions above
     */
    public boolean canUnMove(Board board, Move move) {
        GColor turn = board.getTurn();
        Square startSquare = move.getStartSquare();
        Square endSquare = move.getEndSquare();
        Piece piece = move.getPiece();

        if (startSquare.equals(endSquare)) {
            board.debugger("Piece not moving.");
            return false;
        }
        if (piece.getColor() != turn) {
            board.debugger("Unexpected piece color.");
            return false;
        }
        if (endSquare.hasPiece()) {
            board.debugger("Cannot move onto square that contains a piece.");
            return false;
        }
        return true;
    }

}
/*
white chess king	♔	U+2654	&#9812;	&#x2654;
white chess queen	♕	U+2655	&#9813;	&#x2655;
white chess rook	♖	U+2656	&#9814;	&#x2656;
white chess bishop	♗	U+2657	&#9815;	&#x2657;
white chess knight	♘	U+2658	&#9816;	&#x2658;
white chess pawn	♙	U+2659	&#9817;	&#x2659;
black chess king	♚	U+265A	&#9818;	&#x265A;
black chess queen	♛	U+265B	&#9819;	&#x265B;
black chess rook	♜	U+265C	&#9820;	&#x265C;
black chess bishop	♝	U+265D	&#9821;	&#x265D;
black chess knight	♞	U+265E	&#9822;	&#x265E;
black chess pawn	♟︎	U+265F	&#9823;	&#x265F;
*/

