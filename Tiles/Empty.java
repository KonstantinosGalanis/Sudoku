package Tiles;
import java.awt.*;

public class Empty extends Solved {
	private final Color default_color = new Color(255, 255, 255);

	public Empty(SudokuTile st) {
		super(st.Solution(), st.Grid(), st.Row(), st.Column());
	}
        
        @Override
	public int Value() {
            return 0;
	}

        @Override
	public String Text() {
            return "";
	}

        @Override
	public Color DefaultColor() {
            return default_color;
	}

	@Override
	public boolean canChange() {
            return true;
	}

}
