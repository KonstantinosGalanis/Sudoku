package SudokuPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import Tiles.SudokuTile;
import Tiles.Solved;
import Tiles.Empty;
import Tiles.Occupied;


public class SudokuSolver {

    private final List<SudokuTile> tl;
    private int[][] in;

    public SudokuSolver() {
            tl = new ArrayList<>();
    }

    private void run() {
        SudokuTile tile;
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[i].length; j++) {
                if (in[i][j] == 0) {
                        tile = new Empty(new Solved(in[i][j], (j / 3) + (i / 3) * 3, i, j));
                } else {
                        tile = new Solved(in[i][j], (j / 3) + (i / 3) * 3, i, j);
                }
                tl.add(tile);
            }
        }
    }

    public List<Solved> getSolution(int[][] in) {
        this.in = in;
        run();
        solve();
        return tl.stream().map(tile -> new Solved(tile.Value(), tile.Grid(), tile.Row(), tile.Column())).collect(Collectors.toList());
    }

    public boolean is0k(SudokuTile t, int newVal) {
		return t.canChange() && tl.stream().noneMatch((s) -> ((s.Row() == t.Row() || s.Column() == t.Column() || s.Grid() == t.Grid()) && s.Value() == newVal));
	}
    
    private boolean solve() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                SudokuTile st = tl.get(in[i].length * i + j);
                if (st.Value() == 0) {
                    for (int k = 1; k < 10; k++) {
                        if (is0k(st, k) && st.canChange()) {
                            tl.set(tl.indexOf(tl.get(in[i].length * i + j)), new Occupied(tl.get(in[i].length * i + j), k));
                            if (solve()) {
                                return true;
                            }
                            tl.set(tl.indexOf(tl.get(in[i].length * i + j)), new Occupied(tl.get(in[i].length * i + j), 0));
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}
