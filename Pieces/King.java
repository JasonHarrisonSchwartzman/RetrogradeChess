package RetrogradeChess.Pieces;


import RetrogradeChess.Board;
import RetrogradeChess.Move;
import RetrogradeChess.Square;

public class King extends Piece{

    private String symbol;
    private final int VALUE = 0;
    private boolean hasMoved;
    public King(GColor color){
        super("King", color);
        if (color == GColor.WHITE)
            this.symbol = "[♔ ]";
        else
            this.symbol = "[♚ ]";
        hasMoved = false;
    }
    public King(Piece p) {
        super(p);
        symbol = p.getSymbol();
    }
    public String getSymbol(){
        return symbol;
    }
    public int getValue() {
        return VALUE;
    }
    public void hasMoved() {
        hasMoved = true;
    }
    public boolean getHasMoved() {
        return hasMoved;
    }

    public boolean controllingSquare(Board board, Move move) {
        Square startSquare = move.getStartSquare();
        Square endSquare = move.getEndSquare();
        if (endSquare.equals(startSquare.getUp()) || endSquare.equals(startSquare.getDown()) || endSquare.equals(startSquare.getLeft()) || endSquare.equals(startSquare.getRight()) || endSquare.equals(startSquare.getUpRight()) || endSquare.equals(startSquare.getUpLeft()) || endSquare.equals(startSquare.getDownLeft()) || endSquare.equals(startSquare.getDownRight())) {
            return true;
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
        if (endSquare.equals(startSquare.getUp()) || endSquare.equals(startSquare.getDown()) || endSquare.equals(startSquare.getLeft()) || endSquare.equals(startSquare.getRight()) || endSquare.equals(startSquare.getUpRight()) || endSquare.equals(startSquare.getUpLeft()) || endSquare.equals(startSquare.getDownLeft()) || endSquare.equals(startSquare.getDownRight())) {
            return true;
        }
        // castling remember to move the rook: ok yeah i think i did that
        //WHITE
        Square[][] boardArray = board.getBoard();
        if (turn == GColor.WHITE) {
            if (endSquare.getCoordinates().getCoordinate().equals("g1")) {
                return !startSquare.getPiece().getHasMoved() && boardArray[0][7].getPiece().getName().equals("Rook") && !boardArray[0][7].getPiece().getHasMoved() && boardArray[0][5].getPiece().getName().equals("Empty") && boardArray[0][6].getPiece().getName().equals("Empty") && !board.canAnyPieceMove(board.getSquare("e1"), GColor.BLACK) && !board.canAnyPieceMove(board.getSquare("f1"), GColor.BLACK) && !board.canAnyPieceMove(board.getSquare("g1"), GColor.BLACK);
            }
            else if (endSquare.getCoordinates().getCoordinate().equals("c1")) {
                return !startSquare.getPiece().getHasMoved() && boardArray[0][0].getPiece().getName().equals("Rook") && !boardArray[0][0].getPiece().getHasMoved() && boardArray[0][1].getPiece().getName().equals("Empty") && boardArray[0][2].getPiece().getName().equals("Empty") && boardArray[0][3].getPiece().getName().equals("Empty") && !board.canAnyPieceMove(boardArray[0][4], GColor.BLACK) && !board.canAnyPieceMove(boardArray[0][3], GColor.BLACK) && !board.canAnyPieceMove(boardArray[0][2], GColor.BLACK);
            }
            return false;
        }
        //BLACK
        else {
            if (endSquare.getCoordinates().getCoordinate().equals("g8")) {
                return !startSquare.getPiece().getHasMoved() && boardArray[7][7].getPiece().getName().equals("Rook") && !boardArray[7][7].getPiece().getHasMoved() && boardArray[7][5].getPiece().getName().equals("Empty") && boardArray[7][6].getPiece().getName().equals("Empty") && !board.canAnyPieceMove(boardArray[7][4], GColor.WHITE) && !board.canAnyPieceMove(boardArray[7][5], GColor.WHITE) && !board.canAnyPieceMove(boardArray[7][6], GColor.WHITE);
            }
            else if (endSquare.getCoordinates().getCoordinate().equals("c8")) {
                return !startSquare.getPiece().getHasMoved() && boardArray[7][0].getPiece().getName().equals("Rook") && !boardArray[7][0].getPiece().getHasMoved() && boardArray[7][1].getPiece().getName().equals("Empty") && boardArray[7][2].getPiece().getName().equals("Empty") && boardArray[7][3].getPiece().getName().equals("Empty") && !board.canAnyPieceMove(boardArray[7][4], GColor.WHITE) && !board.canAnyPieceMove(boardArray[7][3], GColor.WHITE) && !board.canAnyPieceMove(boardArray[7][2], GColor.WHITE);
            }
            return false;
        }
        
    }

    public static void main(String[]args) {
        

    }
}