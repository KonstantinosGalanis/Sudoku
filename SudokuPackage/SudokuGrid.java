package SudokuPackage;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import Tiles.SudokuTile;
import Tiles.Solved;
import Tiles.Empty;

public class SudokuGrid {
	private final SudokuSolver s;
	private List<SudokuTile> tl;

	public SudokuGrid(SudokuSolver solver) {
            this.s = solver;
	}

	public void addDifficulty(int[][] difficulty) {
            tl = new ArrayList<>();
            List<Solved> solvedTiles = s.getSolution(difficulty);
            SudokuTile tile;
            for (int i = 0; i < difficulty.length; i++) {
                for (int j = 0; j < difficulty[i].length; j++) {
                    if (difficulty[i][j] == 0) {
                            tile = new Empty(solvedTiles.get(difficulty[i].length * i + j));
                    } 
                    else {
                            tile = solvedTiles.get(difficulty[i].length * i + j);
                    }
                    tl.add(tile);
                }
            }
	}

	public void changeTile(SudokuTile old_tl, SudokuTile new_tl) {
		tl.set(tl.indexOf(old_tl), new_tl);
	}

	public List<SudokuTile> Tile() {
		return tl;
	}

	public List<SudokuTile> SolvedTile() {
		return tl.stream().map(tile -> new Solved(tile.Solution(), tile.Grid(), tile.Row(), tile.Column())).collect(Collectors.toList());
	}

	public List<SudokuTile> SameValue(SudokuTile tile) {
		return tl.stream().filter(s -> s.Value() == tile.Value()).collect(Collectors.toList());
	}

	public List<SudokuTile> Conflict(SudokuTile t, int val) {
		return tl.stream().filter((s) -> ((s.Row() == t.Row() || s.Column() == t.Column() || s.Grid() == t.Grid()) && s.Value() == val)).collect(Collectors.toList());
	}

	public boolean is_wrong(SudokuTile t, int newValue) {
		return tl.stream().noneMatch(s -> ((s.Row() == t.Row() || s.Column() == t.Column() || s.Grid() == t.Grid()) && s.Value() == newValue));
	}

	public boolean is_Solved() {
		return tl.stream().allMatch(tile -> tile.Value() == tile.Solution());
	}
}
