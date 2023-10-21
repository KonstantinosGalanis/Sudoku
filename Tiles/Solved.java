package Tiles;

import java.awt.*;

public class Solved implements SudokuTile {

	private final int solved;
	private final int grid;
	private final int row;
	private final int column;
	private final Color defaultColor;

	public Solved(int sol, int grid, int row, int column) {
		this.solved = sol;
		this.grid = grid;
		this.row = row;
		this.column = column;
		this.defaultColor = new Color(235, 235, 235);
	}

        @Override
	public int Value() {
            return solved;
	}

        @Override
	public int Grid() {
            return grid;
	}

        @Override
	public int Row() {
            return row;
	}

        @Override
	public int Column() {
            return column;
	}

        @Override
	public int Solution() {
            return solved;
	}

	@Override
	public String Text() {
            return String.valueOf(solved);
	}
        
        @Override
	public Color DefaultColor() {
            return defaultColor;
	}

	@Override
	public boolean canChange() {
            return false;
	}

}
