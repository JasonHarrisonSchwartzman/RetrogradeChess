package RetrogradeChess.Pieces;

import RetrogradeChess.Board;
import RetrogradeChess.Move;
import RetrogradeChess.Square;

import java.awt.geom.Point2D;

public class Knight extends Piece{

    private String symbol;
    private final int VALUE = 3;
    public Knight(GColor color){
        super("Knight", color);
        if (color == GColor.WHITE)
            this.symbol = "[♘ ]";
        else
            this.symbol = "[♞ ]";
    }
    public Knight(Piece p) {
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
        Square startSquare = move.getStartSquare();
        Square endSquare = move.getEndSquare();

        char endFile = endSquare.getCoordinates().getFile();
        int endRank = endSquare.getCoordinates().getRank();
        char startFile = startSquare.getCoordinates().getFile();
        int startRank = startSquare.getCoordinates().getRank();

        //Knight moving constant
        Point2D x = new Point2D.Double();
        Point2D y = new Point2D.Double();
        x.setLocation(0, 0);
        y.setLocation(1, 2);
        double distance = x.distance(y);

        Point2D start = new Point2D.Double();
        Point2D end = new Point2D.Double();
        start.setLocation(startFile - 97, startRank - 1);
        end.setLocation(endFile - 97, endRank - 1);

        if (start.distance(end) == distance) {
            return true;
        }
        board.debugger("KNIGHT CANT MOVE: NOT EQUAL TO KNIGHT CAPTURE CONSTANT");
        return false;
    }

    public boolean canUnMove(Board board, Move move) {
        return canMove(board, move);
    }

    public boolean canMove(Board board, Move move) {
        if (!super.canMove(board, move)) {
            return false;
        }
        Square startSquare = move.getStartSquare();
        Square endSquare = move.getEndSquare();

        char endFile = endSquare.getCoordinates().getFile();
        int endRank = endSquare.getCoordinates().getRank();
        char startFile = startSquare.getCoordinates().getFile();
        int startRank = startSquare.getCoordinates().getRank();

        //Knight moving constant
        Point2D x = new Point2D.Double();
        Point2D y = new Point2D.Double();
        x.setLocation(0, 0);
        y.setLocation(1, 2);
        double distance = x.distance(y);

        Point2D start = new Point2D.Double();
        Point2D end = new Point2D.Double();
        start.setLocation(startFile - 97, startRank - 1);
        end.setLocation(endFile - 97, endRank - 1);

        if (start.distance(end) == distance) {
            return true;
        }
        board.debugger("KNIGHT CANT MOVE: NOT EQUAL TO KNIGHT CAPTURE CONSTANT");
        return false;
    }


}