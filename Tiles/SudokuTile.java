package Tiles;

import java.awt.*;

public interface SudokuTile {
	int Value();

	int Grid();

	int Row();

	int Column();

	int Solution();

	String Text();

	Color DefaultColor();

	boolean canChange();
}
