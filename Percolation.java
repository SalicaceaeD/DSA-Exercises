import edu.princeton.cs.algs4.*;

public class Percolation {
    private int N; // size of the grid
    private WeightedQuickUnionUF opened;
    private WeightedQuickUnionUF connected;
    private int top;
    private int bot;
    /**
     * creates n-by-n grid, with all sites initially blocked.
     * @param n size of the grid
     */
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.N = n;
        opened = new WeightedQuickUnionUF(n*n+1);
        connected = new WeightedQuickUnionUF(n*n+2);
        this.top = 0;
        this.bot = n*n+1;
    }

    /**
     * opens the site (row, col) if it is not open already.
     * @param row row index
     * @param col column index
     */
    public void open(int row, int col) {
        if (row < 1 || row > N || col < 1 || col > N)
            throw new IllegalArgumentException();
        if (isOpen(row, col))
            return;
        int id = (row - 1) * N + col;
        opened.union(top, id);
        if (row == 1) connected.union(top, id);
        if (row == N) connected.union(bot, id);
        int[] X = {-1, 0, 0, 1};
        int[] Y = {0, -1, 1, 0};
        for (int i = 0; i < 4; ++i) {
            if (row + X[i] < 1 || row + X[i] > N ||
                col + Y[i] < 1 || col + Y[i] > N) {
                continue;
            }
            if (isOpen(row + X[i], col + Y[i])) {
                int neighbour = (row + X[i] - 1) * N + (col + Y[i]);
                connected.union(id, neighbour);
            }
        }
    }

    /**
     * is the site (row, col) open?.
     * @param row row index
     * @param col column index
     * @return true if the site is open, else false
     */
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > N || col < 1 || col > N)
            throw new IllegalArgumentException();
        int id = (row - 1) * N + col;
        return opened.find(top) == opened.find(id);
    }

    /**
     * is the site (row, col) full?.
     * @param row row index
     * @param col column index
     * @return true if the site is full, else false
     */
    public boolean isFull(int row, int col) {
        if (row < 1 || row > N || col < 1 || col > N)
            throw new IllegalArgumentException();
        int id = (row - 1) * N + col;
        return connected.find(top) == connected.find(id);
    }

    /**
     * count the number of open sites.
     * @return the number of open sites
     */
    public int numberOfOpenSites() {
        return N * N + 1 - opened.count();
    }

    /**
     * does the system percolate?.
     * @return true if the system percolates, else false
     */
    public boolean percolates() {
        return connected.find(top) == connected.find(bot);
    }

    // test client (optional)
    public static void main(String[] args) {}
}