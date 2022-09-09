import edu.princeton.cs.algs4.*;

import java.util.ArrayList;

public class PercolationStats {
    private int N;
    private int T;
    private ArrayList<Double> fractions = new ArrayList<>();

    public void addToFractions(double val) {
        fractions.add(val);
    }
    /**
     * perform independent trials on an n-by-n grid.
     * @param n size of the grid
     * @param trials number of trials
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        this.N = n;
        this.T = trials;
    }

    /**
     * sample mean of percolation threshold.
     * @return sample mean of percolation threshold
     */
    public double mean() {
        double res = 0;
        for (double num : fractions)
            res += num;
        return res / T;
    }

    /**
     * sample standard deviation of percolation threshold.
     * @return sample standard deviation of percolation threshold
     */
    public double stddev() {
        double meanVal = mean();
        double res = 0;
        for (double num : fractions)
            res += (meanVal - num) * (meanVal - num);
        return res / (T - 1);
    }

    /**
     * low endpoint of 95% confidence interval.
     * @return low endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    /**
     * high endpoint of 95% confidence interval.
     * @return high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats test = new PercolationStats(n, trials);
        for (int time = 0; time < trials; ++time) {
            Percolation p = new Percolation(test.N);
            while (!p.percolates()) {
                int row = StdRandom.uniformInt(1, n+1);
                int col = StdRandom.uniformInt(1, n+1);
                p.open(row, col);
            }
            test.addToFractions(1.0 * p.numberOfOpenSites() / (n * n));
        }
        StdOut.println("mean                    = " + test.mean());
        StdOut.println("stddev                  = " + test.stddev());
        StdOut.println("95% confidence interval = [" + test.confidenceLo() + ", " + test.confidenceHi() + "]");
    }

}