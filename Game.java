package RetrogradeChess;

import java.util.Scanner;

import java.util.ArrayList;

import RetrogradeChess.Pieces.*;


public class Game{

    public Board board;
	public GColor turn;
    public ArrayList<Move> moves;
    private boolean debug = true;
    

    public Game() {
        newGame();
    }

    /**
     * Sets up the game
     */
    public void newGame() {
        board = new Board();
        turn = GColor.WHITE;
        moves = new ArrayList<Move>();
    }


    /**
     * Plays the game 
     * Uses the Scanner class to get input and converts the input to a move8
     */
    public void playGame() {
        Scanner white = new Scanner(System.in);
        Scanner black = new Scanner(System.in);
        board.printBoard();
        while (true) {

            //White's move
            System.out.print("White Move: ");
            String whiteMove = white.nextLine();

            if (board.canMove(inputToMove(whiteMove))) {
                board.move(inputToMove(whiteMove));
            }
            else {
                while (!board.canMove(inputToMove(whiteMove))) {
                    System.out.print("Illegal move try again: ");
                    whiteMove = white.nextLine();
                    if (board.canMove(inputToMove(whiteMove))) {
                        board.move(inputToMove(whiteMove));
                        break;
                    }
                }
            }

            board.printBoard();
            turn = GColor.BLACK;

            if (board.isInCheck(turn)) {
                if (board.isInCheckMate(turn)) {
                    System.out.println("White wins!");
                    break;
                }
            }
            else {
                if (board.isInStaleMate(turn)) {
                    System.out.println("Stalemate!");
                    break;
                }
            }

            //Black's move
            System.out.print("Black Move: ");
            String blackMove = white.nextLine();

            if (board.canMove(inputToMove(blackMove))) {
                board.move(inputToMove(blackMove));
            }
            else {
                while (!board.canMove(inputToMove(blackMove))) {
                    System.out.print("Illegal move try again: ");
                    blackMove = black.nextLine();
                    if (board.canMove(inputToMove(blackMove))) {
                        board.move(inputToMove(blackMove));
                        break;
                    }
                }
            }

            board.printBoard();
            turn = GColor.WHITE;

            if (board.isInCheck(turn)) {
                if (board.isInCheckMate(turn)) {
                    System.out.println("Black wins!");
                    break;
                }
            }
            else {
                if (board.isInStaleMate(turn)) {
                    System.out.println("Stalemate!");
                    break;
                }
            }

        }
        white.close();
        black.close();
    }

    /**
     * Translates algebraic notation ex. "Nf3" to the move object associated with it
     * @param string
     * @return Move object
     */
    public Move inputToMove(String string){
        if (string.equals("O-O")){
            if (turn == GColor.WHITE){
                return new Move(board.getSquare("e1"), board.getSquare("g1"));
            }
            else{
                return new Move(board.getSquare("e8"), board.getSquare("g8"));
            }
        }
        if (string.equals("O-O-O")){
            if (turn == GColor.WHITE){
                return new Move(board.getSquare("e1"), board.getSquare("c1"));
            }
            else{
                return new Move(board.getSquare("e8"), board.getSquare("c8"));
            }
        }
        ArrayList<Move> legalMoves = board.generateAllLegalMoves(turn);
        String pieceName = "";
        if (string.charAt(0) == 'a' || string.charAt(0) == 'b' || string.charAt(0) == 'c' || string.charAt(0) == 'd' || string.charAt(0) == 'e' || string.charAt(0) == 'f' || string.charAt(0) == 'g' || string.charAt(0) == 'h'){
            pieceName = "Pawn";
        }
        if (string.charAt(0) == 'N'){
            pieceName = "Knight";
        }
        if (string.charAt(0) == 'B'){
            pieceName = "Bishop";
        }
        if (string.charAt(0) == 'R'){
            pieceName = "Rook";
        }
        if (string.charAt(0) == 'Q'){
            pieceName = "Queen";
        }
        if (string.charAt(0) == 'K'){
            pieceName = "King";
        }
        Square endSquare = board.getSquare(string.substring(string.length() - 2)); //location the piece is moving

        for (Move move : legalMoves) {
            if (!endSquare.equals(move.getEndSquare()) || !pieceName.equals(move.getPiece().getName())) {
                continue;
            }

            if (pieceName.equals("Pawn")) {
                if (move.getStartSquare().getCoordinates().getFile() == string.charAt(0)) {
                    debugger("Move: " + move);
                    return move;
                }
            }
            else {
                if (string.length() == 3) {
                    debugger("Move: " + move);
                    return move;
                }
                if (move.getStartSquare().getCoordinates().getFile() == string.charAt(1) || move.getStartSquare().getCoordinates().getRank() == string.charAt(1)) {
                    debugger("Move: " + move);
                    return move;
                }
            }
        }
        System.out.println("LEGAL MOVES " + board.generateAllLegalMoves(turn));
        System.out.println("uh oh");
        return null;
    }

    public void debugger(Object object) {
        if (debug) {
            System.out.println(object);
        }
    }

    public static void main(String[] args){
        Game testGame = new Game();
        testGame.playGame();
    }
}
    