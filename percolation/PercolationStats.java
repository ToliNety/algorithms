/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private double[] means;
    private int trials;

    public PercolationStats(int n, int trials)
    // perform trials independent experiments on an n-by-n grid
    {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        this.trials = trials;

        means = new double[trials];
        for (int trial = 0; trial < trials; trial++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int i = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);
                perc.open(i, j);
            }
            means[trial] = (perc.numberOfOpenSites() * 1.0) / (n * n);
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
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        // repeatedly read in sites to open and draw resulting system
        if (args.length > 0 && args.length % 2 == 0) {
            for (int i = 0; i < args.length; i++) {
                int n = Integer.parseInt(args[i]);
                int T = Integer.parseInt(args[++i]);

                Stopwatch stopwatch = new Stopwatch();
                run(n, T);
                System.out.println("Ex time: " + stopwatch.elapsedTime());
            }

        }
    }

    private static void run(int n, int t) {
        PercolationStats stats = new PercolationStats(n, t);
        StdOut.println("PercolationStats " + n + " " + t);
        StdOut.println("mean = " + stats.mean());
        StdOut.println("stddev = " + stats.stddev());
        System.out.println(
                "95% confidence interval = [" + stats.confidenceLo() + "," + stats.confidenceHi()
                        + "]");
    }
}
