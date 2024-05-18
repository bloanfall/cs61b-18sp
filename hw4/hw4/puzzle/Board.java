package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    private int[][] tiles;
    private static final int BLANK = 0;
    private int N;


    /*Constructs a board from an N-by-N array of tiles,
    where tiles[i][j] = tile at row i, column j*/
    public Board(int[][] tiles) {
        this.N = tiles.length;
        this.tiles = new int[N][N];
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }

    }

    /*Returns value of tile at row i, column j (or 0 if blank) */
    public int tileAt(int i, int j){
        if (i < 0 || i >= size() || j < 0 || j >= size()){
            throw new IndexOutOfBoundsException();
        }
        return tiles[i][j];
    }

    /*Returns the board size N*/
    public int size(){
        return tiles.length;
    }

     /**
     * Returns neighbors of this board.
     * SPOILERZ: This is the answer.
     * @sourse https://joshh.ug/neighbors.html
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    /**The number of tiles in the wrong position. */
    public int hamming(){
        int hamming = 0;
        for (int i = 0; i < size(); i++){
            for (int j = 0; j < size(); j++){
                if (tileAt(i, j) != size() * i + j + 1){
                    hamming++;
                }
            }
        }
        return hamming;
    }

    /**The sum of the Manhattan distances (sum of the vertical and horizontal distance) 
     * from the tiles to their goal positions. */
    public int manhattan(){
        int dist = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (tiles[i][j] == 0) {
                    continue;
                } else {
                    int targeti = (tiles[i][j] - 1) / size();
                    int targetj = (tiles[i][j] - 1) % size();
                    dist += Math.abs(targeti - i);
                    dist += Math.abs(targetj - j);
                }
            }
        }
        return dist;
    }

    /** Estimated distance to goal. This method should
     * simply return the results of manhattan() when submitted to
     * Gradescope.
     */
    public int estimatedDistanceToGoal(){
        return manhattan();
    }

     /** Returns true if this board's tile values are the same
     position as y's.
     */
    public boolean equals(Object y){
        if (y == null) {
            return false;
        }
        if (y == this) {
            return true;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board that = (Board) y;
        if (that.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < size();i++) {
            for (int j = 0; j < size(); j++) {
                if (this.tileAt(i, j) != that.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Returns the string representation of the board.  */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
