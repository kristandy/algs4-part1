/* *****************************************************************************
 *  Name:              Aryagara Kristandy
 *  Coursera User ID:  aryagarakristandi@gmail.com
 *  Last modified:     January 23, 2024
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] matrix;
    private int sourceTop;
    private int sinkBottom;
    private int matrixSize;
    private int openSiteCount;
    private final WeightedQuickUnionUF quf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Nilai n harus kurang dari atau sama dengan 0!");
        }
        matrixSize = n;
        sinkBottom = matrixSize * matrixSize + 1;
        quf = new WeightedQuickUnionUF(matrixSize * matrixSize + 2);
        matrix = new boolean[matrixSize][matrixSize];
        sourceTop = 0;
        openSiteCount = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (checkIndex(row, col)) {
            if (!isOpen(row, col)) {
                matrix[row - 1][col - 1] = true;
                openSiteCount++;
            }

            if (row == 1) {
                quf.union(convertArray(row, col), sourceTop);
            }

            if (row == matrixSize) {
                quf.union(convertArray(row, col), sinkBottom);
            }

            if (row > 1 && isOpen(row - 1, col)) {
                quf.union(convertArray(row, col), convertArray(row - 1, col));
            }

            if (row < matrixSize && isOpen(row + 1, col)) {
                quf.union(convertArray(row, col), convertArray(row + 1, col));
            }

            if (col > 1 && isOpen(row, col - 1)) {
                quf.union(convertArray(row, col), convertArray(row, col - 1));
            }

            if (col < matrixSize && isOpen(row, col + 1)) {
                quf.union(convertArray(row, col), convertArray(row, col + 1));
            }
        }
        throw new IllegalArgumentException();
    }

    private int convertArray(int row, int col) {
        return matrixSize * (row - 1) + col;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (checkIndex(row, col)) {
            checkIndex(row, col);
            return matrix[row - 1][col - 1];
        }
        throw new IllegalArgumentException();
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (checkIndex(row, col)) {
            return quf.connected(sourceTop, convertArray(row, col));
        }
        throw new IllegalArgumentException();
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSiteCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return quf.connected(sourceTop, sinkBottom);
    }

    private boolean checkIndex(int row, int col) {
        if (row < 1 || row > matrixSize || col < 1 || col > matrixSize) {
            return false;
        }
        return true;
    }
}
