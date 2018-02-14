package test;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.Board;

public class BoardTest {

	private Board board;
	
	@Before
	public void setup() {
		board = new Board();
	}
	
	@Test
	public void testRow() {
		assertFalse(board.hasValueInRow(0, 1));
		board.setField(1, 1);
		assertTrue(board.hasValueInRow(0, 1));
	}
	
	@Test
	public void testColumn() {
		assertFalse(board.hasValueInColumn(0, 1));
		board.setField(9, 1);
		assertTrue(board.hasValueInColumn(0, 1));
	}
	
	@Test
	public void testBlock() {
		assertFalse(board.hasValueInBlock(0, 1));
		board.setField(2, 1);
		assertTrue(board.hasValueInBlock(0, 1));
		assertTrue(board.hasValueInBlock(9, 1));
		assertTrue(board.hasValueInBlock(18, 1));
		assertFalse(board.hasValueInBlock(27, 1));
		assertTrue(board.hasValueInBlock(20, 1));
		assertFalse(board.hasValueInBlock(22, 1));
	}
}
