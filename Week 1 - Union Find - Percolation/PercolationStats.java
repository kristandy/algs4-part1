// /* *****************************************************************************
//  *  Name:              Aryagara Kristandy
//  *  Coursera User ID:  aryagarakristandi@gmail.com
//  *  Last modified:     January 27, 2024
//  **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] result;
    private int t;
    private static final double CONFIDENCE_95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        t = trials;
        result = new double[t];
        for (int i = 0; i < t; i++) {
            Percolation percolation = new Percolation(n);
            int opened = 0;
            while (!percolation.percolates()) {
                int x = StdRandom.uniformInt(1, n + 1);
                int y = StdRandom.uniformInt(1, n + 1);
                if (!percolation.isOpen(x, y)) {
                    percolation.open(x, y);
                    opened++;
                }
            }
            result[i] = (double) opened / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(result);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(result);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((CONFIDENCE_95 * stddev()) / Math.sqrt(t));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((CONFIDENCE_95 * stddev()) / Math.sqrt(t));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trails = Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(n, trails);
        StdOut.println("mean = " + percStats.mean());
        StdOut.println("stddev = " + percStats.stddev());
        StdOut.println("95% confidence interval = [" + percStats.confidenceLo() + ", "
                               + percStats.confidenceHi() + "]");
    }
}



