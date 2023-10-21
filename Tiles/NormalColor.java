package Tiles;

import java.awt.*;

public class NormalColor implements BackColor {

	public final View view;

	public NormalColor(View view) {
		this.view = view;
	}

	@Override
	public Color getBackColor() {
		return view.DefaultColor();
	}
}
