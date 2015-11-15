import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * The Solver class represents a solution to a Board (if any)
 * 
 * @author Mike Milonakis
 */
public class Solver {

    private MinPQ<SearchNode> pqueue;
    private Stack<Board> solution;
    private int moves;
    private MinPQ<SearchNode> twinPqueue;

    public Solver(Board initial) {
        pqueue = new MinPQ<SearchNode>(new ManhattanComparator());
        twinPqueue = new MinPQ<SearchNode>(new ManhattanComparator());
        SearchNode snode = new SearchNode();
        snode.moves = 0;
        snode.previous = null;
        snode.current = initial;
        snode.manhattan = initial.manhattan();

        SearchNode goal = solve(snode);
        if (goal != null) {
            solution = new Stack<>();
            solution.push(goal.current);
            SearchNode n = goal.previous;
            while (n != null) {
                solution.push(n.current);
                n = n.previous;
            }
        }
        else {
            moves = -1;
        }

    }

    private SearchNode solve(SearchNode snode) {
        boolean done = false;
        pqueue.insert(snode);
        SearchNode twinSnode = new SearchNode();
        twinSnode.moves = 0;
        twinSnode.previous = null;
        twinSnode.current = snode.current.twin();
        twinPqueue.insert(twinSnode);
        while (!done) {
            if (!(pqueue.isEmpty())) {
                SearchNode node = pqueue.delMin();
                if (node.current.isGoal()) {
                    moves = node.moves;
                    return node;
                }
                for (Board b : node.current.neighbors()) {
                    boolean beenHere = false;
                    SearchNode prev = node.previous;
                    while (prev != null) {
                        if (prev.current.equals(b)) {
                            beenHere = true;
                            break;
                        }
                        prev = prev.previous;
                    }
                    if (beenHere)
                        continue;
                    SearchNode newSnode = new SearchNode();
                    newSnode.moves = node.moves + 1;
                    newSnode.previous = node;
                    newSnode.current = b;
                    newSnode.manhattan = b.manhattan();

                    pqueue.insert(newSnode);
                }
            }
            if (!(twinPqueue.isEmpty())) {
                SearchNode node = twinPqueue.delMin();
                if (node.current.isGoal()) {
                    return null;
                }
                for (Board b : node.current.neighbors()) {
                    boolean beenHere = false;
                    SearchNode prev = node.previous;
                    while (prev != null) {
                        if (prev.current.equals(b)) {
                            beenHere = true;
                            break;
                        }
                        prev = prev.previous;
                    }
                    if (beenHere)
                        continue;
                    SearchNode newSnode = new SearchNode();
                    newSnode.moves = node.moves + 1;
                    newSnode.previous = node;
                    newSnode.current = b;
                    newSnode.manhattan = b.manhattan();

                    twinPqueue.insert(newSnode);
                }
            }
        }
        return null;
    }

    public Iterable<Board> solution() {
        return solution;
    }

    public int moves() {
        return moves;
    }

    public boolean isSolvable() {
        return (solution != null);
    }

    private class ManhattanComparator implements Comparator<SearchNode> {

        @Override
        public int compare(SearchNode a, SearchNode b) {
            int pa = a.manhattan + a.moves;
            int pb = b.manhattan + b.moves;
                return (pa) - (pb);
        }
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
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
}

class SearchNode {
    Board current;
    SearchNode previous;
    int moves;
    int manhattan;
}
