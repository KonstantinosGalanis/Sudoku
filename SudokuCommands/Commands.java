package SudokuCommands;
import SudokuPackage.SudokuGrid;
import Tiles.View;
import Tiles.SudokuTile;

public abstract class Commands {

	protected final SudokuGrid grid;
	protected final View view;
	protected final SudokuTile old_tl;
	protected SudokuTile new_tl;

	protected Commands(SudokuGrid grid, View viewTile) {
		this.grid = grid;
		this.view = viewTile;
		old_tl = viewTile.getSudokuTile();
	}

	public void run() {
		if (!is0k(old_tl, new_tl)) {
			throw new CantChange("Wrong");
		}
		view.setTile(new_tl);
		grid.changeTile(old_tl, new_tl);
	}

	public void undo() {
		view.setTile(old_tl);
		grid.changeTile(new_tl, old_tl);
	}

	protected abstract boolean is0k(SudokuTile old_tl, SudokuTile new_tl);

}
