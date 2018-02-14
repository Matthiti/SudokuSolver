package view;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import static javax.swing.JOptionPane.*;

import javax.swing.*;

import controller.Solver;
import model.Board;
import util.Util;

public class GUIView extends JFrame implements View, Observer {

	private JButton but;
	private JButton again;
	private Solver solver;
	private int[] values;
	private Map<String, JTextField> textFields;
	
	public static final String EMPTY = "    ";
	private static final long serialVersionUID = 1L;

	public GUIView(Solver solver) {
		super("Sudoku Solver");
		this.solver = solver;
	}
	
	@Override
	public void start() {
		values = new int[81];
		textFields = new HashMap<>();
		init();
		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void init() {
		Container c = getContentPane();
		c.setLayout(new GridLayout(0, Board.DIM));
		
		for (int i = 0; i < Board.DIM * Board.DIM; i++) {
			JTextField textField = new JTextField();
	        Font bigFont = textField.getFont().deriveFont(Font.PLAIN, 50f);
			textField.setFont(bigFont);
			textField.setText(EMPTY);
			textField.setHorizontalAlignment(JTextField.CENTER);
			textField.setName(i + "");
			textField.addFocusListener(new TextFieldFocusListener(textField));
			c.add(textField);
			textFields.put(i + "", textField);
		}
		but = new JButton("Confirm");
		but.addActionListener(new ButtonListener());
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.add(but);
		c.add(panel);
		
		again = new JButton("Again");
		again.addActionListener(new AgainButtonListener());
		again.setVisible(false);
		panel.add(again);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		Board board = (Board) arg0;
		for (int i = 0; i < Board.DIM * Board.DIM; i++) {
			if (board.getField(i) != 0) {
				textFields.get(i + "").setText(board.getField(i) + "");
			}
		}
		showMessage("Solution found!");
		end();
	}
	
	@Override
	public void end() {
		but.setVisible(false);
		again.setVisible(true);
	}
	
	@Override
	public void showMessage(String message) {
		showMessageDialog(null, message);
	}
	
	private class TextFieldFocusListener implements FocusListener {
		
		private JTextField textField;
		
		public TextFieldFocusListener(JTextField textField) {
			this.textField = textField;
		}

		@Override
		public void focusGained(FocusEvent arg0) {
			textField.setText("");
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if (!Util.isDigit(textField.getText())) {
				values[Integer.parseInt(textField.getName())] = 0;
				textField.setText("");
			} else {
				int index = Integer.parseInt(textField.getName());
				values[index] = Integer.parseInt(textField.getText());
			}
		}
	}
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			but.setEnabled(false);
			for (JTextField t : textFields.values()) {
				t.setEnabled(false);
			}
			solver.setValues(values);
			System.out.println("Solving...");
			solver.solve();
		}
	}
	
	private class AgainButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			dispose();
			new Solver();
		}
	}
}