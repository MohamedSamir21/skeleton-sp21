package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.
        changed = moveTilesInDirection(side);
        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /**
     * Moves tiles in the side direction
     * @param side the side you want to move the tiles to
     * @return whether any tile is moved
     */
    private boolean moveTilesInDirection(Side side) {
        boolean changed;
        board.setViewingPerspective(side);
        changed = moveTilesUp();
        board.setViewingPerspective(Side.NORTH);
        return changed;
    }

    /**
     * Moves the tiles up
     * @return whether there's at least one tile that's moved up.
     */
    private boolean moveTilesUp() {
        boolean changed = false;
        for (int c = 0; c < board.size(); c++) {
            int untilRow = board.size() - 1; // To avoid merging again the tile that's previously merged in the same column.
            for (int r = board.size() - 1; r >= 0; r--) {
                Tile t = board.tile(c, r);
                if (t == null) { // Skip moving space place.
                    continue;
                }
                int rowIndex = findValidRowIndexForColumn(c, r + 1, untilRow,t);
                if (rowIndex != -1) { // We could move the tile to a valid place
                    boolean wasMerge = board.move(c, rowIndex, t);
                    if (wasMerge) {
                        score += board.tile(c, rowIndex).value();
                        untilRow--;
                    }
                    changed = true;
                }
            }
        }
        return changed;
    }

    /**
     * Finds a valid row index in range fromRow to toRow (Inclusive), given the col.
     * @param col the index of the column to search within.
     * @param fromRow the index of the first row to start looking at.
     * @param toRow the index of the last row to end looking at (inclusive).
     * @param t the tile to find valid index for.
     * @return a valid row index in the range fromRow to toRow. Otherwise, returns -1 indicating that you can't move.
     */
    private int findValidRowIndexForColumn(int col, int fromRow, int toRow, Tile t) {
        int validRowIndex = -1;
        for (int i = fromRow; i <= toRow; i++) {
            Tile curr = board.tile(col, i);
            if (curr == null) { // Possible space to move tile to
                validRowIndex = i;
                continue;
            }
            if (curr.value() == t.value()) { // There's a merge
                return i;
            } else { // We don't need to keep searching, we touched a blocking tile that can't be merged!
                break;
            }
        }
        return validRowIndex;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        for (int col = 0; col < b.size(); col++) {
            for (int row = 0; row < b.size(); row++) {
                if (b.tile(col, row) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        for (int col = 0; col < b.size(); col++) {
            for (int row = 0; row < b.size(); row++) {
                Tile tile = b.tile(col, row);
                if (tile == null) {
                    continue;
                }
                if (tile.value() == MAX_PIECE) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        return emptySpaceExists(b) || canMerge(b);
    }

    private static boolean canMerge(Board b) {
        for (int col = 0; col < b.size(); col++) {
            for (int row = 0; row < b.size(); row++) {
                Tile currentTile = b.tile(col, row);
                int leftCol = col - 1, rightCol = col + 1;
                int aboveRow = row - 1, downRow = row + 1;
                if (canMergeToLeft(b, row, leftCol, currentTile)
                       || canMergeToRight(b, row, rightCol, currentTile)
                       || canMergeToAbove(b, col, aboveRow, currentTile)
                       || canMergeToDown(b, col, downRow, currentTile)
                ) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean canMergeToDown(Board b, int col, int downRow, Tile currentTile) {
        if (downRow < b.size()) {
            Tile downTile = b.tile(col, downRow);
            return currentTile.value() == downTile.value();
        }
        return false;
    }

    private static boolean canMergeToAbove(Board b, int col, int aboveRow, Tile currentTile) {
        if (aboveRow >= 0) {
            Tile aboveTile = b.tile(col, aboveRow);
            return currentTile.value() == aboveTile.value();
        }
        return false;
    }

    private static boolean canMergeToRight(Board b, int row, int rightCol, Tile currentTile) {
        if (rightCol < b.size()) {
            Tile rightTile = b.tile(rightCol, row);
            return currentTile.value() == rightTile.value();
        }
        return false;
    }

    private static boolean canMergeToLeft(Board b, int row, int leftCol, Tile currentTile) {
        if (leftCol >= 0) {
            Tile leftTile = b.tile(leftCol, row);
            return currentTile.value() == leftTile.value();
        }
        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
