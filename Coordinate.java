package RetrogradeChess;

/**
 * Coordinate on board ex. e4
 */
public class Coordinate {
    private char file;
    private int rank;
    private String coordinate; // file and rank combined

    public Coordinate (char f, int r) {
        file = f;
        rank = r;
        coordinate = "" + f + r;
    }

    public Coordinate (String coordinate) {
        this.coordinate = coordinate;
        file = coordinate.charAt(0);
        rank = Integer.parseInt(coordinate.substring(1));
    }

    public char getFile() {
        return file;
    }

    public int getRank() {
        return rank;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public String toString() {
        return coordinate;
    }
}
