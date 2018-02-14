package model;
import java.util.Observable;

/**
 * Representation of the 9x9 fields.
 * @author Matthijs Roelink
 */
public class Board extends Observable {

	/**
	 * An array representing the values at an index.
	 */
	//@ private invariant fields.length == 81;
	private int[] fields;
	
	/**
	 * The size of the board.
	 */
	public static final int DIM = 9;
	
	/**
	 * The size of a block.
	 */
	public static final int BLOCK_SIZE = (int) Math.sqrt(DIM);
	
	//======================= Constructor =======================
	/**
	 * Creates a new <code>Board</code>.
	 */
	//@ ensures (\forall int i; i >= 0 && i <= 81; getField(i) == 0);
	public Board() {
		fields = new int[DIM * DIM];
	}
	
	//======================= Queries =======================
	/**
	 * Returns the value at a certain index.
	 * @param index index of the board.
	 * @return the value at a certain index.
	 */
	//@ requires index >= 0 && index < DIM * DIM;
	//@ ensures \result >= 0 && \result <= 9;
	/*@ pure */ public int getField(int index) {
		return fields[index];
	}
	
	/**
	 * Returns the value at a certain index with the given row and column.
	 * @param row row of the board.
	 * @param column column of the board.
	 * @return the value at a certain index with the given row and column.
	 */
	//@ requires row >= 0 && row < DIM;
	//@ requires column >= 0 && row < DIM;
	//@ ensures \result == getField(row * DIM + column);
	//@ ensures \result >= 0 && \result <= 9;
	/*@ pure */ public int getField(int row, int column) {
		return getField(row * DIM + column);
	}
	
	/**
	 * Returns <code>true</code> if there is a field with the given value in the same row at the given index.
	 * @param index index of the board.
	 * @param value value to be checked.
	 * @return <code>true</code> if there is a field with the given value in the same row at the given index;
	 * 		   <code>false</code> otherwise.
	 */
	//@ requires index >= 0 && index < DIM * DIM;
	//@ requires value >= 0 && index <= 9;
	//@ ensures (\exists int i; i >= 0 && i < DIM; getField(index / DIM, i) == value ==> \result == true);
	/*@ pure */ public boolean hasValueInRow(int index, int value) {
		int row = index / DIM;
		for (int column = 0; column < DIM; column++) {
			if (getField(row, column) == value) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns <code>true</code> if there is a field with the given value in the same column at the given index.
	 * @param index index of the board.
	 * @param value value to be checked.
	 * @return <code>true</code> if there is a field with the given value in the same column at the given index;
	 * 		   <code>false</code> otherwise.
	 */
	//@ requires index >= 0 && index < DIM * DIM;
	//@ requires value >= 0 && value <= 9;
	//@ ensures (\exists int i; i >= 0 && i < DIM; getField(i, index % DIM) == value ==> \result == true);
	/*@ pure */ public boolean hasValueInColumn(int index, int value) {
		int column = index % DIM;
		for (int row = 0; row < DIM; row++) {
			if (getField(row, column) == value) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Returns <code>true</code> if there is a field with the given value in the same block at the given index.
	 * @param index index of the board.
	 * @param value value to be checked.
	 * @return <code>true</code> if there is a field with the given value in the same block at the given index;
	 * 		   <code>false</code> otherwise;
	 */
	//@ requires index >= 0 && index < DIM * DIM;
	//@ requires value >= 0 && value <= 9;
	/*@ ensures (\forall int i; \forall int j; i >= BLOCK_SIZE * ((index / DIM) / BLOCK_SIZE) 
	             && i < BLOCK_SIZE * ((index / DIM) / BLOCK_SIZE) + BLOCK_SIZE  
	             && j >= BLOCK_SIZE * ((index % DIM) / BLOCK_SIZE)
	             && j < BLOCK_SIZE * ((index % DIM) / BLOCK_SIZE) + BLOCK_SIZE;
	             getField(i, j) == value ==> \result == true); */
	/*@ pure */ public boolean hasValueInBlock(int index, int value) {
		int row = index / DIM;
		int column = index % DIM;
		
		int blockRow = row / BLOCK_SIZE;
		int blockColumn = column / BLOCK_SIZE;
		
		for (int i = BLOCK_SIZE * blockRow; i < BLOCK_SIZE * blockRow + BLOCK_SIZE; i++) {
			for (int j = BLOCK_SIZE * blockColumn; j < BLOCK_SIZE * blockColumn + BLOCK_SIZE; j++) {
				if (getField(i, j) == value) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns <code>true</code> if there is a field with the given value in the same row, column or block
	 * 		   at the given index.
	 * @param index index of the board.
	 * @param value value to be checked.
	 * @return <code>true</code> if there is a field with the given value in the same row, column or block
	 * 		   at the given index;
	 * 		   <code>false</code> otherwise;
	 */
	//@ requires index >= 0 && index < DIM * DIM;
	//@ requires value >= 0 && value <= 9;
	/*@ ensures \result == (hasValueInRow(index, value) || hasValueInColumn(index, value) ||
	  						hasValueInBlock(index, value)); */
	/*@ pure */ public boolean hasValue(int index, int value) {
		return hasValueInRow(index, value) || hasValueInColumn(index, value) || hasValueInBlock(index, value);
	}
	
	/**
	 * Returns <code>true</code> if the field at the given index already has a value.
	 * @param index index of the board.
	 * @return <code>true</code> if the field at the given index already has a value;
	 * 		   <code>false</code> otherwise.
	 */
	//@ requires index >= 0 && index < DIM * DIM;
	//@ ensures \result == (getField(index) != 0);
	/*@ pure */ public boolean isOccupied(int index) {
		return getField(index) != 0;
	}
	
	/**
	 * Returns <code>true</code> if all fields are occupied.
	 * @return <code>true</code> if all fields are occupied;
	 * 		   <code>false</code> otherwise.
	 */
	//@ ensures (\forall int i; i >= 0 && i < DIM * DIM; getField(i) == 0 ==> \result == true);
	/*@ pure */ public boolean hasEmptyField() {
		for (int i = 0; i < DIM * DIM; i++) {
			if (!isOccupied(i)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns a <code>String</code> representation of the board.
	 * @return a <code>String</code> representation of the board.
	 */
	//@ ensures \result != null;
	/*@ pure */ public String toString() {
		String result = "";
		for (int i = 0; i < DIM; i++) {
			for (int j = 0; j < DIM; j++) {
				result += getField(i, j) + " ";
			}
			result += "\n";
		}
		return result;
	}
	
	//======================= Commands =======================
	/**
	 * Sets the value at a given index.
	 * @param index index of the board.
	 * @param value value to be set.
	 */
	//@ requires index >= 0 && index < DIM * DIM;
	//@ requires value >= 0 && value <= 9;
	//@ ensures getField(index) == value;
	public void setField(int index, int value) {
		fields[index] = value;
	}
	
	/**
	 * Sets the value at a certain index, with the given row and column.
	 * @param row row of the board.
	 * @param column column of the board.
	 * @param value value to be set.
	 */
	//@ requires row >= 0 && row < DIM;
	//@ requires column >= 0 && column < DIM;
	//@ requires value >= 0 && value <= 9;
	//@ ensures getField(row, column) == value;
	public void setField(int row, int column, int value) {
		setField(row * DIM + column, value);
	}
	
	/**
	 * Notifies the observers.
	 */
	public void finish() {
		setChanged();
		notifyObservers();
	}
}