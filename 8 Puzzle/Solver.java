import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solver {
    private List<Board> solution = new ArrayList<>();

    /**
     * find a solution to the initial board (using the A* algorithm).
     */
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        Comparator<SearchNode> comparator = (o1, o2) -> {
            if (o1.priority < o2.priority)
                return -1;
            if (o1.priority > o2.priority)
                return 1;
            return 0;
        };

        MinPQ<SearchNode> pq = new MinPQ<>(comparator);
        pq.insert(new SearchNode(initial, null, 0));

        MinPQ<SearchNode> twinPq = new MinPQ<>(comparator);
        twinPq.insert(new SearchNode(initial.twin(), null, 0));

        boolean solved = false;
        boolean twinSolved = false;

        SearchNode current = null;

        while (!solved && !twinSolved) {
            current = pq.delMin();
            SearchNode twinCurrent = twinPq.delMin();

            solved = current.isGoal();
            twinSolved = twinCurrent.isGoal();

            Board tmp = current.board;
            for (Board neighbor : tmp.neighbors()) {
                SearchNode neighborNode = new SearchNode(neighbor, current, current.moves + 1);
                if (neighborNode.isPrev(current)) {
                    continue;
                }
                pq.insert(neighborNode);
            }

            tmp = twinCurrent.board;
            for (Board neighbor : tmp.neighbors()) {
                SearchNode neighborNode = new SearchNode(neighbor, twinCurrent, twinCurrent.moves + 1);
                if (neighborNode.isPrev(twinCurrent)) {
                    continue;
                }
                twinPq.insert(neighborNode);
            }
        }

        if (solved) {
            constructPath(current);
        }
    }

    private void constructPath(SearchNode current) {
        while (current != null) {
            solution.add(current.board);
            current = current.prev;
        }
        Collections.reverse(solution);
    }

    private class SearchNode {
        Board board;
        SearchNode prev;
        int priority;
        int moves;

        SearchNode(Board board, SearchNode prev, int moves) {
            this.board = board;
            this.prev = prev;
            this.moves = moves;
            this.priority = board.manhattan() + moves;
        }

        boolean isGoal() {
            return board.isGoal();
        }

        boolean isPrev(SearchNode other) {
            if (other.prev == null) {
                return false;
            }
            return this.board.equals(other.prev.board);
        }
    }

    /**
     * is the initial board solvable? (see below).
     */
    public boolean isSolvable() {
        return !solution.isEmpty();
    }

    /**
     * min number of moves to solve initial board; -1 if unsolvable.
     */
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return solution.size() - 1;
    }

    /**
     * sequence of boards in the shortest solution; null if unsolvable.
     */
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {

    }

}