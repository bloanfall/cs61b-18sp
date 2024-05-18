package hw4.puzzle;


import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import java.util.List;

public class Solver {
    private class SearchNode implements Comparable<SearchNode> {
        private WorldState state;
        private int moves;
        private SearchNode pre;
        public SearchNode(WorldState state, int moves, SearchNode pre) {
            this.state = state;
            this.moves = moves;
            this.pre = pre;
        }

        public WorldState getState() {
            return state;
        }

        public int getMoves() {
            return moves;
        }

        public SearchNode getPre() {
            return pre;
        }

        @Override
        public int compareTo(SearchNode that) {
            int thisPriority = this.moves + this.state.estimatedDistanceToGoal();
            int thatPriority = that.moves + that.state.estimatedDistanceToGoal();
            return thisPriority - thatPriority;
        }
    
        
    }
    private MinPQ<SearchNode> pq = new MinPQ<>();
    private List<WorldState> bestSolution;
    private int totmoves;
    
    private void getAnswer(SearchNode goal){
        totmoves = goal.getMoves();
        bestSolution = new ArrayList<>();
        SearchNode p = goal;
        while (p != null) {
            bestSolution.add(p.state);
            p = p.pre;
        }
    }

    /** Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists.
     */
    public Solver(WorldState initial){
        pq.insert(new SearchNode(initial, 0, null));
        while (true) {
            SearchNode cur = pq.delMin();
            if (cur.state.isGoal()) {
                getAnswer(cur);
                break;
            }
            for (WorldState n : cur.state.neighbors()) {
                if (cur.pre == null || !n.equals(cur.pre.state)) {
                    pq.insert(new SearchNode(n, cur.moves + 1, cur));
                }
            }
        }
    }
    public int moves(){
        return totmoves;
    }

    /** Returns a sequence of WorldStates from the initial WorldState
     * to the solution.
     */
    public Iterable<WorldState> solution(){
        List<WorldState> ret = new ArrayList<>();
        for (int i = totmoves; i >= 0; i--) {
            ret.add(bestSolution.get(i));
        }
        return ret;
    }
}