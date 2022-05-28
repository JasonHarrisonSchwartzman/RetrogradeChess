package RetrogradeChess.Pieces;

import RetrogradeChess.Board;
import RetrogradeChess.Move;
import RetrogradeChess.Square;

public class Rook extends Piece{

    private String symbol;
    private boolean hasMoved;
    private final int VALUE = 5;
    public Rook(GColor color){
        super("Rook", color);
        if (color == GColor.WHITE)
            this.symbol = "[♖ ]";
        else
            this.symbol = "[♜ ]";
        hasMoved = false;
    }
    public Rook(Piece p){
        super(p);
        symbol = p.getSymbol();
    }
    public boolean getHasMoved(){
        return hasMoved;
    }
    public void hasMoved(){
        hasMoved = true;
    }
    public String getSymbol(){
        return symbol;
    }
    public int getValue() {
        return VALUE;
    }

    public boolean controllingSquare(Board board, Move move) {
        GColor turn = board.getTurn();
        Square startSquare = move.getStartSquare();
        Square endSquare = move.getEndSquare();
        Square currentSquare = startSquare;

        char endFile = endSquare.getCoordinates().getFile();
        int endRank = endSquare.getCoordinates().getRank();
        char startFile = startSquare.getCoordinates().getFile();
        int startRank = startSquare.getCoordinates().getRank();

        if (endSquare.equals(startSquare)) {
            return false;
        }

        // if not on same rank or file
        if (!(startFile == endFile || startRank == endRank)) {
            return false;
        }

        String direction = "";
        if (endFile > startFile && startRank == endRank) {
            direction = "right";
        }
        else if (endFile < startFile && startRank == endRank) {
            direction = "left";
        }
        else if (endFile == startFile && endRank > startRank) {
            direction = "up";
        }
        else if (endFile == startFile && endRank < startRank) {
            direction = "down";
        }
        else {
            System.out.println("ERROR WHAT ARE YOU DOING JASON");
        }
        while(!endSquare.equals(currentSquare)) {
            if (direction.equals("right")) {
                currentSquare = currentSquare.getRight();
            }
            else if (direction.equals("left")) {
                currentSquare = currentSquare.getLeft();
            }
            else if (direction.equals("up")) {
                currentSquare = currentSquare.getUp();
            }
            else if (direction.equals("down")) {
                currentSquare = currentSquare.getDown();
            }
            else {
                System.out.println("ERROR BIG BOY");
            }
            //if there is a queen or rook of the same color in front, it will continue the loop
            if (currentSquare.getPiece().getName().equals("Empty") || ((currentSquare.getPiece().getName().equals("Queen") || currentSquare.getPiece().getName().equals("Rook")) && currentSquare.getPiece().getColor() == turn)) {
                if (endSquare.equals(currentSquare)) {
                    return true;
                }
                continue;
            }
            if (currentSquare.getPiece().getColor() == turn) {
                if (endSquare.equals(currentSquare)) {
                    return true;
                }
                return false;
            }
            //idk ask brandt
            if (endSquare.equals(currentSquare)) {
                return true;
            }
            return false;
        }
        // idk ask brandt
        if (endSquare.equals(currentSquare)) {
            return true;
        }
        return false;
    }
    public boolean canUnMove(Board board, Move move) {
        return canMove(board, move);
    }
    public boolean canMove(Board board, Move move) {
        if (!super.canMove(board, move)) {
            return false;
        }
        GColor turn = board.getTurn();
        Square startSquare = move.getStartSquare();
        Square endSquare = move.getEndSquare();
        Square currentSquare = startSquare;

        char endFile = endSquare.getCoordinates().getFile();
        int endRank = endSquare.getCoordinates().getRank();
        char startFile = startSquare.getCoordinates().getFile();
        int startRank = startSquare.getCoordinates().getRank();

        // if not on same rank or file
        if (!(startFile == endFile || startRank == endRank)) {
            return false;
        }

        String direction = "";
        if (endFile > startFile && startRank == endRank) {
            direction = "right";
        }
        else if (endFile < startFile && startRank == endRank) {
            direction = "left";
        }
        else if (endFile == startFile && endRank > startRank) {
            direction = "up";
        }
        else if (endFile == startFile && endRank < startRank) {
            direction = "down";
        }
        else {
            System.out.println("ERROR WHAT ARE YOU DOING JASON");
        }
        while(!endSquare.equals(currentSquare)) {
            if (direction.equals("right")) {
                currentSquare = currentSquare.getRight();
            }
            else if (direction.equals("left")) {
                currentSquare = currentSquare.getLeft();
            }
            else if (direction.equals("up")) {
                currentSquare = currentSquare.getUp();
            }
            else if (direction.equals("down")) {
                currentSquare = currentSquare.getDown();
            }
            else {
                System.out.println("ERROR BIG BOY");
            }
            if (currentSquare.getPiece().getName().equals("Empty")) {
                if (endSquare.equals(currentSquare)) {
                    return true;
                }
                continue;
            }
            if (currentSquare.getPiece().getColor() == turn) {
                return false;
            }
            //idk ask brandt
            if (endSquare.equals(currentSquare)) {
                return true;
            }
            return false;
        }
        // idk ask brandt
        if (endSquare.equals(currentSquare)) {
            return true;
        }
        return false;
    }
}