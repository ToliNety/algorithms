/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Percolation {
    final int[] data;
    final int n;
    int numOpenSites = 0;
    static final int top = 0;
    static int bottom;


    public Percolation(int n)              // create n-by-n grid, with all sites blocked
    {
        if (n <= 0)
            throw new IllegalArgumentException();

        this.n = n;

        bottom = n * n + 1;

        data = new int[bottom + 1];
        data[0] = 0;
        data[bottom] = bottom;
        for (int i = 1; i < bottom; i++) {
            data[i] = -1;
        }
    }

    public void open(int row, int col)    // open site (row, col) if it is not open already
    {
        if (row > n || col > n || row <= 0 || col <= 0)
            throw new IllegalArgumentException();

        if (isOpen(row, col))
            return;

        numOpenSites++;
        data[getInx(row, col)] = getInx(row, col);
        quickUnion(row, col);

    }

    private int getInx(int row, int col) {
        return (row - 1) * n + col;
    }

    private void quickUnion(int row, int col) {
        //left
        if ((col - 1) > 0 && isOpen(row, col - 1))
            union(getInx(row, col - 1), getInx(row, col));
        //top
        if (row - 1 == top)
            data[getInx(row, col)] = top;
        else if (row - 1 > 0 && isOpen(row - 1, col)){
            union(getInx(row - 1, col), getInx(row, col));
        }

        //right
        if (col + 1 <= n && isOpen(row, col + 1))
            union(getInx(row, col + 1), getInx(row, col));

        //bottom
        // if (row == n)
        //     data[getInx(row, col)] = bottom;
        else if (row < n && isOpen(row + 1, col))
            union(getInx(row + 1, col), getInx(row, col));
    }

    private void union(int inx1, int inx2) {
        int rootInx1 = root(inx1);
        int rootInx2 = root(inx2);
        if (rootInx1 != rootInx2) {
            if (rootInx1 == top){
                data[rootInx2] = top;
            } else if (rootInx2 == top){
                data[rootInx1] = top;
            // } else if (rootInx1 == bottom){
            //     data[rootInx2] = bottom;
            // } else if (rootInx2 == bottom){
            //     data[rootInx1] = bottom;
            } else {
                data[rootInx1] = rootInx2;
            }

        }
    }

    private boolean isConnected(int inx1, int inx2) {
        return root(data[inx1]) == root(data[inx2]);
    }

    private int root(int value) {
        if (value < 0)
            return value;
        else if (data[value] == value)
            return value;
        else
            return root(data[value]);
    }

    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        if (row > n || col > n || row <= 0 || col <= 0)
            throw new IllegalArgumentException();

        return data[getInx(row, col)] != -1;
    }

    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        if (row > n || col > n || row <= 0 || col <= 0)
            throw new IllegalArgumentException();

        return root(data[getInx(row, col)]) == top;
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
        //return root(bottom) == top;
    }

    public static void main(String[] args)   // test client (optional)
    {

    }
}
