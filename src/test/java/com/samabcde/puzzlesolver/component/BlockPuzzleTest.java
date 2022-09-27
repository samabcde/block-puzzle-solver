package com.samabcde.puzzlesolver.component;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BlockPuzzleTest {

    @ParameterizedTest
    @CsvSource(textBlock = """
            1|1
            2|2
            """, delimiterString = "|")
    public void width(int width, int expected) {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(width, 1), new String[]{});
        assertEquals(expected, blockPuzzle.getPuzzleWidth());
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            1|1
            2|2
            """, delimiterString = "|")
    public void height(int height, int expected) {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(1, height), new String[]{});
        assertEquals(expected, blockPuzzle.getPuzzleHeight());
    }

    @Test
    public void getBlocks() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(1, 1), new String[]{"1"});
        List<Block> blocks = blockPuzzle.getBlocks();
        assertEquals(1, blocks.size());
    }

    @Test
    public void testIsIntersect() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(4, 3), new String[]{"001,111", "1,1111", "111"});
        assertEquals(3, blockPuzzle.getBlocks().size());

        BlockPosition p1 = blockPuzzle.getBlockIdToBlockPositionsMap().get(0).get(0);
        BlockPosition p2 = blockPuzzle.getBlockIdToBlockPositionsMap().get(1).get(0);
        BlockPosition p3 = blockPuzzle.getBlockIdToBlockPositionsMap().get(2).get(0);
        assertTrue(p1.isIntersect(p2));
        assertTrue(p1.isIntersect(p3));

    }

    @Test
    public void testBlockPuzzle2() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(2, 2), new String[]{"1,1", "1,1"});
        assertTrue(blockPuzzle.getBlocks().size() == 2);

        BlockPosition p1 = blockPuzzle.getBlockIdToBlockPositionsMap().get(0).get(0);
        BlockPosition p2 = blockPuzzle.getBlockIdToBlockPositionsMap().get(0).get(1);

    }

}
