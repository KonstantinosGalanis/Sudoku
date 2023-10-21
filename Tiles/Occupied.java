package Tiles;

import java.awt.*;

public class Occupied extends Solved {

	protected final int val;
	private final Color defaultColor = new Color(140, 140, 140);

	public Occupied(SudokuTile tile, int newValue) {
		super(tile.Solution(), tile.Grid(), tile.Row(), tile.Column());
		this.val = newValue;
	}

        @Override
	public int Value() {
		return val;
	}

	@Override
	public String Text() {
		return String.valueOf(val);
	}

	@Override
	public Color DefaultColor() {
		return defaultColor;
	}

	@Override
	public boolean canChange() {
		return true;
	}
}
