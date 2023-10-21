package SudokuCommands;
import SudokuPackage.SudokuGrid;
import Tiles.View;
import Tiles.Empty;
import Tiles.SudokuTile;

public class Delete extends Commands {

	public Delete(SudokuGrid grid, View view) {
		super(grid, view);
		this.new_tl = new Empty(old_tl);
	}

	@Override
	protected boolean is0k(SudokuTile old_tl, SudokuTile new_tl) {
		return old_tl.canChange();
	}

}
