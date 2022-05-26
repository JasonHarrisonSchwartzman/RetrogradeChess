package RetrogradeChess;

import RetrogradeChess.Pieces.GColor;
import RetrogradeChess.Pieces.Empty;
import RetrogradeChess.Pieces.Pawn;
import RetrogradeChess.Pieces.Piece;


/**
 * This class has a coordinate and piece aspects
 */
public class Square {
    private Coordinate coordinates;
    private Piece piece;

    private Square up;
    private Square down;
    private Square left;
    private Square right;
    private Square upRight;
    private Square upLeft;
    private Square downRight;
    private Square downLeft;

    /**
     * Create a square with a coordinate object and piece
     * @param c
     * @param p
     */
    public Square(Coordinate c, Piece p) {
        coordinates = c;
        piece = p;
    }
    /**
     * Create a Square with a file and rank and a piece
     * @param file
     * @param rank
     * @param p
     */
    public Square(char file, int rank, Piece p) {
        coordinates = new Coordinate(file,rank);
        piece = p;
    }
    /**
     * Create a square with a String coordinate and a pice
     * @param coordinate
     * @param p
     */
    public Square(String coordinate, Piece p) {
        this.coordinates = new Coordinate(coordinate);
        piece = p;
    }

    /**
     * Not sure if I need this but its here most likely going to be used when creating a new board (for AI)
     * @param s
     */
    public Square(Square s) {
        coordinates = s.getCoordinates();
        piece = s.getPiece();
        up = s.getUp();
        down = s.getDown();
        left = s.getLeft();
        right = s.getRight();
        upRight = s.getUpRight();
        upLeft = s.getUpLeft();
        downLeft = s.getDownLeft();
        downRight = s.getDownRight();

    }

    // getter methods for Coordinates and Piece.
    public Coordinate getCoordinates() {
        return coordinates;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean hasPiece() {
        return !piece.getName().equals("Empty");
    }

    // this will be used when moving pieces
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    //getters and setters this will be used later when initiallizing the board
    public void setUp(Square s) { up = s; }
    public void setDown(Square s) { down = s; }
    public void setLeft(Square s) { left = s; }
    public void setRight(Square s) { right = s; }
    public void setUpRight(Square s) { upRight = s; }
    public void setUpLeft(Square s) { upLeft = s; }
    public void setDownRight(Square s) { downRight = s; }
    public void setDownLeft(Square s) { downLeft = s; }

    public Square getUp() { return up; }
    public Square getDown() { return down; }
    public Square getLeft() { return left; }
    public Square getRight() { return right; }
    public Square getUpRight() { return upRight; }
    public Square getUpLeft() { return upLeft; }
    public Square getDownRight() { return downRight; }
    public Square getDownLeft() { return downLeft; }

    /**
     * Printing out all of the adjacent squares
     * @return
     */
    public String getAdjacentSquares() {
        return "Square Up: " + ((getUp() == null) ? "null" : getUp().getCoordinates()) + "\nSquare Down: " + ((getDown() == null) ? "null" : getDown().getCoordinates()) + "\nSquare Left: " + ((getLeft() == null) ? "null": getLeft().getCoordinates()) + "\nSquare Right: " + ((getRight() == null) ? "null" : getRight().getCoordinates()) + "\nSquare Up Right: " + ((getUpRight() == null) ? "null" :getUpRight().getCoordinates()) + "\nSquare Up Left: " + ((getUpLeft() == null) ? "null" :getUpLeft().getCoordinates()) + "\nSquare Down Right: " + ((getDownRight() == null) ? "null" :getDownRight().getCoordinates()) + "\nSquare Down Left: " + ((getDownLeft() == null) ? "null" : getDownLeft().getCoordinates()) + "\n";
    }

    /**
     * Two squares are equal if they have the same coordinates...
     * @param square
     * @return boolean
     */
    public boolean equals(Square square) {
        if (square == null) {
            return false;
        }
        if (this.coordinates.getCoordinate().equals(square.getCoordinates().getCoordinate()) && piece.equals(square.getPiece())) {
            return true;
        }
        return false;
    }


    /**
     * This is used so you can System.out.println(Square object);
     * This prints out the pieces symbol. This is used when printing out the board.
     */
    public String toString() {
        return piece.getSymbol();
    }

    public String getInfo() {
        return toString() + " Square: " + getCoordinates().getCoordinate() + " | Piece: " + piece.getInfo();
    }

    public static void main(String[]args) {
        Square e4 = new Square("e4", new Empty());
        System.out.println(e4);
        e4.setPiece(new Pawn(GColor.WHITE));
        System.out.println(e4);
        System.out.println(e4.getPiece().getName());
    }
}
