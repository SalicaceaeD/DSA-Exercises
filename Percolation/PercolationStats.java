import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private int n;
    private int t;
    private double[] fractions;

    /**
     * perform independent trials on an n-by-n grid.
     * @param n size of the grid
     * @param trials number of trials
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        this.n = n;
        this.t = trials;
        fractions = new double[trials];
        for (int i = 0; i < t; ++i)
            handle(i);
    }

    private void handle(int time) {
        Percolation p = new Percolation(n);
        while (!p.percolates()) {
            int row = StdRandom.uniformInt(1, n+1);
            int col = StdRandom.uniformInt(1, n+1);
            p.open(row, col);
        }
        addToFractions(time, 1.0 * p.numberOfOpenSites() / (n * n));
    }

    /**
     * add value val to array fractions at index id
     * @param id index
     * @param val value
     */
    private void addToFractions(int id, double val) {
        fractions[id] = val;
    }

    /**
     * sample mean of percolation threshold.
     * @return sample mean of percolation threshold
     */
    public double mean() {
        return StdStats.mean(fractions);
    }

    /**
     * sample standard deviation of percolation threshold.
     * @return sample standard deviation of percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(fractions);
    }

    /**
     * low endpoint of 95% confidence interval.
     * @return low endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(t);
    }

    /**
     * high endpoint of 95% confidence interval.
     * @return high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(t);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats test = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + test.mean());
        StdOut.println("stddev                  = " + test.stddev());
        StdOut.println("95% confidence interval = [" + test.confidenceLo() + ", " + test.confidenceHi() + "]");
    }

}