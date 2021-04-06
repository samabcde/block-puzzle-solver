package com.samabcde.puzzlesolver.component;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class BlockPuzzleSolverUnitTest {
    @Test
    public void testSolve() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(4, 4, new String[]{"1111", "111", "001,111", "1,1111"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve2() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(5, 5,
                new String[]{"1111", "111", "001,111", "1,1111", "111", "01,01,01,11,01"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve3a() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(6, 7, new String[]{"1,1", "1,1,1", "11,11", "01,11,1", "001,111", "11,11,01",
                "1,11,01,01", "101,111", "011,01,11", "1,1,1,1,1"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve3() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(8, 8, new String[]{"11", "111,11", "001,111", "1,1111", "111", "01,01,01,11,01",
                "111,1,1,1,1,1,1,1", "11,11,11,11,1,1,1", "111111,1,111111", "11111"

        });
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve4() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(10, 10,
                new String[]{"11", "111,11", "001,111", "1,1111", "111", "01,01,01,11,01", "111,1,1,1,1,1,1,1",
                        "11,11,11,11,1,1,1", "111111,1,111111", "11111", "0111111,11", "111111", "01,11", "1,11,11",
                        "11,11,01", "01,011,111,111"

                });
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve4A() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(10, 10,
                new String[]{"1", "1", "111,11", "001,111", "1,1111", "111", "01,01,01,11,01", "111,1,1,1,1,1,1,1",
                        "11,11,11,11,1,1,1", "111111,1,111111", "11111", "0111111,11", "111111", "01,11", "1,11,11",
                        "11,11,01", "01,011,111,111"

                });
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve5() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(12, 12,
                new String[]{"11", "111,11", "001,111", "1,1111", "111", "01,01,01,11,01", "111,1,1,1,1,1,1,1",
                        "11,11,11,11,1,1,1", "111111,1,111111", "11111", "0111111,11", "111111", "01,11", "1,11,11",
                        "11,11,01", "01,011,111,111", "0000001,0000001,0000001,0000001,0000011,1111111,0000011",
                        "111,011,011,011,01,01,01,01", "0011111,1111", "1111111"

                });
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve6() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(13, 13,
                new String[]{"11", "111,11", "001,111", "1,1111", "111", "01,01,01,11,01",
                        "0111,01,01,01,01,01,11,11,1,1,1", "11,11,11,11,1,1,1", "111111,1,111111,00111111",
                        "1,111111,1,1,1,1,1,1", "0111111,11", "111111", "01,11", "1,11,11", "11,11,01",
                        "01,011,111,111,111111", "0000001,0000001,0000001,0000001,0000011,1111111,0000011",
                        "111,011,011,011,01,01,01,01", "0011111,1111", "1111111"

                });
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve9() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(8, 8,
                new String[]{"1", "11", "01,11", "11,1", "11,01", "1111", "1,1,1,1", "11,11", "111,1", "111,01",
                        "111,001", "1,111", "01,111", "001,111", "11,011", "011,11", "1,11,1", "01,11,01"

                });
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve10() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(10, 10,
                new String[]{"1", "11", "01,11", "11,1", "11,01", "1111", "1,1,1,1", "1,1", "111,1", "111,01",
                        "111,001", "1,111", "01,111", "001,111", "11,011", "011,11", "1,11,1", "01,11,01", "01,11,1",
                        "1,11,01", "11111", "1,1,1,1,1", "1111,1", "1111,01", "1111,001", "111,101"
                });
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve11() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(12, 12,
                new String[]{"1", "11", "1,1", "111", "1,1,1", "01,11", "11,1", "11,01", "11,11", "1111", "1,1,1,1",
                        "111,1", "111,01", "111,001", "1,111", "01,111", "001,111", "11,011", "011,11", "1,11,1",
                        "01,11,01", "01,11,1", "1,11,01", "11111", "1,1,1,1,1", "1111,1", "1111,01", "1111,001",
                        "1111,0001", "111,101", "111,110", "111,011", "101,111", "110,111", "011,111", "111,1"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    @Disabled
    public void testSolve12() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(13, 13,
                new String[]{"1", "1", "1", "1", "1", "1", "1", "1", "11,1", "1", "1,1", "11", "11", "1", "1", "1",
                        "1", "11,11,111,1", "111,01", "111,001", "1,111", "01,111,001,111", "11,011,011,11",
                        "1,111111,01", "01,11,11,11,01", "111111,1,1,1,1", "1111,11111,01", "1111,1111,101",
                        "111,011,111,11,11,0111", "011,111,1,11111,111,011", "111,001,001,111", "01,1111,111111",
                        "01,111,01,01,111,001,001,111,1"

                });
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve7() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(13, 13,
                new String[]{"11", "111,11", "001,111", "1,1111", "111", "01,01,01,11,01",
                        "0111,01,01,01,01,01,11,11,1,1,1", "11,11,11,11,1,1,1", "111111,1,111,001", "111,11111",
                        "1,111111,1,1,1,1,1,1", "0111111,11", "111111", "01,11", "1,11,11", "11,11,01", "01,011,111",
                        "111,111111", "0000001,0000001,0000001,0000001,0000011,1111111,0000011",
                        "111,011,011,011,01,01,01,01", "0011111,1111", "1111111"

                });
        assertPuzzleSolvable(blockPuzzle);
    }


    @Test
    public void testSolve13() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(13, 13,
                new String[]{"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                        "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                        "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                        "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                        "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                        "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                        "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                        "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                        "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve14() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(12, 12,
                new String[]{"11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11",
                        "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11",
                        "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11",
                        "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11",
                        "11", "11", "11", "11", "11", "11", "11", "11", "11"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve15() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(12, 12,
                new String[]{"1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1",
                        "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1",
                        "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1",
                        "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1",
                        "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1",
                        "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve16() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(13, 13,
                new String[]{
                        "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11",
                        "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11",
                        "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11",
                        "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11",
                        "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11",
                        "11", "11", "11", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve17() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(8, 8,
                new String[]{"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                        "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                        "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                        "1", "1", "1", "1", "1", "1", "1", "1"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    @Disabled
    public void testSolve18() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(13, 13,
                new String[]{"1", "11", "1,1", "01,11", "11,1", "11,01", "1111", "1,1,1,1", "11,11", "111,1",
                        "111,01", "111,001", "1,111", "01,111", "001,111", "11,011", "011,11", "1,11,1", "01,11,01",
                        "01,11,1", "1,11,01", "11111", "1,1,1,1,1", "1111,1", "1111,01", "1111,001", "111,101",
                        "111,011", "111,11", "11,0111", "011,111", "1,111,1", "1,111,01", "1,111,001", "01,111,1",
                        "01,111,01", "01,111,001", "001,111,1", "001,111,01", "001,111,001"
                });
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve19() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(8, 5, new String[]{"1,1", "1,1,1", "11,01", "11,11", "011,11", "1,11,1",
                "001,111", "1111", "001,001,111", "00111,111,1",});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    @Disabled
    public void testSolve20() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(13, 13,
                new String[]{"11", "111,11", "001,111", "1,1111", "111", "01,01,01,11,01",
                        "0111,01,01,01,01,01,11,11,1,1,1", "11,11,11,11,1,1,1", "111111,1,111,001", "111,11111",
                        "1,111111", "1,1,1,1,1,1", "0111111,11", "111111", "01,11", "1,11,11", "11,11,01", "01,011,111",
                        "111,111111", "0000001,0000001,0000001,0000001,0000011,1111110", "01,11",
                        "111,011,011,011,01,01,01,01", "0011111,1111", "1111111"

                });
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve21() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(6, 6, new String[]{"11,01", "1,1,1", "011,11", "111,1", "1,1,1,1", "01,111",
                "11,11", "111,11", "001,111,1"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve22() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(10, 4, new String[]{"1,1", "11,1", "111", "11,11", "1111", "1,11,01",
                "01,01,11", "11111", "01,11,01", "011,11111"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve23() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(4, 10, new String[]{"11", "111", "1,11", "01,111", "11,01,01", "11,11",
                "01,011,11", "11,11,01", "11,01,011", "1,1,1,1,1"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve24() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(10, 4, new String[]{"11", "111", "01,11", "111,1", "01,11,11", "01,11,1",
                "1111", "11,11", "01,111", "01,11111,0001"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve25() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(5, 8, new String[]{"1,1", "111", "01,11", "01,01,11", "1,11,01", "01,11,01",
                "11,11", "11,0111,001", "1,11,111", "1111"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve26() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(8, 3,
                new String[]{"1,1", "111", "01,11", "11,11", "11,011", "111,01", "001,111"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve27() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(3, 8,
                new String[]{"11", "1,1,1", "01,11", "11,11", "1,11,01", "1,11,1", "01,01,11"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve28() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(10, 4, new String[]{"1,1", "11,1", "1,1,1", "01,11,1", "01,111", "01,01,11",
                "11,11", "1111", "1111,0001", "01,111111"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve29() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(10, 4, new String[]{"1,1", "11,01", "1,1,1", "1,111", "111,01", "1111", "11,11",
                "011,11,01", "1,11,0111", "11,0111"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve855() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(4, 10, new String[]{"11", "111", "11,11", "1,1,11", "1,11,01", "01,11,01",
                "01,111,01", "1,1,1,1", "01,01,01,11", "01,01,11,01"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve482() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(6, 8, new String[]{"1,1", "01,11", "111", "11,011", "11,11", "111,01", "1,1,11",
                "1,1,1,1", "1111,01", "111,1,1", "11,1,1,1", "11111"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve489() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(10, 5, new String[]{"11", "11,10", "01,11,1", "11,11", "111,01", "01,01,11",
                "1,1,1,1", "1,111,1", "1,1,1,11", "01,111,01", "11,01,11", "11,011,01"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve484() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(6, 8, new String[]{"11,1", "1,1,1", "1111", "11,011", "11,11", "1,11,1",
                "1,1,11", "1,11,011", "1,111,001", "11,01,11", "1001,11111"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve486() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(8, 7, new String[]{"11", "111", "01,11", "1,111,1", "1,111,001", "011,111",
                "1111,1", "111,0011", "1,1,111", "1,1111,0001", "1111,00011", "111111"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve486_2() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(9, 6, new String[]{"11,1", "1,1,1", "1111", "11,11", "11,011", "1,111",
                "101,111", "1,111,1", "111,1,1", "11,11,01", "11111", "001,001,1111,1"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve485() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(4, 12, new String[]{"1,1", "01,11", "11,11", "1,1,1,1", "11,1,1", "1,11,1",
                "01,11,11", "011,11,01", "01,01,11,01", "11,01,01,011", "11,1,11,01"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve491() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(7, 7, new String[]{"11", "01,11", "111", "1,111", "011,11", "111,01", "1111",
                "111,101", "01,1111", "001,111,001", "111,0011", "1,111,001"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve491_2() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(7, 7, new String[]{"11", "11,1", "1,1,1", "1,11,01", "111,01", "001,111",
                "11,11", "1,1,1,1", "1,1,1,11", "111,01,01", "1,111,001", "01,111,0011"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve492() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(7, 7, new String[]{"11", "1,1,1", "1,11", "11,011", "1111", "111,001", "11,11",
                "1,1111", "111,011", "111,1,1", "001,111,1", "01,011,11"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve493() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(8, 6, new String[]{"1,1", "1,11", "111", "11,11", "011,11", "01,11,01", "1111",
                "01,01,11", "1,11,11", "1,111,01", "01,01,11,01", "11,01,011"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve496() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(10, 5, new String[]{"1,1", "11,01", "11,11", "011,11", "11,01,01", "01,11,01",
                "1,1,1,1", "01,01,111", "11111", "01,11,11", "01,1111", "111,0011"});
        assertPuzzleSolvable(blockPuzzle);
    }

    private void assertPuzzleSolvable(BlockPuzzle blockPuzzle) {
        BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
        Collection<BlockPosition> solutions = solver.solve();
        assertFalse(solutions.isEmpty());
    }

}
