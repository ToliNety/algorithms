import java.util.LinkedList;
import java.util.List;

public class Board {
    private final int[][] blocks;
    private final int length;
    private int i0;
    private int j0;
    private final int manhattan;

    public Board(int[][] blocks) {
        this.blocks = blocks;
        this.length = blocks.length;

        if (2 > length || length > 127)
            throw new IllegalArgumentException();

        int manhattan = 0;
        for (int j = 0; j < length; j ++) {
            for (int i = 0; i < length; i++) {
                int num = blocks[i][j];
                if (num != i + 1 + j * length) {
                    int x = num % length == 0 ? length : num % length - 1;
                    int y = num / length;
                    manhattan += Math.abs(x - i) + Math.abs(y - j);
                }
                if (num == 0) {
                    i0 = i;
                    j0 = j;
                }
            }
        }
        this.manhattan = manhattan;
    }

    public int dimension() {
        return length;
    }

    public int hamming() {
        return 0;
    }

    public int manhattan() {
        return manhattan;
    }

    public boolean isGoal() {
        return manhattan == 0;
    }

    public Board twin() {
        return this;
    }

    public boolean equals(Object y) {
        if (y.getClass() != this.getClass())
            return false;
        Board yBoard = (Board) y;

        if (yBoard.length != this.length)
            return false;

        if (yBoard.manhattan != this.manhattan)
            return false;

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (this.blocks[i][j] != yBoard.blocks[i][j])
                    return false;
            }
        }

        return true;
    }

    public Iterable<Board> neighbors() {
        List<Board> neighbors = new LinkedList<>();
        if (i0 > 0) {
            int newBlocks[][] = blocks.clone();
            int e = newBlocks[i0 - 1][j0];
            newBlocks[i0 - 1][j0] = 0;
            newBlocks[i0][j0] = e;
            neighbors.add(new Board(newBlocks));
        }

        if (i0 < length - 1){
            int newBlocks[][] = blocks.clone();
            int e = newBlocks[i0 + 1][j0];
            newBlocks[i0 + 1][j0] = 0;
            newBlocks[i0][j0] = e;
            neighbors.add(new Board(newBlocks));
        }

        if (j0 > 0){
            int newBlocks[][] = blocks.clone();
            int e = newBlocks[i0][j0 - 1];
            newBlocks[i0][j0 - 1] = 0;
            newBlocks[i0][j0] = e;
            neighbors.add(new Board(newBlocks));
        }

        if (j0 < length - 1){
            int newBlocks[][] = blocks.clone();
            int e = newBlocks[i0][j0 + 1];
            newBlocks[i0][j0 + 1] = 0;
            newBlocks[i0][j0] = e;
            neighbors.add(new Board(newBlocks));
        }

        return neighbors;
    }

    public String toString() {
        String out = "";
        for (int j = 0; j < length; j ++) {
            for (int i = 0; i < length; i++) {
                out += blocks[i][j];
            }
            out += System.lineSeparator();
        }

        return out;
    }

    public static void main(String[] args) {

    }
}
