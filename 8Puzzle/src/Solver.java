import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Solver {
    private final List<Board> solution = new LinkedList<>();
    private final MinPQ<Node> pq;
    private final Node rootNode;

    public Solver(Board initial) {
        pq = new MinPQ<>(Comparator.comparingInt(Node::priority));

        Node rootNode = new Node(initial, null, 0);
        pq.insert(rootNode);
        this.rootNode = rootNode;

        boolean isFound = false;
        while (!isFound){
            Node nodeProcessed = pq.delMin();
            if (nodeProcessed.getBoard().isGoal()){
                isFound = true;
            } else {
                addNeighborsToGame(nodeProcessed);
            }
        }

    }

    private void addNeighborsToGame(Node nodeProcessed) {
        Iterable<Board> neighbors = nodeProcessed.getBoard().neighbors();
        neighbors.forEach(neighbor -> {
            Node neighborNode = new Node(neighbor, nodeProcessed.board, nodeProcessed.move + 1);
            pq.insert(neighborNode);
        });
    }

    public boolean isSolvable() {
        return true;
    }

    public int moves() {
        return solution.size();
    }

    public Iterable<Board> solution() {
        return solution;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private static class Node {
        private final Board board;
        private final Board parent;
        private final int move;

        public Node(Board board, Board parent, int move) {
            this.board = board;
            this.parent = parent;
            this.move = move;
        }

        public Board getBoard() {
            return board;
        }

        public Board getParent() {
            return parent;
        }

        public int getMove() {
            return move;
        }

        public int priority() {
            return move + board.manhattan();
        }
    }
}
