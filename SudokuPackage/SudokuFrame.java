package SudokuPackage;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.ItemListener;
import javax.imageio.ImageIO;

public class SudokuFrame {
    public JCheckBox verify;
    public JButton solve;
    public JButton undo;
    public JButton erase;
    public JTextField[][] tf;
    public JButton[] jb;
    public JMenuItem d1;
    public JMenuItem d2;
    public JMenuItem d3;
    
    public SudokuFrame() {
        JFrame frame = new JFrame("Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        
        JMenuBar menu = new JMenuBar();
        menu.setVisible(true);
        frame.setJMenuBar(menu);

        JMenu m1 = new JMenu("New Game");
        menu.add(m1);

        d1 = new JMenuItem("Easy");
        d2 = new JMenuItem("Intermediate");
        d3 = new JMenuItem("Expert");
        m1.add(d1);
        m1.add(d2);
        m1.add(d3);

        JPanel gl = new JPanel(new GridLayout(3, 3));
        gl.setBorder(BorderFactory.createEmptyBorder(50,2,2,2));
        Border Raised = BorderFactory.createRaisedBevelBorder();
        frame.add(BorderLayout.CENTER, gl);

        JPanel[] gl2 = new JPanel[9];
        for (int i = 0; i < 9; i++) {
            gl2[i] = new JPanel(new GridLayout(3, 3, 1, 1));
            gl2[i].setBorder(Raised);
            gl.add(gl2[i]);
        }
        

        tf = new JTextField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tf[i][j] = new JTextField("");
                tf[i][j].setHorizontalAlignment(JTextField.CENTER);
                tf[i][j].setBackground(Color.WHITE);
                Font font1 = new Font("SansSerif", Font.BOLD, 20);
                tf[i][j].setFont(font1);
                gl2[(j / 3) + (i / 3) * 3].add(tf[i][j]);
                tf[i][j].setEditable(false);
                tf[i][j].setFocusable(false);
            }
        }
        
        JPanel end = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        end.setBorder(BorderFactory.createEmptyBorder(10,2,2,2));
        end.setPreferredSize(new Dimension(500, 120));
        jb = new JButton[9];

        for (int i = 0; i < 9; i++) {
            jb[i] = new JButton(String.valueOf(i + 1));
            jb[i].setPreferredSize(new Dimension(50, 30));
            jb[i].setMnemonic(97 + i);
            jb[i].setEnabled(false);
            end.add(jb[i]);
        }

        erase = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("/Images/eraser.png")).getScaledInstance(16, 20, Image.SCALE_DEFAULT);
            erase.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        erase.setEnabled(false);
        end.add(erase);


        undo = new JButton();
        try {
            Image img1 = ImageIO.read(getClass().getResource("/Images/undo.png")).getScaledInstance(16, 20, Image.SCALE_DEFAULT);
            undo.setIcon(new ImageIcon(img1));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        undo.setEnabled(false);
        end.add(undo);

        verify = new JCheckBox("Verify against solution");
        verify.setEnabled(false);
        verify.setRolloverEnabled(false);
        end.add(verify);
        
        
        solve = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("/Images/rubik.png")).getScaledInstance(16, 20, Image.SCALE_DEFAULT);
            solve.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        solve.setEnabled(false);
        end.add(solve);

        frame.add(BorderLayout.PAGE_END,end);

        frame.pack();
    }
    
    public void EasyGameListener(ActionListener listener) {
        d1.addActionListener(listener);
    }

    public void IntermediateGameListener(ActionListener listener) {
        d2.addActionListener(listener);
    }

    public void ExpertGameListener(ActionListener listener) {
        d3.addActionListener(listener);
    }

    public void EraseListener(ActionListener listener) {
        erase.addActionListener(listener);
    }

    public void UndoListener(ActionListener listener) {
        undo.addActionListener(listener);
    }

    public void VerifyListener(ItemListener listener) {
        verify.addItemListener(listener);
    }

    public void SolvedListener(ActionListener listener) {
        solve.addActionListener(listener);
    }

    public void NumbersListener(ActionListener listener) {
        for (int i = 0; i < 9; i++) {
            jb[i].addActionListener(listener);
            
        }
    }

    public void FieldsListener(FocusListener listener) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tf[i][j].addFocusListener(listener);
            }
        }
    }
}
