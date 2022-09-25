import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int[][] tiles;

    /**
     * create a board from an n-by-n array of tiles.
     * tiles[row][col] = tile at (row, col).
     */
    public Board(int[][] tiles) {
        int n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < n; ++j)
                this.tiles[i][j] = tiles[i][j];
    }

    /**
     * string representation of this board.
     */
    public String toString() {
        int n = tiles.length;
        StringBuilder s = new StringBuilder();
        s.append(n);
        for (int i = 0; i < n; ++i) {
            s.append("\n");
            for (int j = 0; j < n; ++j) {
                s.append(tiles[i][j] + " ");
            }
        }
        return s.toString();
    }

    /**
     * board dimension n.
     */
    public int dimension() {
        return tiles.length;
    }

    /**
     * number of tiles out of place.
     */
    public int hamming() {
        int hammingDistance = 0;
        int n = tiles.length;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (tiles[i][j] == 0) {
                    continue;
                }
                if (tiles[i][j] != i * n + j + 1) {
                    ++hammingDistance;
                }
            }
        }
        return hammingDistance;
    }

    /**
     * sum of Manhattan distances between tiles and goal.
     */
    public int manhattan() {
        int manhattanDistance = 0;
        int n = tiles.length;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (tiles[i][j] == 0) {
                    continue;
                }
                int x = (tiles[i][j] - 1) / n;
                int y = (tiles[i][j] - 1) % n;
                manhattanDistance += Math.abs(x - i) + Math.abs(y - j);
            }
        }
        return manhattanDistance;
    }

    /**
     * is this board the goal board?.
     */
    public boolean isGoal() {
        int n = tiles.length;
        int expected = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                ++expected;
                if (expected == n * n) {
                    break;
                }
                if (tiles[i][j] != expected) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * does this board equal y?.
     */
    public boolean equals(Object y) {
        if (y == null || !y.getClass().equals(getClass())) {
            return false;
        }
        Board other = (Board) y;
        if (other.dimension() != this.dimension()) {
            return false;
        }
        int n = dimension();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (other.tiles[i][j] != this.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * check if row index and col index are relevant.
     */
    private boolean relevant(int row, int col) {
        return row >= 0 && row < tiles.length && col >= 0 && col < tiles.length;
    }

    private void swap(int[][] board, int row1, int col1, int row2, int col2) {
        int tmp = board[row1][col1];
        board[row1][col1] = board[row2][col2];
        board[row2][col2] = tmp;
    }

    private int findIndexOf0() {
        for (int i = 0; i < tiles.length; ++i) {
            for (int j = 0; j < tiles.length; ++j) {
                if (tiles[i][j] == 0) {
                    return i * tiles.length + j;
                }
            }
        }
        return -1;
    }

    /**
     * all neighboring boards.
     */
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>();
        int ind = findIndexOf0();
        int indx = ind / tiles.length;
        int indy = ind % tiles.length;
        int[] goX = {-1, 1, 0, 0};
        int[] goY = {0, 0, -1, 1};
        int[][] cloneTiles = tiles.clone();
        for (int i = 0; i < goX.length; ++i) {
            if (relevant(indx + goX[i], indy + goY[i])) {
                swap(cloneTiles, indx, indy, indx + goX[i], indy + goY[i]);
                neighbors.add(new Board(cloneTiles));
                swap(cloneTiles, indx, indy, indx + goX[i], indy + goY[i]);
            }
        }
        return neighbors;
    }

    /**
     * a board that is obtained by exchanging any pair of tiles.
     */
    public Board twin() {
        int n = tiles.length;
        int ind = findIndexOf0();
        Board twinBoard;

        for (int id = 0; id < n * n; ++id) {
            if (id != ind) {
                int idx = id / n;
                int idy = id % n;
                int[] goX = {-1, 1, 0, 0};
                int[] goY = {0, 0, -1, 1};
                int[][] cloneTiles = tiles.clone();
                for (int i = 0; i < goX.length; ++i) {
                    int newx = idx + goX[i];
                    int newy = idy + goY[i];
                    if (relevant(newx, newy) && cloneTiles[newx][newy] != 0) {
                        swap(cloneTiles, idx, idy, newx, newy);
                        twinBoard = new Board(cloneTiles);
                        swap(cloneTiles, idx, idy, newx, newy);
                        return twinBoard;
                    }
                }
            }
        }

        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }
}