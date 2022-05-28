package RetrogradeChess.Pieces;

import RetrogradeChess.Board;
import RetrogradeChess.Move;
import RetrogradeChess.Square;

public class Bishop extends Piece{

    private String symbol;
    private final int VALUE = 3;
    public Bishop(GColor color){
        super("Bishop", color);
        if (color == GColor.WHITE)
            this.symbol = "[♗ ]";
        else
            this.symbol = "[♝ ]";
    }
    public Bishop(Piece p) {
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

        if (Math.abs(endFile - startFile) != Math.abs(endRank - startRank)) {
            return false;
        }

        String direction = "";

        if (endFile > startFile && endRank > startRank) {
            direction = "up right";
        }
        else if (endFile < startFile && endRank > startRank) {
            direction = "up left";
        }
        else if (endFile < startFile && endRank < startRank) {
            direction = "bottom left";
        }
        else if (endFile > startFile && endRank < startRank) {
            direction = "bottom right";
        }
        else {
            System.out.println("heh?");
        }

        while(!endSquare.equals(currentSquare)) {
            if (direction.equals("up right")) {
                currentSquare = currentSquare.getUpRight();
            }
            else if (direction.equals("up left")) {
                currentSquare = currentSquare.getUpLeft();
            }
            else if (direction.equals("bottom left")) {
                currentSquare = currentSquare.getDownLeft();
            }
            else if (direction.equals("bottom right")) {
                currentSquare = currentSquare.getDownRight();
            }
            else {
                //System.out.println("ERROR LMAO");
            }
            if (currentSquare.getPiece().getName().equals("Empty") || ((currentSquare.getPiece().getName().equals("Queen") || currentSquare.getPiece().getName().equals("Bishop") || currentSquare.getPiece().getName().equals("Pawn")) && currentSquare.getPiece().getColor() == turn)) {
                if (endSquare.equals(currentSquare)) {
                    return true;
                }
                //checks to see if pawn can move to endSquare (the pawn in front)
                if (currentSquare.getPiece().getName().equals("Pawn")) {
                    Pawn pawn = new Pawn(turn);
                    Move pawnMove = new Move(currentSquare, endSquare);
                    return pawn.controllingSquare(board, pawnMove);
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
        //idk ask brandt
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

        if (Math.abs(endFile - startFile) != Math.abs(endRank - startRank)) {
            return false;
        }

        String direction = "";

        if (endFile > startFile && endRank > startRank) {
            direction = "up right";
        }
        else if (endFile < startFile && endRank > startRank) {
            direction = "up left";
        }
        else if (endFile < startFile && endRank < startRank) {
            direction = "bottom left";
        }
        else if (endFile > startFile && endRank < startRank) {
            direction = "bottom right";
        }
        else {
            System.out.println("heh?");
        }

        while(!endSquare.equals(currentSquare)) {
            if (direction.equals("up right")) {
                currentSquare = currentSquare.getUpRight();
            }
            else if (direction.equals("up left")) {
                currentSquare = currentSquare.getUpLeft();
            }
            else if (direction.equals("bottom left")) {
                currentSquare = currentSquare.getDownLeft();
            }
            else if (direction.equals("bottom right")) {
                currentSquare = currentSquare.getDownRight();
            }
            else {
                //System.out.println("ERROR LMAO");
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
        //idk ask brandt
        if (endSquare.equals(currentSquare)) {
            return true;
        }
        return false;
    }
    
    public static void main(String[]args) {
       
    }
}