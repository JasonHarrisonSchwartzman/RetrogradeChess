package RetrogradeChess;


import RetrogradeChess.Pieces.Piece;
/**
 * Move class contains two aspects:
 * piece: the piece that is going to move
 * square: the location where the piece is moving
 */

public class Move {
    private Square startSquare; //this is where the Piece is located
    private Piece piece;
    private Square endSquare;

    public Move(Square startSquare, Square endSquare) {
        this.startSquare = startSquare;
        this.endSquare = endSquare;
        piece = startSquare.getPiece();
    }

    public Piece getPiece() {
        return piece;
    }

    public Square getStartSquare() {
        return startSquare;
    }

    public Square getEndSquare() {
        return endSquare;
    }

    public String toString() {
        return "(" + startSquare.getCoordinates().getCoordinate() + ")Piece: " + piece + " Square: " + endSquare.getCoordinates().getCoordinate();
    }
}
