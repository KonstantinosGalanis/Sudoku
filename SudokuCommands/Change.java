package SudokuCommands;
import SudokuPackage.SudokuGrid;
import Tiles.View;
import Tiles.Occupied;
import Tiles.SudokuTile;

public class Change extends Commands {

	private final int new_val;

	public Change(SudokuGrid grid, View view, int new_val) {
		super(grid, view);
		this.new_val = new_val;
		this.new_tl = new Occupied(old_tl, new_val);
	}

	@Override
	protected boolean is0k(SudokuTile old_tl, SudokuTile new_tl) {
		return grid.is_wrong(old_tl, new_val) && old_tl.canChange();
	}

}
