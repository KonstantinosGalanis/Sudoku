package SudokuPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.util.List;
import java.util.*;
import Tiles.View;
import SudokuCommands.Commands;
import SudokuCommands.Delete;
import SudokuCommands.Change;
import Tiles.SudokuTile;
import Tiles.Verified;
import Tiles.NormalColor;
import SudokuCommands.CantChange;

public class SudokuControl {

	private final SudokuFrame frame;
	private final SudokuGrid grid;
	private final static Color CHOSEN = new Color(255, 255, 200);
	private final static Color WRONG = new Color(255, 0, 0);
	private JTextField chosenfield;
	private Deque<Commands> history;
	private Map<JTextField, View> temp;
        private char key = '\0'; 

	public SudokuControl(SudokuFrame frame, SudokuGrid grid) {
		this.frame = frame;
		this.grid = grid;

		frame.EasyGameListener(new DifficultyListener("Difficulties/easy.txt"));
		frame.IntermediateGameListener(new DifficultyListener("Difficulties/intermediate.txt"));
		frame.ExpertGameListener(new DifficultyListener("Difficulties/expert.txt"));
		frame.EraseListener(new EraseListener());
                frame.UndoListener(new UndoListener());
                frame.VerifyListener(new VerifyListener());
                frame.SolvedListener(new SolvedListener());
                frame.NumbersListener(new NumbersListener());
		frame.FieldsListener(new FieldsListener());	
	}

	private void updateColors() {
		temp.values().forEach(viewTile -> viewTile.getTextField().setBackground(viewTile.getBackgroundColor()));
	}

	public void updateTiles(List<SudokuTile> tiles) {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
                            View v = new View(frame.tf[i][j]);
                            v.setTile(tiles.get(9 * i + j));
                            temp.put(frame.tf[i][j], v);
                            frame.tf[i][j].setFocusable(true);
                            temp.put(frame.tf[i][j], v);
			}
			frame.jb[i].setEnabled(true);
		}

		frame.verify.setSelected(false);
		frame.verify.setEnabled(true);
		frame.solve.setEnabled(true);
	}

	private void disabled() {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    frame.tf[i][j].setFocusable(false);
                }
                frame.jb[i].setEnabled(false);
            }
            frame.verify.setEnabled(false);
            frame.solve.setEnabled(false);
            frame.undo.setEnabled(false);
            frame.erase.setEnabled(false);
	}

	private class DifficultyListener implements ActionListener {
            private final String file;
            int[][] difficulty = new int[9][9];

            public DifficultyListener(String file) {
                this.file = file;
            }

            @Override
            public void actionPerformed(ActionEvent ae) {
                InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
                Scanner sc = new Scanner(in);
                while(sc.hasNext()) {
                    for(int i=0;i<9;i++){
                        String row = sc.next();
                        for(int j=0;j<9;j++){
                            if(row.charAt(j) == '.'){
                                difficulty[i][j] = 0;
                            }
                            else{
                                difficulty[i][j] = Character.getNumericValue(row.charAt(j));
                            }
                        }
                    }
                }
                sc.close();
                history = new LinkedList<>();
                temp = new HashMap<>();
                grid.addDifficulty(difficulty);
                updateTiles(grid.Tile());
            }
	}

	private class FieldsListener extends KeyAdapter implements FocusListener {
            
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_1:key  = e.getKeyChar(); break;
                    case KeyEvent.VK_2:key  = e.getKeyChar(); break;
                    case KeyEvent.VK_3:key  = e.getKeyChar(); break;
                    case KeyEvent.VK_4:key  = e.getKeyChar(); break;
                    case KeyEvent.VK_5:key  = e.getKeyChar(); break;
                    case KeyEvent.VK_6:key  = e.getKeyChar(); break;
                    case KeyEvent.VK_7:key  = e.getKeyChar(); break;
                    case KeyEvent.VK_8:key  = e.getKeyChar(); break;
                    case KeyEvent.VK_9:key  = e.getKeyChar(); break;
                    case KeyEvent.VK_DELETE:key  = e.getKeyChar(); break;
                    case KeyEvent.VK_BACK_SPACE:key  = e.getKeyChar(); break;
                }
                
                if(e.getKeyCode() == 127 || e.getKeyCode() == 8) {
                    Commands Delete = new Delete(grid, temp.get(chosenfield));
                    Delete.run();
                    history.push(Delete);
                    updateColors();
                }
                else {
                    if (key  == '\0')
                        return ;

                    updateColors();
                    if (chosenfield == null) {
                        return;
                    }

                    int newVal = Character.getNumericValue(key);

                    try {
                        Commands change = new Change(grid, temp.get(chosenfield), newVal);
                        change.run();
                        if (history.isEmpty())
                            frame.undo.setEnabled(true);

                        history.push(change);

                        if (grid.is_Solved()) {
                            JFrame victoryPopup = new JFrame("Congratulations");
                            JTextField victoryMessage = new JTextField("Congratulations");
                            victoryMessage.setEditable(false);
                            victoryPopup.add(victoryMessage);
                            victoryPopup.setSize(200, 100);
                            victoryPopup.setVisible(true);
                            disabled();
                        }

                    } catch (CantChange ex) {
                        for (SudokuTile conflict : grid.Conflict(temp.get(chosenfield).getSudokuTile(), newVal)) {
                            frame.tf[conflict.Row()][conflict.Column()].setBackground(WRONG);
                        }
                        chosenfield.setBackground(CHOSEN);
                    }
                    key = '\0';
                }
            }
            
            @Override
            public void focusGained(FocusEvent fe) {
                updateColors();
                chosenfield = (JTextField) fe.getComponent();
                chosenfield.addKeyListener(this);
                chosenfield.setBackground(CHOSEN);
                if (!chosenfield.getText().isEmpty()) {
                    frame.erase.setEnabled(true);
                    grid.SameValue(temp.get(chosenfield).getSudokuTile()).forEach((t) ->frame.tf[t.Row()][t.Column()].setBackground(CHOSEN));
                } else {
                    frame.erase.setEnabled(false);
                }
            }

            @Override
                public void focusLost(FocusEvent e) {
            }
        }

	
	private class SolvedListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent ae) {
                updateTiles(grid.SolvedTile());
                disabled();
            }
	}

	private class EraseListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent ae) {

                try {
                    Commands Delete = new Delete(grid, temp.get(chosenfield));
                    Delete.run();
                    history.push(Delete);

                    updateColors();
                } catch (CantChange ex) {
                    chosenfield.setBackground(WRONG);
                }
            }

	}

	private class NumbersListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent ae) {
                updateColors();
                if (chosenfield == null) {
                    return;
                }

                int newValue = Integer.parseInt(ae.getActionCommand());

                try {
                    Commands change = new Change(grid, temp.get(chosenfield), newValue);
                    change.run();
                    if (history.isEmpty())
                        frame.undo.setEnabled(true);

                    history.push(change);

                    if (grid.is_Solved()) {
                        JFrame victoryPopup = new JFrame("Congratulations");
                        JTextField victoryMessage = new JTextField("Congratulations");
                        victoryMessage.setEditable(false);
                        victoryPopup.add(victoryMessage);
                        victoryPopup.setSize(200, 100);
                        victoryPopup.setVisible(true);
                        disabled();
                    }

                } catch (CantChange ex) {
                    for (SudokuTile conflict : grid.Conflict(temp.get(chosenfield).getSudokuTile(), newValue)) {
                        frame.tf[conflict.Row()][conflict.Column()].setBackground(WRONG);
                    }
                    chosenfield.setBackground(CHOSEN);
                }
            }
	}
        
        private class KeyListener  {

            
	}

	private class VerifyListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent ie) {
                    temp.values().forEach(viewTile -> {
                        if (ie.getStateChange() == ItemEvent.SELECTED)
                            viewTile.setBackgroundColorState(new Verified(viewTile));
                        else
                            viewTile.setBackgroundColorState(new NormalColor(viewTile));
                    });
                    updateColors();
		}

	}

	private class UndoListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!history.isEmpty()) {
                    Commands cmd = history.pop();
                    cmd.undo();
                    if (history.isEmpty()) frame.undo.setEnabled(false);
                    updateColors();
                }
            }
	}
}