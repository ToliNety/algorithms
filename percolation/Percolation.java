/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    final WeightedQuickUnionUF uf;

    final int[] openSites;
    final int n;
    int numOpenSites = 0;
    static final int top = 0;


    public Percolation(int n)              // create n-by-n grid, with all sites blocked
    {
        if (n <= 0)
            throw new IllegalArgumentException();

        this.n = n;

        int size = n * n + 2;
        uf = new WeightedQuickUnionUF(size);

        openSites = new int[size];
        for (int i = 0; i < size; i++) {
            openSites[i] = 0;
        }

        for (int i = 1; i <= n; i++) {
            uf.union(0 , i);
        }

    }

    public void open(int row, int col)    // open site (row, col) if it is not open already
    {
        validate(row, col);

        if (isOpen(row, col))
            return;

        numOpenSites++;
        openSites[getInx(row, col)] = 1;

        //left
        if ((col - 1) > 0 && isOpen(row, col - 1))
            uf.union(getInx(row, col - 1), getInx(row, col));
        //top
        if (row > 1 && isOpen(row - 1, col)){
            uf.union(getInx(row - 1, col), getInx(row, col));
        }

        //right
        if (col + 1 <= n && isOpen(row, col + 1))
            uf.union(getInx(row, col + 1), getInx(row, col));

        //bottom
        if (row < n && isOpen(row + 1, col))
            uf.union(getInx(row + 1, col), getInx(row, col));

    }

    private void validate(int row, int col) {
        if (row > n || col > n || row <= 0 || col <= 0)
            throw new IllegalArgumentException();
    }

    private int getInx(int row, int col) {
        return (row - 1) * n + col;
    }

    private boolean isConnected(int inx1, int inx2) {
        return uf.find(inx1) == uf.find(inx2);
    }

    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        validate(row, col);
        return openSites[getInx(row, col)] == 1;
    }

    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        validate(row, col);
        return isConnected(top, getInx(row, col)) && isOpen(row, col);
    }

    public int numberOfOpenSites()       // number of open sites
    {
        return numOpenSites;
    }

    public boolean percolates()              // does the system percolate?
    {
        boolean result = false;
        for (int i = 1; i <= n && !result; i++) {
            result = isConnected(top, getInx(n, i));
        }
        return result;
    }

    public static void main(String[] args)   // test client (optional)
    {

    }
}
