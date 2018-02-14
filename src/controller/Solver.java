package controller;

import model.Board;
import view.*;
public class Solver {

	private Board board;
	private int[] values;
	private View view;
		
	public Solver() {
		board = new Board();
		values = new int[81];
		view = new GUIView(this);
		board.addObserver(view);
		view.start();
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void setValues(int[] values) {
		for (int i = 0; i < Board.DIM * Board.DIM; i++) {
			board.setField(i, values[i]);
		}
		this.values = values;
	}
	
	public boolean isInitial(int index) {
		return values[index] != 0;
	}
	
	public void solve() {
		long startTime = System.nanoTime();
		int index = 0;
		int start = 1;
		while (board.hasEmptyField()) {
			if (!isInitial(index)) {
				boolean found = false;
				for (int value = start; value <= Board.DIM; value ++) {
					if (!found && !board.hasValue(index, value)) {
						board.setField(index, value);
						found = true;
					}
				}
				if (!found) {
					board.setField(index, 0);
					int i = 1;
					while (index - i >= 0 && isInitial(index - i)) {
						i++;
					}
					if (index - i < 0) {
						view.showMessage("Solution can not be found");
						view.end();
						return;
					}
					start = board.getField(index - i) + 1;
					index -= i;
				} else {
					index++;
					start = 1;
				}
			} else {
				index++;
				start = 1;
			}
			
			if (System.nanoTime() - startTime > Math.pow(10, 10) / 4) {
				view.showMessage("Solution can not be found");
				view.end();
				return;
			}
		}
		board.finish();
	}
	
	public static void main(String[] args) {
		new Solver();
	}
}