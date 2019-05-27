/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] means;

    public PercolationStats(int n, int trials)
    // perform trials independent experiments on an n-by-n grid
    {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        means = new double[trials];
        for (int trial = 0; trial < trials; trial++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int i = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);
                perc.open(i, j);
            }
            means[trial] = (perc.numOpenSites * 1.0) / (n * n);
        }

    }

    public double mean()                          // sample mean of percolation threshold
    {
        return StdStats.mean(means);
    }

    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(means);
    }

    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return 0.0;
    }

    public double confidenceHi() {
        return 0.0;
    }

    public static void main(String[] args) {
        // repeatedly read in sites to open and draw resulting system
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, T);
        StdOut.println("PercolationStats " + n + " " + T);
        StdOut.println("mean = " + stats.mean());
        StdOut.println("stddev = " + stats.stddev());
    }
}
