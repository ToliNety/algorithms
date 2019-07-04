public class Board {
    private final int[][] blocks;
    private final int manhattan;

    public Board(int[][] blocks) {
        this.blocks = blocks;

    }

    public int dimension(){
        return blocks.length;
    }

    public int hamming(){
        return 0;
    }

    public int manhattan(){
        return manhattan;
    }

    public boolean isGoal()                // is this board the goal board?
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    public boolean equals(Object y)        // does this board equal y?
    public Iterable<Board> neighbors()     // all neighboring boards
    public String toString()               // string representation of this board (in the output format specified below)

    public static void main(String[] args) {

    }
}
