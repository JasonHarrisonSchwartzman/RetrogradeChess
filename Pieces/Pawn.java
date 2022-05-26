package RetrogradeChess.Pieces;

import RetrogradeChess.Board;
import RetrogradeChess.Square;
import RetrogradeChess.Move;

public class Pawn extends Piece{

    private String symbol;
    private final int VALUE = 1;
    public Pawn(GColor color){
        super("Pawn", color);
        if (color == GColor.WHITE)
            this.symbol = "[♙ ]";
        else
            this.symbol = "[♟︎ ]";
    }
    public Pawn(Piece p) {
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
       

        char endFile = endSquare.getCoordinates().getFile();
        int endRank = endSquare.getCoordinates().getRank();
        char startFile = startSquare.getCoordinates().getFile();
        int startRank = startSquare.getCoordinates().getRank();

        if (endSquare.equals(startSquare) || !startSquare.getPiece().getName().equals("Pawn")) {
            return false;
        }

        String direction = "";
        if (turn == GColor.WHITE) {
            direction = "up";
        }
        else {
            direction = "down";
        }

        //white
        if (direction.equals("up")) {
            if (endRank < startRank || endRank - startRank > 2 || Math.abs(endFile - startFile) > 1) {
                return false;
            }
            // one square
            else {
                // captures
                    //does this line work?????????? i think so
                if ((endSquare.equals(startSquare.getUpLeft()) || endSquare.equals(startSquare.getUpRight()))) {
                    return true;
                }
            }

        }
        //black
        else {
            if (endRank > startRank || startRank - endRank > 2 || Math.abs(endFile - startFile) > 1) {
                return false;
            }
            // one square
            else {
                // captures
                    //does this line work?????????? i think so
                if ((endSquare.equals(startSquare.getDownLeft()) || endSquare.equals(startSquare.getDownRight()))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canMove(Board board, Move move) {
        if (!super.canMove(board, move)) {
            return false;
        }
        GColor turn = board.getTurn();
        Square startSquare = move.getStartSquare();
        Square endSquare = move.getEndSquare();
       

        char endFile = endSquare.getCoordinates().getFile();
        int endRank = endSquare.getCoordinates().getRank();
        char startFile = startSquare.getCoordinates().getFile();
        int startRank = startSquare.getCoordinates().getRank();

        String direction = "";
        if (turn == GColor.WHITE) {
            direction = "up";
        }
        else {
            direction = "down";
        }

        //white
        if (direction.equals("up")) {
            if (endRank < startRank || endRank - startRank > 2 || Math.abs(endFile - startFile) > 1) {
                if (endRank < startRank) {
                    board.debugger("CANT MOVE PAWN: BACKWARDS");
                }
                if (endRank - startRank > 2) {
                    board.debugger("CANT MOVE PAWN: MOVING MORE THAN 2 SQUARES UP");
                }
                if (Math.abs(endFile - startFile) > 1) {
                    board.debugger("CANT MOVE PAWN: MOVING SIDEWAYS MORE THAN 2 SQUARES");
                }
                return false;
            }
            //two squares
            if (endRank - startRank == 2) {
                if (startRank == 2) {
                    if (startSquare.getUp().hasPiece() || endSquare.hasPiece() || endSquare.getCoordinates().getFile() != startSquare.getCoordinates().getFile()) {
                        if (startSquare.getUp().hasPiece()) {
                            board.debugger("CANT MOVE PAWN: PIECE ON SQUARE");
                        }
                        return false;
                    }
                    return true;
                }
                else {
                    board.debugger("CANT MOVE PAWN: MOVING TWO SQUARES WHEN NOT AT SECOND RANK");
                    return false;
                }
            }
            // one square
            else {
                //  non captures
                if (endFile == startFile) {
                    if (endSquare.hasPiece()) {
                        return false;
                    }
                    return true;
                }
                // captures
                else {
                    //does this line work?????????? i think so
                    if (endSquare.hasPiece() && (endSquare.equals(startSquare.getUpLeft()) || endSquare.equals(startSquare.getUpRight()))) {
                        return true;
                    }
                    // en passant remember to delete the pawn
                    else {
                        Move lastMove = board.getLastMove();
                        if (lastMove == null) {
                            return false;
                        }
                        Square lastMoveStartSquare = lastMove.getStartSquare();
                        Square lastMoveEndSquare = lastMove.getEndSquare();
                        Piece lastMovePiece = lastMove.getPiece();
                        return lastMovePiece.getName().equals("Pawn") && lastMoveStartSquare.getCoordinates().getRank() == 7 && lastMoveEndSquare.getCoordinates().getRank() == 5 && endSquare.getDown().equals(lastMoveEndSquare) && (endSquare.equals(startSquare.getUpLeft()) || endSquare.equals(startSquare.getUpRight()));
                    }
                }
            }

        }
        //black
        else {
            if (endRank > startRank || startRank - endRank > 2 || Math.abs(endFile - startFile) > 1) {
                return false;
            }
            //two squares
            if (endRank - startRank == -2) {
                if (startRank == 7) {
                    if (startSquare.getDown().hasPiece() || endSquare.hasPiece() || endSquare.getCoordinates().getFile() != startSquare.getCoordinates().getFile()) {
                        return false;
                    }
                    return true;
                }
                else {
                    return false;
                }
            }
            // one square
            else {
                //  non captures
                if (endFile == startFile) {
                    if (endSquare.hasPiece()) {
                        return false;
                    }
                    return true;
                }
                // captures
                else {
                    //does this line work?????????? i think so
                    if (endSquare.hasPiece() && (endSquare.equals(startSquare.getDownLeft()) || endSquare.equals(startSquare.getDownRight()))) {
                        return true;
                    }
                    // en passant remember to delete the pawn
                    else {
                        Move lastMove = board.getLastMove();
                        Square lastMoveStartSquare = lastMove.getStartSquare();
                        Square lastMoveEndSquare = lastMove.getEndSquare();
                        Piece lastMovePiece = lastMove.getPiece();
                        return lastMovePiece.getName().equals("Pawn") && lastMoveStartSquare.getCoordinates().getRank() == 2 && lastMoveEndSquare.getCoordinates().getRank() == 4 && endSquare.getUp().equals(lastMoveEndSquare) && (endSquare.equals(startSquare.getDownLeft()) || endSquare.equals(startSquare.getDownRight()));
                    }
                }
            }
        }
    }
}