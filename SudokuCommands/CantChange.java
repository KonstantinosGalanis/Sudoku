package SudokuCommands;


public class CantChange extends RuntimeException {
	public CantChange(String s) {
		System.out.println(s);
	}
}
