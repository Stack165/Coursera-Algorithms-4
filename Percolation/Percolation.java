import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final byte[][] grid;
    private final int n;
    private final int ncn; // n*n
    private final WeightedQuickUnionUF topUF;
    private final WeightedQuickUnionUF noBottomUF;
    private int openedSite;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        this.n = n;
        ncn = n * n;
        grid = new byte[n][n];
        topUF = new WeightedQuickUnionUF(ncn + 2);
        noBottomUF = new WeightedQuickUnionUF(ncn + 2);
        openedSite = 0;
    }

    private void validate(int i, int j) {
        if (i <= 0 || i > n || j <= 0 || j > n) {
            throw new IllegalArgumentException("index out of bound");
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        int p = (row - 1) * n + col;
        grid[row - 1][col - 1] = 1;
        openedSite++;
        //顶部虚site：0，底部虚site：ncn
        if (row == 1) {
            topUF.union(col, 0);
            noBottomUF.union(col, 0);
        }
        if (row == n) {
            topUF.union((n - 1) * n + col, ncn + 1);
        }
        //上方
        if (row - 1 > 0 && isOpen(row - 1, col)) {
            topUF.union(p, p - n);
            noBottomUF.union(p, p - n);
        }
        //下方
        if (row < n && isOpen(row + 1, col)) {
            topUF.union(p, p + n);
            noBottomUF.union(p, p + n);
        }
        //左方
        if (col - 1 > 0 && isOpen(row, col - 1)) {
            topUF.union(p, p - 1);
            noBottomUF.union(p, p - 1);
        }
        //右方
        if (col < n && isOpen(row, col + 1)) {
            topUF.union(p, p + 1);
            noBottomUF.union(p, p + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row - 1][col - 1] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isOpen(row, col)) {
            return false;
        }
        return isOpen(row, col) && topUF.find(0) == topUF.find((row - 1) * n + col)
                && noBottomUF.find(0) == noBottomUF.find((row - 1) * n + col);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openedSite;
    }

    // does the system percolate?
    public boolean percolates() {
        return topUF.find(0) == topUF.find(ncn + 1);
    }

}