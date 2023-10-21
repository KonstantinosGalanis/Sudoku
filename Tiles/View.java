package Tiles;

import javax.swing.*;
import java.awt.*;

public class View {

	private final JTextField tf;
	BackColor color;
	private SudokuTile tl;

	public View(JTextField tf2) {
		this.tf = tf2;
		color = new NormalColor(this);
	}

	public int Value() {
		return tl.Value();
	}

	public int Solution() {
		return tl.Solution();
	}

	public String Text() {
		return tl.Text();
	}

	public Color DefaultColor() {
		return tl.DefaultColor();
	}

	public Color getBackgroundColor() {
		return color.getBackColor();
	}

	public void setBackgroundColorState(BackColor state) {
		this.color = state;
	}

	public void setTile(SudokuTile tl) {
		this.tl = tl;
		tf.setText(this.Text());
		tf.setBackground(this.getBackgroundColor());
	}

	public SudokuTile getSudokuTile() {
		return tl;
	}

	public JTextField getTextField() {
		return tf;
	}
}
