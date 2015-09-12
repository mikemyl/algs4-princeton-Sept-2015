import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/*----------------------------------------------------------------
 *  Author:        Mike Milonakis
 *  Written:       09/09/2015
 *  Last updated:  09/09/2015
 *
 *  Compilation:   javac Percolation.java 
 *  Execution:     java Perolation.class < input.txt
 *  
 *  (Needs to have algs4.jar in classpath)
 *  
 *  This is the first programming assignment of 
 *  princeton - algorithms course in coursera
 *
 *----------------------------------------------------------------*/

/**
 * The Percolation class models a N-by-N percolation system
 * 
 * @author Mike Milonakis
 */
public class Percolation {

    private final int size;
    private final WeightedQuickUnionUF weightedQuickUF;
    private final boolean[][] grid;

    /**
     * 
     * @param N the size of the N-by-N grid
     */
    public Percolation(int N) {
        if (N <= 0)
            throw new IndexOutOfBoundsException();
        size = N;
        weightedQuickUF = new WeightedQuickUnionUF(N * N + 2);
        grid = new boolean[size][size];
    }

    /**
     * Checks if site (i,j) is open
     * 
     * @param i the row index of the grid (1 - indexed)
     * @param j the column index of the grid (1- indexed)
     * @return True if the site (i,j) is open, false otherwise
     */
    public boolean isOpen(int i, int j) {
        if (!validate(i, j))
            throw new IndexOutOfBoundsException();
        return grid[i - 1][j - 1];
    }

    /**
     * Checks if the site (i,j) is connected to a site at the top
     * 
     * @param i the row index of the grid (1 - indexed)
     * @param j the column index of the grid (1- indexed)
     * @return true if the site is connected to one site in the top row
     *         false otherwise
     */
    public boolean isFull(int i, int j) {
        if (!validate(i, j))
            throw new IndexOutOfBoundsException();
        return weightedQuickUF.connected(xyTo1D(i, j), 0);
    }

    /**
     * Opens the site (i,j)
     * 
     * @param i the row index of the grid (1 - indexed)
     * @param j the column index of the grid (1- indexed)
     */
    public void open(int i, int j) {
        if (!validate(i, j))
            throw new IndexOutOfBoundsException();
        openGrid(i, j);
        linkWithOpenNeighbors(i, j);
    }

    public boolean percolates() {
        return weightedQuickUF.connected(0, size * size + 1);
    }

    private void openGrid(int i, int j) {
        grid[i - 1][j - 1] = true;
    }

    private void linkWithOpenNeighbors(int i, int j) {
        if (validate(i - 1, j)) {
            if (isOpen(i - 1, j))
                weightedQuickUF.union(xyTo1D(i, j), xyTo1D(i - 1, j));
        } else {
            weightedQuickUF.union(xyTo1D(i, j), 0);
        }
        if (validate(i + 1, j)) {
            if (isOpen(i + 1, j))
                weightedQuickUF.union(xyTo1D(i, j), xyTo1D(i + 1, j));
        } else {
            weightedQuickUF.union(xyTo1D(i, j), size * size + 1);
        }
        if (validate(i, j - 1))
            if (isOpen(i, j - 1))
                weightedQuickUF.union(xyTo1D(i, j), xyTo1D(i, j - 1));
        if (validate(i, j + 1))
            if (isOpen(i, j + 1))
                weightedQuickUF.union(xyTo1D(i, j), xyTo1D(i, j + 1));
    }

    private int xyTo1D(int x, int y) {
        return ((x - 1) * size + y);
    }

    private boolean validate(int x, int y) {
        return (validate(x) && validate(y));
    }

    private boolean validate(int n) {
        if (n <= 0 || n > size)
            return false;
        return true;
    }

    public static void main(String[] args) {
        int N = StdIn.readInt();
        Percolation percolation = new Percolation(N);
        while (!StdIn.isEmpty()) {
            int i = StdIn.readInt();
            int j = StdIn.readInt();
            if (percolation.isOpen(i, j))
                continue;
            percolation.open(i, j);
            StdOut.println(i + "  " + j);
        }
        StdOut.println("Percolates? : " + percolation.percolates());
    }

}
