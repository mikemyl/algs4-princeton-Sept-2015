import edu.princeton.cs.algs4.Stack;

/**
 * The Boad class represents a N by N puzzle
 * 
 * @author Mike Milonakis
 */
public class Board {

    private final int[][] board;

    /**
     * Public constructor of the board class. Takes a N-N array containing all
     * the numbers between 0 and N^2 -1. 0 represents the blank square
     * 
     * @param blocks the N by N array
     */
    public Board(int[][] blocks) {
        this.board = new int[blocks.length][];
        for (int i = 0; i < blocks.length; i++) {
            this.board[i] = new int[blocks[i].length];
            for (int j = 0; j < blocks[i].length; j++) {
                this.board[i][j] = blocks[i][j];
            }
        }
    }

    /**
     * Returns the dimension of the board
     * 
     * @return board dimension N
     */
    public int dimension() {
        return board.length;
    }

    /**
     * Hamming distance represents the number of blocks that are out of space
     *
     * @return The number of blocks that are out of space
     */
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != 0) {
                    if (board[i][j] != i * board.length + j + 1)
                        hamming++;
                }
            }
        }
        return hamming;
    }

    /**
     * Hamming distance represents the sum of x, y distances of the blocks that
     * are out of space
     *
     * @return The sum of the Manhattan distances between blocks and the goal
     */
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != 0) {
                    if (board[i][j] != i * board.length + j + 1) {
                        int xRef = (board[i][j] - 1) % board.length;
                        int yRef = (board[i][j] - 1) / board.length;
                        int x = xRef > j ? xRef - j : j - xRef;
                        int y = yRef > i ? yRef - i : i - yRef;
                        manhattan += x + y;
                    }
                }
            }
        }
        return manhattan;
    }

    /**
     * Checks if current board is a goal board - solution to the puzzle
     * 
     * @return True if it is, false if not
     */
    public boolean isGoal() {
        if (hamming() == 0)
            return true;
        return false;
    }

    /**
     * Returns a twin Board - that is a board with any pair of blocks exchanged
     * 
     * @return twin, the new Board
     */
    public Board twin() {
        int i1 = 0;
        int j1 = 0;
        int i2 = 0;
        int j2 = 1;
        if (board[i1][j1] == 0) {
            j1 = 1;
            i2 = 1;
        }
        if (board[i2][j2] == 0) {
            i2 = 1;
            j2 = 0;
        }
        int temp = board[i1][j1];
        board[i1][j1] = board[i2][j2];
        board[i2][j2] = temp;
        Board twin = new Board(board);
        board[i2][j2] = board[i1][j1];
        board[i1][j1] = temp;

        return twin;
    }

    public boolean equals(Object y) {
        if (y == this)
            return true;
        if (y == null)
            return false;

        if (y.getClass() != this.getClass())
            return false;

        Board that = (Board) y;
        if (this.dimension() != that.dimension())
            return false;
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (this.board[i][j] != that.board[i][j])
                    return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> result = new Stack<Board>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != 0)
                    continue;
                if ((i - 1) >= 0) {
                    board[i][j] = board[i - 1][j];
                    board[i - 1][j] = 0;
                    result.push(new Board(board));
                    board[i -1][j] = board[i][j];
                    board[i][j] = 0;
                }
                if ((j - 1) >= 0) {
                    board[i][j] = board[i][j - 1];
                    board[i][j - 1] = 0;
                    result.push(new Board(board));
                    board[i][j - 1] = board[i][j];
                    board[i][j] = 0;
                }
                if ((i + 1) < board.length) {
                    board[i][j] = board[i + 1][j];
                    board[i + 1][j] = 0;
                    result.push(new Board(board));
                    board[i + 1][j] = board[i][j];
                    board[i][j] = 0;
                }
                if ((j + 1) < board[i].length) {
                    board[i][j] = board[i][j + 1];
                    board[i][j + 1] = 0;
                    result.push(new Board(board));
                    board[i][j + 1] = board[i][j];
                    board[i][j] = 0;
                }
            }
        }
        return result;
    }

    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append(dimension() + "\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                ret.append(String.format("%2d ", board[i][j]));
            }
            ret.append(System.lineSeparator());
        }
        return ret.toString();
    }

    public static void main(String[] args) {
        int[][] n = { { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6 } };
        Board board = new Board(n);
        System.out.println(board);
        System.out.println(board.manhattan());

        for (Board b: board.neighbors()) {
            System.out.println(b);
            System.out.println(b.manhattan());
        }

    }
}
