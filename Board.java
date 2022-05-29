package RetrogradeChess;

import java.util.ArrayList;
import java.util.HashMap;

import RetrogradeChess.Pieces.Bishop;
import RetrogradeChess.Pieces.GColor;
import RetrogradeChess.Pieces.Knight;
import RetrogradeChess.Pieces.Empty;
import RetrogradeChess.Pieces.Queen;
import RetrogradeChess.Pieces.Rook;
import RetrogradeChess.Pieces.King;
import RetrogradeChess.Pieces.Pawn;
import RetrogradeChess.Pieces.Piece;

public class Board {
    private Square[][] board;
    private GColor turn;
    private ArrayList<Move> moves;
    private HashMap<GColor,ArrayList<Square>> pieceLocation; //HashMap that contains an arraylist of squares where the pieces of each player is
    private Square capturedPiece; //holds a captured piece if a piece was just captured
    private boolean debug = false;

    public GColor getTurn() {
    	return turn;
    }

    /**
     * returns the array list of moves
     * @return
     */
    public ArrayList<Move> getMoves() {
        return moves;
    }

    /**
     * Switches the turn
     */
    public void switchTurn() {
        if (turn == GColor.WHITE) {
            turn = GColor.BLACK;
        }
        else {
            turn = GColor.WHITE;
        }
    }
    
    public void makeTurn(GColor c) {
    	turn = c;
    }
    
    /**
     * Gets the color of who's turn it is not
     * @return Color of next turn
     */
    public GColor getNextTurn() {
        if (turn == GColor.WHITE) {
            return GColor.BLACK;
        }
            return GColor.WHITE;
    }

    /**
     * Creates a new Board object and sets it up
     */
    public Board() {
        moves = new ArrayList<Move>();
        //setting up pieceLocations
        ///pieceLocation = new HashMap<Color,ArrayList<Square>>();
        //pieceLocation.put(Color.WHITE, new ArrayList<Square>());
        //pieceLocation.put(Color.BLACK, new ArrayList<Square>());
        //resets the board (initializes it)
        resetBoard();
    }
    /**
     * Returns the last move in the move arraylist
     * you better hope its not empty
     * @return Move
     */
    public Move getLastMove() {
        if (moves.size() == 0) {
            return null;
        }
        return moves.get(moves.size() - 1);
    }

    /**
     * Gets a square from the board array given the coordinates ex. "e4"
     * @param coordinates
     * @return Square object from the board array
     */
    public Square getSquare(String coordinates) {
        String s = Conversions.coordinatesToArrayIndices(coordinates);
        return board[Integer.parseInt(s.substring(0,1))][Integer.parseInt(s.substring(1))];
    }

    /**
     * Creates a new board given a predefined board (i think)
     * @param b
     */
    public Board(Board b) {
        board = new Square[8][8];
        Square[][] boardArray = b.getBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Square(boardArray[i][j]);
            }
        }
        ArrayList<Move> bMoves = b.getMoves();
        moves = new ArrayList<Move>();
        for (int i = 0; i < bMoves.size(); i++) {
            moves.set(i, bMoves.get(i));
        }
    }

    /**
     * Makes a board given a predefined board and makes a move
     * @param b
     * @param move
     */
    public Board(Board b, Move move) {
        this(b);
        this.move(move);
    }
    /**
     * This method sets up all the adjacent square references in each square object
     */
    public void setAdjacentSquares() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i != 7) {
                    board[i][j].setUp(board[i + 1][j]);
                    if (j != 7) {
                        board[i][j].setUpRight(board[i + 1][j + 1]);
                    }
                    if (j != 0) {
                        board[i][j].setUpLeft(board[i + 1][j - 1]);
                    }
                }
                if (i != 0) {
                    board[i][j].setDown(board[i - 1][j]);
                    if (j != 7) {
                        board[i][j].setDownRight(board[i - 1][j + 1]);
                    }
                    if (j != 0) {
                        board[i][j].setDownLeft(board[i - 1][j - 1]);
                    }
                }
                if (j != 0) {
                    board[i][j].setLeft(board[i][j - 1]);
                }
                if (j != 7) {
                    board[i][j].setRight(board[i][j + 1]);
                }
            }
        }
    }
    /**
     * You can also convert the two Squares to a move object to move a piece
     * @param startSquare
     * @param endSquare
     */
    public void move(Square startSquare, Square endSquare) {
        move(new Move(startSquare, endSquare));
    }
    /**
     * This will move a piece from one square on the board to the other
     * @param move
     */
    public void move(Move move) {
        //note that this method doesn't check the move's legality

        //disecting the move 
        Piece piece = move.getPiece(); // creates a new Piece object
        Square startSquare = move.getStartSquare();
        Square endSquare = move.getEndSquare();
        // where in the board array we will operate
        String startSquareIndices = Conversions.coordinatesToArrayIndices(startSquare.getCoordinates());
        String endSquareIndices = Conversions.coordinatesToArrayIndices(endSquare.getCoordinates());
        //deletes the opponents piece from the piecelocation array is it was on the endSquare
        if (board[Integer.parseInt(endSquareIndices.substring(0,1))][Integer.parseInt(endSquareIndices.substring(1))].hasPiece()) {
            //pieceLocation.get(getNextTurn()).remove(board[Integer.parseInt(endSquareIndices.substring(0,1))][Integer.parseInt(endSquareIndices.substring(1))]);
            capturedPiece = new Square(endSquare); // should add the captured piece to the arrayList
        }
        // capturedPiece is empty otherwisep
        else {
            capturedPiece = null;
        }
        //**MOVING THE PIECE**
        // now the piece on the original square is placed on the end square and the original square is now empty
        if (piece.getName().equals("Pawn")) {
            if (endSquare.getPiece().getName().equals("Empty") && startSquare.getCoordinates().getFile() != endSquare.getCoordinates().getFile()) {
                enPassent(move);
            }
        }
        board[Integer.parseInt(startSquareIndices.substring(0,1))][Integer.parseInt(startSquareIndices.substring(1))].setPiece(new Empty()); //startSquare
        board[Integer.parseInt(endSquareIndices.substring(0,1))][Integer.parseInt(endSquareIndices.substring(1))].setPiece(piece); //endSquare
        moves.add(move);
        //en passent and pawn promotion
        if (piece.getName().equals("Pawn")) {
            if (endSquare.getCoordinates().getRank() == 1 || endSquare.getCoordinates().getRank() == 8) {
                promotePawn(move);
            }
        }
        //if the piece that moved was king or rook they moved (for castling)
        if (piece.getName().equals("King") || piece.getName().equals("Rook")) {
            piece.hasMoved();
        }
        //if the king moved 2 squares its trying to castle
        if (piece.getName().equals("King") && Math.abs(endSquare.getCoordinates().getFile() - startSquare.getCoordinates().getFile()) == 2) {
            castle(move);
        }


        //updates the piece location
        //updatePieceLocation(startSquare, endSquare);
        //finally ends the move function
        switchTurn();
    }

    /**
     * Lazy ass solution
     * @param move
     */
    public void tempMove(Move move) {
        //note that this method doesn't check the move's legality

        //disecting the move 
        Piece piece = move.getPiece(); // creates a new Piece object
        Square startSquare = move.getStartSquare();
        Square endSquare = move.getEndSquare();
        // where in the board array we will operate
        String startSquareIndices = Conversions.coordinatesToArrayIndices(startSquare.getCoordinates());
        String endSquareIndices = Conversions.coordinatesToArrayIndices(endSquare.getCoordinates());
        //deletes the opponents piece from the piecelocation array is it was on the endSquare
        if (board[Integer.parseInt(endSquareIndices.substring(0,1))][Integer.parseInt(endSquareIndices.substring(1))].hasPiece()) {
            //pieceLocation.get(getNextTurn()).remove(board[Integer.parseInt(endSquareIndices.substring(0,1))][Integer.parseInt(endSquareIndices.substring(1))]);
            capturedPiece = new Square(endSquare); // should add the captured piece to the arrayList
        }
        // capturedPiece is empty otherwisep
        else {
            capturedPiece = null;
        }
        //**MOVING THE PIECE**
        // now the piece on the original square is placed on the end square and the original square is now empty
        if (piece.getName().equals("Pawn")) {
            if (endSquare.getPiece().getName().equals("Empty") && startSquare.getCoordinates().getFile() != endSquare.getCoordinates().getFile()) {
                enPassent(move);
            }
        }
        board[Integer.parseInt(startSquareIndices.substring(0,1))][Integer.parseInt(startSquareIndices.substring(1))].setPiece(new Empty()); //startSquare
        board[Integer.parseInt(endSquareIndices.substring(0,1))][Integer.parseInt(endSquareIndices.substring(1))].setPiece(piece); //endSquare
        moves.add(move);
        //en passent and pawn promotion
        if (piece.getName().equals("Pawn")) {
            if (endSquare.getCoordinates().getRank() == 1 || endSquare.getCoordinates().getRank() == 8) {
                promotePawn(move);
            }
        }
        //if the king moved 2 squares its trying to castle
        if (piece.getName().equals("King") && Math.abs(endSquare.getCoordinates().getFile() - startSquare.getCoordinates().getFile()) == 2) {
            castle(move);
        }


        //updates the piece location
        //updatePieceLocation(startSquare, endSquare);
        //finally ends the move function
        switchTurn();
    }

    /**
     * Will capture the pawn being en passented
     * @param move
     */
    public void enPassent(Move move) {
        Square endSquare = move.getEndSquare();
        GColor currentTurn = move.getPiece().getColor();
        //not sure if you can set the piece given the Square object and not the arary
        if (currentTurn == GColor.WHITE) {
            capturedPiece = new Square(endSquare.getDown());
            endSquare.getDown().setPiece(new Empty());
            //pieceLocation.get(Color.BLACK).remove(endSquare.getDown());
        }
        else {
            capturedPiece = new Square(endSquare.getUp());
            endSquare.getUp().setPiece(new Empty());
            //pieceLocation.get(Color.WHITE).remove(endSquare.getUp());
        }
    }

    /**
     * Promotes the pawn only to a queen idc about the others
     * @param move
     */
    public void promotePawn(Move move) {
        Square endSquare = move.getEndSquare();
        if (turn == GColor.WHITE) {
            endSquare.setPiece(new Queen(GColor.WHITE));
        }
        else {
            endSquare.setPiece(new Queen(GColor.BLACK));
        }
    }

    /**
     * Will MOVE the rook while castling 
     * DOES NOT CHECK IF ITS LEGAL
     * @param move
     */
    public void castle(Move move) {
        Square endSquare = move.getEndSquare();

        if (endSquare.getCoordinates().getCoordinate().equals("g1")) {
            board[0][5].setPiece(board[0][7].getPiece()); 
            board[0][7].setPiece(new Empty());
            //updatePieceLocation(board[0][7], board[0][5]); 
        }
        else if (endSquare.getCoordinates().getCoordinate().equals("c1")) {
            board[0][3].setPiece(board[0][0].getPiece()); 
            board[0][0].setPiece(new Empty()); 
            //updatePieceLocation(board[0][0], board[0][3]);
        }
        else if (endSquare.getCoordinates().getCoordinate().equals("g8")) {
            board[7][5].setPiece(board[7][7].getPiece()); 
            board[7][7].setPiece(new Empty());
            //updatePieceLocation(board[7][7], board[7][5]);
        }
        // c8 (hopefully)
        else {
            board[7][3].setPiece(board[7][0].getPiece()); 
            board[7][0].setPiece(new Empty()); 
            //updatePieceLocation(board[7][0], board[7][3]);
        }
    }

    /**
     * Takes back a move for now its only going to be the last move only
     * @param move
     */
    public void takeBack(Move move) {
        Piece piece = move.getPiece();
        Square startSquare = move.getStartSquare();
        Square endSquare = move.getEndSquare();
        String startSquareIndices = Conversions.coordinatesToArrayIndices(startSquare.getCoordinates());
        String endSquareIndices = Conversions.coordinatesToArrayIndices(endSquare.getCoordinates());

        moves.remove(moves.size() - 1); // removes the last move
        if (capturedPiece != null && !capturedPiece.getCoordinates().getCoordinate().equals(endSquare.getCoordinates().getCoordinate())) {
            //special illegal en passent case
            board[Integer.parseInt(startSquareIndices.substring(0,1))][Integer.parseInt(startSquareIndices.substring(1))].setPiece(piece);
            board[Integer.parseInt(Conversions.coordinatesToArrayIndices(capturedPiece.getCoordinates()).substring(0,1))][Integer.parseInt(Conversions.coordinatesToArrayIndices(capturedPiece.getCoordinates()).substring(1))].setPiece(capturedPiece.getPiece());
            board[Integer.parseInt(endSquareIndices.substring(0,1))][Integer.parseInt(endSquareIndices.substring(1))].setPiece(new Empty());
        }
        else {
            board[Integer.parseInt(startSquareIndices.substring(0,1))][Integer.parseInt(startSquareIndices.substring(1))].setPiece(piece); //startSquare
            board[Integer.parseInt(endSquareIndices.substring(0,1))][Integer.parseInt(endSquareIndices.substring(1))].setPiece((capturedPiece == null) ? new Empty() : capturedPiece.getPiece()); //endSquare
        }

        //pieceLocation.get(turn).set(pieceLocation.get(turn).indexOf(endSquare), startSquare); //puts the piece back on the original square on location map
        if (capturedPiece != null) {
            //pieceLocation.get(getNextTurn()).add(capturedPiece); //WHY DOES THIS THROW AN EXCEPTION WHAT IS GOD
        }


    }

    public boolean canUnMove(Move move) {
        if (move == null) {
            return false;
        }
        Square startSquare = move.getStartSquare();
        Square endSquare = move.getEndSquare();
        Piece piece = startSquare.getPiece();
        if (piece.canMove(this, move)) {
            return true;
        }
        return true;
    }


    /**
     * The highest up canMove function.
     * First will see if the piece can move
     * Second will move
     * Third will see if the king gets into check will take back the move if it puts the king in check
     * Returns true if piece canMove
     * @param move
     * @return true if piece can move
     */
    public boolean canMove(Move move) {
        if (move == null) {
            return false;
        }
        Square startSquare = move.getStartSquare();
        Square endSquare = move.getEndSquare();
        Piece piece = startSquare.getPiece();
        if (piece.canMove(this, move)) {
            //the king canMove function checks to see if you're in every square so it can bypass moving and then taking back
            if (piece.getName().equals("King") && Math.abs(endSquare.getCoordinates().getFile() - startSquare.getCoordinates().getFile()) == 2) {
                return true;
            }

            //move on board
            tempMove(move);
            // has to get previous turn because the move function changed the turn
            
            if (isInCheck(getNextTurn())) {
                //if the king is in check after move return false
                debugger("CANT MOVE: WILL BE IN CHECK AFTER MOVING");
                takeBack(move);
                switchTurn();
                return false;
            } 
            takeBack(move);
            switchTurn(); 
            return true;
        }
        return false;
    }

    /**
     * Checks to see if a color is stalemated
     * @param turn
     * @return
     */
    public boolean isInStaleMate(GColor turn) {
        if (generateAllLegalMoves(turn).size() == 0 && !isInCheck(turn)) {
            return true;
        }
        return false;
    }

    /**
     * Checks to see if a color is checkmated
     * @param turn
     * @return
     */
    public boolean isInCheckMate(GColor turn) {
        if (generateAllLegalMoves(turn).size() == 0 && isInCheck(turn)) {
            return true;
        }
        return false;
    }

    /**
     * Goes through the enemy piece locations and sees if they can move to the king's square
     * If a piece can "take" the king return true otherwise return false
     */ 
    public boolean isInCheck(GColor turn) {
        if (canAnyPieceMove(getKingLocation(turn), (turn == GColor.WHITE) ? GColor.BLACK: GColor.WHITE)) {
            return true;
        }
        return false;
    }

    /**
     * Checks to see whether one of the opponents pieces can move to the square
     * @param endSquare
     * @param opponentsColor
     * @return if an opponent piece can move to the square
     */
    public boolean canAnyPieceMove(Square endSquare, GColor opponentsColor) {
        GColor originalTurn = turn;
        ArrayList<Square> pieceLocations = findAllPiecesOfColor(opponentsColor);
        for (Square square : pieceLocations) {
            Piece piece = square.getPiece();
            if (opponentsColor != turn) {
                switchTurn();
            }
            if (piece.canMove(this, new Move(square, endSquare))) {
                turn = originalTurn;
                return true;
            }
        }
        turn = originalTurn;
        return false;
    }

    /**
     * Finds all squares the pieces of a speicified color are on
     * @param color
     * @return Square arrayList
     */
    public ArrayList<Square> findAllPiecesOfColor(GColor color) {
        ArrayList<Square> pieces = new ArrayList<Square>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!board[i][j].getPiece().getName().equals("Empty") && board[i][j].getPiece().getColor() == color) {
                    pieces.add(board[i][j]);
                }
            }
        }
        return pieces;
    }

    /**
     * generates all legal moves given a color
     * @param color
     * @return ArrayList of Moves
     */
    /*public ArrayList<Move> generateAllLegalMoves(Color color) {
        ArrayList<Move> legalMoves = new ArrayList<Move>();
        ArrayList<Square> pieces = new ArrayList<Square>();
        for (Square s: pieceLocation.get(color)) {
            pieces.add(s);
        }
        for (Square square: pieces) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Move move = new Move(square, board[i][j]);
                    if (canMove(move)) {
                        legalMoves.add(move);
                    }
                }
            }
        }
        System.out.println("Size of moves" + legalMoves.size());
        return legalMoves;
    } */

    /**
     * generates all legal moves given a color
     * @param color
     * @return ArrayList of Moves
     */
    public ArrayList<Move> generateAllLegalMoves(GColor color) {
        ArrayList<Move> legalMoves = new ArrayList<Move>();
        ArrayList<Square> pieces = findAllPiecesOfColor(color);
        for (Square square: pieces) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Move move = new Move(square, board[i][j]);
                    if (canMove(move)) {
                        legalMoves.add(move);
                    }
                }
            }
        }
        return legalMoves;
    }

    public void readFEN(String FEN) {
        int rank = 7;
        int file = 0;
        for (int i = 0; i < FEN.length(); i++) {
            char c = FEN.charAt(i);
            if (!(rank <= 0 && file > 7)) {
                switch (c) {
                    case 'p':
                        board[rank][file] = new Square("" + rank + file, new Pawn(GColor.BLACK));
                        file++;
                        break;
                    case 'P':
                        board[rank][file] = new Square("" + rank + file, new Pawn(GColor.WHITE));
                        file++;
                        break;
                    case 'n':
                        board[rank][file] = new Square("" + rank + file, new Knight(GColor.BLACK));
                        file++;
                        break;
                    case 'N':
                        board[rank][file] = new Square("" + rank + file, new Knight(GColor.WHITE));
                        file++;
                        break;
                    case 'b':
                        board[rank][file] = new Square("" + rank + file, new Bishop(GColor.BLACK));
                        file++;
                        break;
                    case 'B':
                        board[rank][file] = new Square("" + rank + file, new Bishop(GColor.WHITE));
                        file++;
                        break;
                    case 'r':
                        board[rank][file] = new Square("" + rank + file, new Rook(GColor.BLACK));
                        file++;
                        break;
                    case 'R':
                        board[rank][file] = new Square("" + rank + file, new Rook(GColor.WHITE));
                        file++;
                        break;
                    case 'q':
                        board[rank][file] = new Square("" + rank + file, new Queen(GColor.BLACK));
                        file++;
                        break;
                    case 'Q':
                        board[rank][file] = new Square("" + rank + file, new Queen(GColor.WHITE));
                        file++;
                        break;
                    case 'k':
                        board[rank][file] = new Square("" + rank + file, new King(GColor.BLACK));
                        file++;
                        break;
                    case 'K':
                        board[rank][file] = new Square("" + rank + file, new King(GColor.WHITE));
                        file++;
                        break;
                    case '/':
                        file = 0;
                        rank--;
                        break;
                    default:
                        int num = Integer.parseInt("" + c);
                        for (int x = 0; x < num; x++) {
                            board[rank][file] = new Square("" + rank + file, new Empty());
                            file++;
                        }
                }
            }
            else {
                if (c == 'b') {
                    turn = GColor.BLACK;
                }
                if (c == 'w') {
                    turn = GColor.BLACK;
                }
            }
        }
    }

    /**
     * Resets the board to the starting position.
     */
    public void resetBoard(){
        turn = GColor.WHITE;
        board = new Square[8][8];
        for(int i = 1; i < 6; i++){
            for(int j = 0; j < 8; j++){
                board[i][j] = new Square(Conversions.arrayIndicesToCoordinates("" + i + j), new Empty());
            }
        }
        for(int i = 0; i < 8; i++){
            //sets up pawns and piecelocations
            board[1][i] = new Square(Conversions.arrayIndicesToCoordinates("" + 1 + i),new Pawn(GColor.WHITE));
            //pieceLocation.get(Color.WHITE).add(board[1][i]);
            board[6][i] = new Square(Conversions.arrayIndicesToCoordinates("" + 6 + i),new Pawn(GColor.BLACK));
            //pieceLocation.get(Color.BLACK).add(board[6][i]);
        }
        //sets up pieces
        board[0][0] = new Square("a1", new Rook(GColor.WHITE));
        board[0][7] = new Square("h1", new Rook(GColor.WHITE));
        board[7][0] = new Square("a8", new Rook(GColor.BLACK));
        board[7][7] = new Square("h8", new Rook(GColor.BLACK));
        board[0][1] = new Square("b1", new Knight(GColor.WHITE));
        board[0][6] = new Square("g1", new Knight(GColor.WHITE));
        board[7][1] = new Square("b8", new Knight(GColor.BLACK));
        board[7][6] = new Square("g8", new Knight(GColor.BLACK));
        board[0][2] = new Square("c1", new Bishop(GColor.WHITE));
        board[0][5] = new Square("f1", new Bishop(GColor.WHITE));
        board[7][2] = new Square("c8", new Bishop(GColor.BLACK));
        board[7][5] = new Square("f8", new Bishop(GColor.BLACK));
        board[0][4] = new Square("e1", new King(GColor.WHITE));
        board[7][4] = new Square("e8", new King(GColor.BLACK));
        board[0][3] = new Square("d1", new Queen(GColor.WHITE));
        board[7][3] = new Square("d8", new Queen(GColor.BLACK));
        for (int i = 0; i < 8; i++) {
            //setting up piece locations
            //pieceLocation.get(Color.WHITE).add(board[0][i]);
            //pieceLocation.get(Color.BLACK).add(board[7][i]);
        }
        setAdjacentSquares();
    }

    /**
     * Prints the board from the perspective of who's turn it is.
     */

    public void printBoard(){
        if(turn == GColor.WHITE){
            System.out.println("- - - - - - - B L A C K - - - - - - -");
            System.out.println("   a   b   c   d   e   f   g   h");
            for(int i = 7; i >= 0; i--){
                System.out.print((i + 1) + " ");
                for(int j = 0; j < 8; j++){
                    if(board[i][j].getPiece() == null){
                        System.out.print(i);
                        System.out.print(j);
                    }
                    System.out.print(board[i][j].getPiece().getSymbol());
                }
                System.out.println(" " + (i + 1));
            }
            System.out.println("   a   b   c   d   e   f   g   h");
            System.out.println("- - - - - - - W H I T E - - - - - - -");
        }
        else if(turn == GColor.BLACK){
            System.out.println("- - - - - - - W H I T E - - - - - - -");
            System.out.println("   h   g   f   e   d   c   b   a");
            for(int i = 0; i < 8; i++){
                System.out.print(i + 1 + " ");
                for(int j = 7; j >= 0; j--){
                    if(board[i][j] == null){
                        System.out.print(i);
                        System.out.print(j);
                    }
                    System.out.print(board[i][j].getPiece().getSymbol());
                }
                System.out.println(" " + (i + 1));
            }
            System.out.println("   h   g   f   e   d   c   b   a");
            System.out.println("- - - - - - - B L A C K - - - - - - -");
        }
        else {
            System.out.println("COLOR NOT WHITE OR BLACK WHAT ARE YOU DOING");
        }
    }

    //for finding king im not going to make a find king method because 
    //it will loop a lot. INSTEAD I'm going to update the locations
    // of the kings

    /**
     * returns the 2d array board (probably not going to be useful)
     * @return board
     */
    public Square[][] getBoard() {
        return board;
    }
    
    /**
     * returns the piece locations array list containing all the pieces a color has
     * @param color
     * @return ArrayList of pieceLocations
     */
    /*public ArrayList<Square> getPieceLocations(Color color) {
        return pieceLocation.get(color);
    } */

    /*public void updatePieceLocation(Square startSquare, Square endSquare) {
        Color color = endSquare.getPiece().getColor();
        pieceLocation.get(color).set(pieceLocation.get(color).indexOf(startSquare), endSquare);
    } */
    
    /**
     * Returns the King of the specific color location
     * @param color
     * @return Square the king is on
     */
    public Square getKingLocation(GColor color) {
        ArrayList<Square> pieceLocation = findAllPiecesOfColor(color);
        for (Square square : pieceLocation) {
            if (square.getPiece().getName().equals("King")) {
                return square;
            }
        }
        return null;
    }

    public void debugger(String s) {
        if (debug) {
            System.out.println(s);
        }
    }

    /*public HashMap<Color,ArrayList<Square>> getPieceLocations() {
        return pieceLocation;
    } */

    /**
     * Checks to see if two all squares on the board are equal
     * @param board
     * @return boolean
     */
    public boolean equals(Board board) {
        Square[][] boardArray = board.getBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!this.board[i][j].equals(boardArray[i][j])) {
                    System.out.println(this.board[i][j].getInfo() + " is not equal to " + boardArray[i][j].getInfo());
                    return false;
                }
            }
        }
        return true;
    }
    public static void main(String[]args) {
        Board b = new Board();
        b.printBoard();
        System.out.println(b.generateAllLegalMoves(GColor.WHITE));
     

 
    }
}
