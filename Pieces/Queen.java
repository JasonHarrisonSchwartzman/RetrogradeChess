package RetrogradeChess.Pieces;

import RetrogradeChess.Board;
import RetrogradeChess.Move;

public class Queen extends Piece{

    private String symbol;
    private final int VALUE = 9;
    public Queen(GColor color){
        super("Queen", color);
        if (color == GColor.WHITE)
            this.symbol = "[♕ ]";
        else
            this.symbol = "[♛ ]";
    }
    public Queen(Piece p){
        super(p);
        symbol = p.getSymbol();
    }
    public String getSymbol(){
        return symbol;
    }
    public int getValue() {
        return VALUE;
    }

    public boolean controllingSquare(Board board, Move move) {
        GColor turn = board.getTurn();

        Bishop bishop = new Bishop(turn);
        Rook rook = new Rook(turn);

        return bishop.controllingSquare(board, move) || rook.controllingSquare(board, move);
    }
    public boolean canUnMove(Board board, Move move) {
        return canMove(board, move);
    }
    public boolean canMove(Board board, Move move) {
        GColor turn = board.getTurn();

        Bishop bishop = new Bishop(turn);
        Rook rook = new Rook(turn);

        return bishop.canMove(board, move) || rook.canMove(board, move);
    }
}