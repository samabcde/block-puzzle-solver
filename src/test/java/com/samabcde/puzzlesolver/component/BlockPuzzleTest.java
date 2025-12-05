package com.samabcde.puzzlesolver.component;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BlockPuzzleTest {

    @ParameterizedTest
    @CsvSource(
            textBlock =
                    """
                    1|1
                    2|2
                    """,
            delimiterString = "|")
    public void width(int width, int expected) {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(width, 1), new String[] {});
        assertEquals(expected, blockPuzzle.getPuzzleWidth());
    }

    @ParameterizedTest
    @CsvSource(
            textBlock =
                    """
                    1|1
                    2|2
                    """,
            delimiterString = "|")
    public void height(int height, int expected) {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(1, height), new String[] {});
        assertEquals(expected, blockPuzzle.getPuzzleHeight());
    }

    @Test
    public void getBlocks() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(1, 1), new String[] {"1"});
        List<Block> blocks = blockPuzzle.getBlocks();
        assertEquals(1, blocks.size());
    }

    @Test
    public void getSize() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(2, 2), new String[] {"1,1", "1,1"});
        assertEquals(4, blockPuzzle.getSize());
    }
}
