package Tiles;
import java.awt.*;

public class Verified implements BackColor {

	private final View tl;

	public Verified(View tile) {
		this.tl = tile;
	}

	@Override
	public Color getBackColor() {
		if (tl.Value() != 0 && tl.Value() != tl.Solution())
			return Color.blue;
		return tl.DefaultColor();
	}
}
