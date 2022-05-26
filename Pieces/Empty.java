package RetrogradeChess.Pieces;

/**
 * Empty "Pieces" don't have a color, but they have a symbol so its easy to print out the board.
 */
public class Empty extends Piece{

    private String symbol;
    public Empty(){
        super("Empty");
        symbol = "[  ]";
    }
    public String getSymbol(){
        return symbol;
    }

    public boolean controllingSquare() {
        return false;
    }

    public boolean canMove() {
        return false;
    }
}