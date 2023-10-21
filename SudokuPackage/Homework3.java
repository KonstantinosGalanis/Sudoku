package SudokuPackage;

public class Homework3 {
	private Homework3() {
		SudokuFrame frame = new SudokuFrame();
                SudokuSolver solver = new SudokuSolver();
		SudokuGrid grid = new SudokuGrid(solver);
		new SudokuControl(frame, grid);
	}
        
	public static void main(String[] args) {
		new Homework3();
	}

}
