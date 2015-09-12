import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/*----------------------------------------------------------------
 *  Author:        Mike Milonakis
 *  Written:       11/09/2015
 *  Last updated:  11/09/2015
 *
 *  Compilation:   javac PercolationStats.java 
 *  Execution:     java Perolation.class < input.txt
 *  
 *  (Needs to have algs4.jar in classpath)
 *  
 *  This is the first programming assignment of 
 *  princeton - algorithms course in coursera
 *
 *----------------------------------------------------------------*/

/**
 * The PercolationStats  class estimates the percolation Threshold by
 * executing (T) experiments on N-by-N grid
 * 
 * @author Mike Milonakis
 */

public class PercolationStats {
    private double[] x;
    private double mean;
    private double stddev;

    /**
     * Default Constructor
     * @param N The size of the N-by-N grid
     * @param T The number of the experiments
     */
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException();
    }

    /**
     * Computes the sample mean of percolation threshold
     * @return mean the mean
     */
    public double mean() {
        mean = StdStats.mean(x);
        return mean;
    }

    /**
     * Computes the standard deviation of percolation threshhold
     * 
     * @return stdev the sample standard deviation
     */
    public double stddev() {
        stddev = StdStats.stddev(x);
        return stddev;
    }

    /**
     * Computes the low endpoint of 95% confidence interval
     * 
     * @return the low endpoint
     */
    public double confidenceLo() {
        return mean - (1.96 * stddev / (Math.sqrt(x.length)));
    }

    /**
     * Computes the high endpoint of 95% confidence interval
     * 
     * @return the high endpoint
     */
    public double confidenceHi() {
        return mean + (1.96 * stddev / (Math.sqrt(x.length)));
    }

    public static void main(String[] args) {
        int N = StdIn.readInt();
        int T = StdIn.readInt();
        PercolationStats stats = new PercolationStats(N, T);
        stats.x = new double[T];

        for (int i = 0; i < T; i++) {
            Percolation grid = new Percolation(N);
            int openSites = 0;
            while (!grid.percolates()) {
                int x = StdRandom.uniform(1, N+1);
                int y = StdRandom.uniform(1, N+1);
                if (grid.isOpen(x, y))
                    continue;
                grid.open(x, y);
                openSites++;
            }
            stats.x[i] = (double) openSites / (N*N);
        }
        StdOut.println("mean\t\t\t= " + stats.mean());
        StdOut.println("stddev\t\t\t= " + stats.stddev());
        StdOut.println("95% confidence interval\t= " + stats.confidenceLo()
                + ", " + stats.confidenceHi());
    }
}
