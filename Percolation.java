import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int ROOT = 0;
    private int n; // size of the grid
    private WeightedQuickUnionUF wTop;
    private WeightedQuickUnionUF wBot;
    private boolean[] stat;
    private int count;
    private boolean percolated;
    /**
     * creates n-by-n grid, with all sites initially blocked.
     * @param n size of the grid
     */
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.n = n;
        wTop = new WeightedQuickUnionUF(n*n+1);
        wBot = new WeightedQuickUnionUF(n*n+1);
        stat = new boolean[n * n + 1];
        count = 0;
        percolated = false;
    }

    /**
     * check if row index and col index are relevent.
     * @param row row index
     * @param col col index
     * @return true if relevant, else false
     */
    private boolean relevant(int row, int col) {
        return row >= 1 && row <= n && col >= 1 && col <= n;
    }

    /**
     * map from 2D to 1D indices.
     * @param row row index
     * @param col col index
     * @return index in 1D map
     */
    private int to1D(int row, int col) {
        return (row - 1) * n + col;
    }

    private void connect(int id1, int id2) {
        wTop.union(id1, id2);
        wBot.union(id1, id2);
    }
    /**
     * opens the site (row, col) if it is not open already.
     * @param row row index
     * @param col column index
     */
    public void open(int row, int col) {
        if (!relevant(row, col))
            throw new IllegalArgumentException();
        if (isOpen(row, col))
            return;
        int id = to1D(row, col);
        ++count;
        stat[id] = true;
        if (row == n) wBot.union(ROOT, id);
        if (row == 1) wTop.union(ROOT, id);
        if (relevant(row + 1, col) && isOpen(row + 1, col))
            connect(id, to1D(row + 1, col));
        if (relevant(row - 1, col) && isOpen(row - 1, col))
            connect(id, to1D(row - 1, col));
        if (relevant(row, col + 1) && isOpen(row, col + 1))
            connect(id, to1D(row, col + 1));
        if (relevant(row, col - 1) && isOpen(row, col - 1))
            connect(id, to1D(row, col - 1));
        if (wTop.find(id) == wTop.find(ROOT) && wBot.find(id) == wBot.find(ROOT))
            percolated = true;
    }

    /**
     * is the site (row, col) open?.
     * @param row row index
     * @param col column index
     * @return true if the site is open, else false
     */
    public boolean isOpen(int row, int col) {
        if (!relevant(row, col))
            throw new IllegalArgumentException();
        return stat[to1D(row, col)];
    }

    /**
     * is the site (row, col) full?.
     * @param row row index
     * @param col column index
     * @return true if the site is full, else false
     */
    public boolean isFull(int row, int col) {
        if (!relevant(row, col))
            throw new IllegalArgumentException();
        return wTop.find(ROOT) == wTop.find(to1D(row, col));
    }

    /**
     * count the number of open sites.
     * @return the number of open sites
     */
    public int numberOfOpenSites() {
        return count;
    }

    /**
     * does the system percolate?.
     * @return true if the system percolates, else false
     */
    public boolean percolates() {
        return percolated;
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}