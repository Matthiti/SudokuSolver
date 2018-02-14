package view;

import java.util.Observable;
import java.util.Scanner;

import controller.Solver;
import model.Board;

public class TUIView implements View {
	
	private Scanner in;
	private Solver solver;
	
	public static final String DONE = "done";
	
	public TUIView(Solver solver) {
		in = new Scanner(System.in);
		this.solver = solver;
	}
	
	@Override
	public void start() {
		boolean done = false;
		int[] values = new int[81];
		while (!done) {
			System.out.println(solver.getBoard());
			System.out.println("Please input the already filled in values or type \"done\" when done");
			int row = readInt("Row: ");
			if (row == -1) {
				done = true;
				break;
			}
			int column = readInt("Column: ");
			if (column == -1) {
				done = true;
				break;
			}
			int value = readInt("Value: ");
			if (value == -1) {
				done = true;
				break;
			}
			values[row * Board.DIM + column] = value;
		}
		solver.setValues(values);
		solver.solve();
	}
	
	public int readInt(String message) {
		boolean valid = false;
		int value = -1;
		while (!valid) {
			System.out.print(message);
			String valueStr = in.next();
			try {
				value = Integer.parseInt(valueStr);
			} catch (NumberFormatException e) {
				if (valueStr.equals(DONE)) {
					return -1;
				}
			}
			
			if (value >= 0 && value <= Board.DIM) {
				valid = true;
			} else {
				System.out.println("Not a valid value");
			}
		}
		return value;
	}
	
	@Override
	public void showMessage(String message) {
		System.out.println(message);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Board board = (Board) arg0;
		System.out.println(board);
		end();		
	}

	@Override
	public void end() {
		boolean valid = false;
		while (!valid) {
			System.out.print("Do you want to play again? (y/n): ");
			String input = in.next();
			if (input.equals("y")) {
				valid = true;
			} else if (input.equals("n")) {
				System.exit(0);
			} else {
				System.out.println("Not a valid answer, please try again");
			}
		}
		new Solver();
	}
}