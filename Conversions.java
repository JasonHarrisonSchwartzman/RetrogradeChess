package RetrogradeChess;


import RetrogradeChess.Pieces.Piece;

public class Conversions {
    /**
     * Input indices [i][j] as a String ex. [0][0] is "00"
     * Returns the coordinate representation
     * @param indices 
     * @return Coordinate
     */
    public static Coordinate arrayIndicesToCoordinates(String indices) {
        char index1 = (char)(Integer.parseInt(indices.substring(1)) + 97);
        Integer index2 = Integer.parseInt(indices.substring(0,1)) + 1;
        Coordinate c = new Coordinate(index1, index2);
        return c;
    }
    /**
     * Input either a String or Coordinate object
     * Returns the array indices [0][5] is "05"
     * @param c
     * @return String
     */

    public static String coordinatesToArrayIndices(Coordinate c) {
        return "" + (c.getRank() - 1) + (c.getFile() - 97);
    }
    /**
     * Input either a String or Coordinate object
     * Returns the array indices [0][5] is "05"
     * @param s
     * @return String
     */
    public static String coordinatesToArrayIndices(String s) {
        Coordinate c = new Coordinate(s);
        return coordinatesToArrayIndices(c);
    }

    /**
     * Converts a move object to a string that is (almost) like algebraic notation
     * @param move
     * @return String
     */
    public static String moveToAlgebraic(Move move) {
        String pieceNotation = "";
        Piece piece = move.getPiece();
        if (piece.getName().equals("Knight")) {
            pieceNotation = "N";
        }
        else if (piece.getName().equals("Bishop")) {
            pieceNotation = "B";
        }
        else if (piece.getName().equals("Rook")) {
            pieceNotation = "R";
        }
        else if (piece.getName().equals("Queen")) {
            pieceNotation = "Q";
        }
        else if (piece.getName().equals("King")) {
            pieceNotation = "K";
        }
        return pieceNotation + move.getEndSquare().getCoordinates().getCoordinate();
    }

    public static void main(String[]args) {
        System.out.println(arrayIndicesToCoordinates("10"));
        System.out.println(coordinatesToArrayIndices("a3"));
    }
}
