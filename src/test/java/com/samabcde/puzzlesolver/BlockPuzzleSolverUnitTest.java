package com.samabcde.puzzlesolver;

import java.util.Collection;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BlockPuzzleSolverUnitTest {
	BlockPuzzle blockPuzzle;

	@Test
	public void testSolve() {
		blockPuzzle = new BlockPuzzle(4, 4, new String[] { "1111", "111", "001,111", "1,1111" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
	}

	@Test
	public void testSolve2() {
		blockPuzzle = new BlockPuzzle(5, 5,
				new String[] { "1111", "111", "001,111", "1,1111", "111", "01,01,01,11,01" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
	}

	@Test
	public void testSolve3a() {
		blockPuzzle = new BlockPuzzle(6, 7, new String[] { "1,1", "1,1,1", "11,11", "01,11,1", "001,111", "11,11,01",
				"1,11,01,01", "101,111", "011,01,11", "1,1,1,1,1" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
	}

	@Test
	public void testSolve3() {
		blockPuzzle = new BlockPuzzle(8, 8, new String[] { "11", "111,11", "001,111", "1,1111", "111", "01,01,01,11,01",
				"111,1,1,1,1,1,1,1", "11,11,11,11,1,1,1", "111111,1,111111", "11111"

		});
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
	}

	@Test
	public void testSolve4() {
		blockPuzzle = new BlockPuzzle(10, 10,
				new String[] { "11", "111,11", "001,111", "1,1111", "111", "01,01,01,11,01", "111,1,1,1,1,1,1,1",
						"11,11,11,11,1,1,1", "111111,1,111111", "11111", "0111111,11", "111111", "01,11", "1,11,11",
						"11,11,01", "01,011,111,111"

				});
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
	}

	@Test
	public void testSolve4A() {
		blockPuzzle = new BlockPuzzle(10, 10,
				new String[] { "1", "1", "111,11", "001,111", "1,1111", "111", "01,01,01,11,01", "111,1,1,1,1,1,1,1",
						"11,11,11,11,1,1,1", "111111,1,111111", "11111", "0111111,11", "111111", "01,11", "1,11,11",
						"11,11,01", "01,011,111,111"

				});
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
	}

	@Test
	public void testSolve5() {
		blockPuzzle = new BlockPuzzle(12, 12,
				new String[] { "11", "111,11", "001,111", "1,1111", "111", "01,01,01,11,01", "111,1,1,1,1,1,1,1",
						"11,11,11,11,1,1,1", "111111,1,111111", "11111", "0111111,11", "111111", "01,11", "1,11,11",
						"11,11,01", "01,011,111,111", "0000001,0000001,0000001,0000001,0000011,1111111,0000011",
						"111,011,011,011,01,01,01,01", "0011111,1111", "1111111"

				});
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
	}

	@Test
	public void testSolve6() {
		blockPuzzle = new BlockPuzzle(13, 13,
				new String[] { "11", "111,11", "001,111", "1,1111", "111", "01,01,01,11,01",
						"0111,01,01,01,01,01,11,11,1,1,1", "11,11,11,11,1,1,1", "111111,1,111111,00111111",
						"1,111111,1,1,1,1,1,1", "0111111,11", "111111", "01,11", "1,11,11", "11,11,01",
						"01,011,111,111,111111", "0000001,0000001,0000001,0000001,0000011,1111111,0000011",
						"111,011,011,011,01,01,01,01", "0011111,1111", "1111111"

				});
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
		for (BlockPosition solution : solutions) {

		}
	}

	@Test
	public void testSolve9() {
		blockPuzzle = new BlockPuzzle(8, 8,
				new String[] { "1", "11", "01,11", "11,1", "11,01", "1111", "1,1,1,1", "11,11", "111,1", "111,01",
						"111,001", "1,111", "01,111", "001,111", "11,011", "011,11", "1,11,1", "01,11,01"

				});
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
		for (BlockPosition solution : solutions) {

		}
	}

	@Test
	@Disabled
	public void testSolve10() {
		blockPuzzle = new BlockPuzzle(10, 10,
				new String[] { "1", "11", "01,11", "11,1", "11,01", "1111", "1,1,1,1", "1,1", "111,1", "111,01",
						"111,001", "1,111", "01,111", "001,111", "11,011", "011,11", "1,11,1", "01,11,01", "01,11,1",
						"1,11,01", "11111", "1,1,1,1,1", "1111,1", "1111,01", "1111,001", "111,101"

				});
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
	}

	@Test
	@Disabled
	public void testSolve11() {
		blockPuzzle = new BlockPuzzle(12, 12,
				new String[] { "1", "11", "1,1", "111", "1,1,1", "01,11", "11,1", "11,01", "11,11", "1111", "1,1,1,1",
						"111,1", "111,01", "111,001", "1,111", "01,111", "001,111", "11,011", "011,11", "1,11,1",
						"01,11,01", "01,11,1", "1,11,01", "11111", "1,1,1,1,1", "1111,1", "1111,01", "1111,001",
						"1111,0001", "111,101", "111,110", "111,011", "101,111", "110,111", "011,111", "111,1" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
	}

	@Test
	@Disabled
	public void testSolve12() {
		blockPuzzle = new BlockPuzzle(13, 13,
				new String[] { "1", "1", "1", "1", "1", "1", "1", "1", "11,1", "1", "1,1", "11", "11", "1", "1", "1",
						"1", "11,11,111,1", "111,01", "111,001", "1,111", "01,111,001,111", "11,011,011,11",
						"1,111111,01", "01,11,11,11,01", "111111,1,1,1,1", "1111,11111,01", "1111,1111,101",
						"111,011,111,11,11,0111", "011,111,1,11111,111,011", "111,001,001,111", "01,1111,111111",
						"01,111,01,01,111,001,001,111,1"

				});
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
		for (BlockPosition solution : solutions) {

		}

	}

	// @Test
	// public void testSolve12b() {
	// blockPuzzle = new BlockPuzzle(13, 13,
	// new String[] { "1", "11", "1,1", "111", "1,1,1", "01,11", "11,1",
	// "11,01", "11,11", "1111", "1,1,1,1",
	// "111,1", "111,01", "111,001", "1,111", "01,111", "001,111", "11,011",
	// "011,11", "1,11,1",
	// "01,11,01", "01,11,1", "1,11,01", "11111", "1,1,1,1,1", "1111,1",
	// "1111,01", "1111,001",
	// "1111,0001", "111,101", "111,110", "111,011", "101,111", "110,111",
	// "011,111", "111,1"
	// ,"1,111,1","1,11,11", "1,11101", "1,111,001", "11,11,1",});
	// BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
	// Collection<BlockPosition> solutions = solver.solve();
	// assertTrue(!solutions.isEmpty());
	// for (BlockPosition solution : solutions) {
	//
	// }
	// }

	@Test
	@Disabled
	public void testSolve7() {
		blockPuzzle = new BlockPuzzle(13, 13,
				new String[] { "11", "111,11", "001,111", "1,1111", "111", "01,01,01,11,01",
						"0111,01,01,01,01,01,11,11,1,1,1", "11,11,11,11,1,1,1", "111111,1,111,001", "111,11111",
						"1,111111,1,1,1,1,1,1", "0111111,11", "111111", "01,11", "1,11,11", "11,11,01", "01,011,111",
						"111,111111", "0000001,0000001,0000001,0000001,0000011,1111111,0000011",
						"111,011,011,011,01,01,01,01", "0011111,1111", "1111111"

				});
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
		for (BlockPosition solution : solutions) {

		}
	}

	@Test
	@Disabled
	public void testSolve20() {
		blockPuzzle = new BlockPuzzle(13, 13,
				new String[] { "11", "111,11", "001,111", "1,1111", "111", "01,01,01,11,01",
						"0111,01,01,01,01,01,11,11,1,1,1", "11,11,11,11,1,1,1", "111111,1,111,001", "111,11111",
						"1,111111", "1,1,1,1,1,1", "0111111,11", "111111", "01,11", "1,11,11", "11,11,01", "01,011,111",
						"111,111111", "0000001,0000001,0000001,0000001,0000011,1111110", "01,11",
						"111,011,011,011,01,01,01,01", "0011111,1111", "1111111"

				});
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
		for (BlockPosition solution : solutions) {

		}
	}

	@Test
	public void testSolve13() {
		blockPuzzle = new BlockPuzzle(13, 13,
				new String[] { "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
						"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
						"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
						"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
						"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
						"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
						"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
						"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
						"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
		for (BlockPosition solution : solutions) {

		}
	}

	@Test
	public void testSolve14() {
		blockPuzzle = new BlockPuzzle(12, 12,
				new String[] { "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11",
						"11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11",
						"11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11",
						"11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11",
						"11", "11", "11", "11", "11", "11", "11", "11", "11" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
		for (BlockPosition solution : solutions) {

		}
	}

	@Test
	public void testSolve15() {
		blockPuzzle = new BlockPuzzle(12, 12,
				new String[] { "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1",
						"1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1",
						"1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1",
						"1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1",
						"1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1",
						"1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
		for (BlockPosition solution : solutions) {

		}
	}

	@Test
	@Disabled
	public void testSolve16() {
		blockPuzzle = new BlockPuzzle(13, 13,
				new String[] { "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11",
						"11", "11", "11", "1", "11", "11", "11", "11", "11", "11", "11", "11", "11", "1", "11", "11",
						"11", "11", "11", "11", "11", "11", "11", "1", "11", "11", "11", "11", "11", "11", "11", "11",
						"11", "1", "11", "11", "11", "11", "11", "11", "11", "11", "11", "1", "11", "11", "11", "11",
						"11", "11", "11", "11", "11", "1", "11", "11", "11", "11", "11", "11", "11", "11", "11", "1",
						"11", "11", "11", "11", "11", "11", "11", "11", "11" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
		for (BlockPosition solution : solutions) {

		}
	}

	@Test
	public void testSolve17() {
		blockPuzzle = new BlockPuzzle(8, 8,
				new String[] { "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
						"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
						"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
						"1", "1", "1", "1", "1", "1", "1", "1" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
		for (BlockPosition solution : solutions) {

		}
	}

	@Test
	@Disabled
	public void testSolve18() {
		blockPuzzle = new BlockPuzzle(13, 13,
				new String[] { "1", "11", "1,1", "01,11", "11,1", "11,01", "1111", "1,1,1,1", "11,11", "111,1",
						"111,01", "111,001", "1,111", "01,111", "001,111", "11,011", "011,11", "1,11,1", "01,11,01",
						"01,11,1", "1,11,01", "11111", "1,1,1,1,1", "1111,1", "1111,01", "1111,001", "111,101",
						"111,011", "111,11", "11,0111", "011,111", "1,111,1", "1,111,01", "1,111,001", "01,111,1",
						"01,111,01", "01,111,001", "001,111,1", "001,111,01", "001,111,001"

				});
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
		for (BlockPosition solution : solutions) {

		}
	}

	@Test
	public void testSolve19() {
		blockPuzzle = new BlockPuzzle(8, 5, new String[] { "1,1", "1,1,1", "11,01", "11,11", "011,11", "1,11,1",
				"001,111", "1111", "001,001,111", "00111,111,1", });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
		for (BlockPosition solution : solutions) {

		}
	}

	@Test
	public void testSolve21() {
		blockPuzzle = new BlockPuzzle(6, 6, new String[] { "11,01", "1,1,1", "011,11", "111,1", "1,1,1,1", "01,111",
				"11,11", "111,11", "001,111,1" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
		for (BlockPosition solution : solutions) {

		}
	}

	@Test
	public void testSolve22() {
		blockPuzzle = new BlockPuzzle(10, 4, new String[] { "1,1", "11,1", "111", "11,11", "1111", "1,11,01",
				"01,01,11", "11111", "01,11,01", "011,11111" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());

	}

	@Test
	public void testSolve23() {
		blockPuzzle = new BlockPuzzle(4, 10, new String[] { "11", "111", "1,11", "01,111", "11,01,01", "11,11",
				"01,011,11", "11,11,01", "11,01,011", "1,1,1,1,1" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());

	}

	@Test
	public void testSolve24() {
		blockPuzzle = new BlockPuzzle(10, 4, new String[] { "11", "111", "01,11", "111,1", "01,11,11", "01,11,1",
				"1111", "11,11", "01,111", "01,11111,0001" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());

	}

	@Test
	public void testSolve25() {
		blockPuzzle = new BlockPuzzle(5, 8, new String[] { "1,1", "111", "01,11", "01,01,11", "1,11,01", "01,11,01",
				"11,11", "11,0111,001", "1,11,111", "1111" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());

	}

	@Test
	public void testSolve26() {
		blockPuzzle = new BlockPuzzle(8, 3,
				new String[] { "1,1", "111", "01,11", "11,11", "11,011", "111,01", "001,111" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());

	}

	@Test
	public void testSolve27() {
		blockPuzzle = new BlockPuzzle(3, 8,
				new String[] { "11", "1,1,1", "01,11", "11,11", "1,11,01", "1,11,1", "01,01,11" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());

	}

	@Test
	public void testSolve28() {
		blockPuzzle = new BlockPuzzle(10, 4, new String[] { "1,1", "11,1", "1,1,1", "01,11,1", "01,111", "01,01,11",
				"11,11", "1111", "1111,0001", "01,111111" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());

	}

	@Test
	public void testSolve29() {
		blockPuzzle = new BlockPuzzle(10, 4, new String[] { "1,1", "11,01", "1,1,1", "1,111", "111,01", "1111", "11,11",
				"011,11,01", "1,11,0111", "11,0111" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());

	}

	@Test
	public void testSolve855() {
		blockPuzzle = new BlockPuzzle(4, 10, new String[] { "11", "111", "11,11", "1,1,11", "1,11,01", "01,11,01",
				"01,111,01", "1,1,1,1", "01,01,01,11", "01,01,11,01" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
	}

	@Test
	public void testSolve482() {
		blockPuzzle = new BlockPuzzle(6, 8, new String[] { "1,1", "01,11", "111", "11,011", "11,11", "111,01", "1,1,11",
				"1,1,1,1", "1111,01", "111,1,1", "11,1,1,1", "11111" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
	}

	@Test
	public void testSolve489() {
		blockPuzzle = new BlockPuzzle(10, 5, new String[] { "11", "11,10", "01,11,1", "11,11", "111,01", "01,01,11",
				"1,1,1,1", "1,111,1", "1,1,1,11", "01,111,01", "11,01,11", "11,011,01" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
	}

	@Test
	public void testSolve484() {
		blockPuzzle = new BlockPuzzle(6, 8, new String[] { "11,1", "1,1,1", "1111", "11,011", "11,11", "1,11,1",
				"1,1,11", "1,11,011", "1,111,001", "11,01,11", "1001,11111" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
	}

	@Test
	public void testSolve486() {
		blockPuzzle = new BlockPuzzle(8, 7, new String[] { "11", "111", "01,11", "1,111,1", "1,111,001", "011,111",
				"1111,1", "111,0011", "1,1,111", "1,1111,0001", "1111,00011", "111111" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
	}

	@Test
	public void testSolve486_2() {
		blockPuzzle = new BlockPuzzle(9, 6, new String[] { "11,1", "1,1,1", "1111", "11,11", "11,011", "1,111",
				"101,111", "1,111,1", "111,1,1", "11,11,01", "11111", "001,001,1111,1" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
	}

	@Test
	public void testSolve485() {
		blockPuzzle = new BlockPuzzle(4, 12, new String[] { "1,1", "01,11", "11,11", "1,1,1,1", "11,1,1", "1,11,1",
				"01,11,11", "011,11,01", "01,01,11,01", "11,01,01,011", "11,1,11,01" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
	}

	@Test
	public void testSolve491() {
		blockPuzzle = new BlockPuzzle(7, 7, new String[] { "11", "01,11", "111", "1,111", "011,11", "111,01", "1111",
				"111,101", "01,1111", "001,111,001", "111,0011", "1,111,001" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
	}

	@Test
	public void testSolve491_2() {
		blockPuzzle = new BlockPuzzle(7, 7, new String[] { "11", "11,1", "1,1,1", "1,11,01", "111,01", "001,111",
				"11,11", "1,1,1,1", "1,1,1,11", "111,01,01", "1,111,001", "01,111,0011" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
	}

	@Test
	public void testSolve492() {
		blockPuzzle = new BlockPuzzle(7, 7, new String[] { "11", "1,1,1", "1,11", "11,011", "1111", "111,001", "11,11",
				"1,1111", "111,011", "111,1,1", "001,111,1", "01,011,11" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
	}

	@Test
	public void testSolve493() {
		blockPuzzle = new BlockPuzzle(8, 6, new String[] { "1,1", "1,11", "111", "11,11", "011,11", "01,11,01", "1111",
				"01,01,11", "1,11,11", "1,111,01", "01,01,11,01", "11,01,011" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
	}

	@Test
	public void testSolve496() {
		blockPuzzle = new BlockPuzzle(10, 5, new String[] { "1,1", "11,01", "11,11", "011,11", "11,01,01", "01,11,01",
				"1,1,1,1", "01,01,111", "11111", "01,11,11", "01,1111", "111,0011" });
		BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
		Collection<BlockPosition> solutions = solver.solve();
		assertTrue(!solutions.isEmpty());
	}
}
