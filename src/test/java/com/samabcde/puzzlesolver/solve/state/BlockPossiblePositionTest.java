package com.samabcde.puzzlesolver.solve.state;

import com.samabcde.puzzlesolver.component.Block;
import com.samabcde.puzzlesolver.component.BlockPosition;
import com.samabcde.puzzlesolver.component.BlockPuzzle;
import com.samabcde.puzzlesolver.component.Dimension;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class BlockPossiblePositionTest {
    @Test
    public void getPossiblePositions() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(1, 1), new String[]{"1"});
        BlockPossiblePosition blockPossiblePosition = new BlockPossiblePosition(blockPuzzle);
        Block block = blockPuzzle.getBlocks().get(0);
        assertEquals(block.getBlockPositions(), blockPossiblePosition.getPossiblePositions(block));
        assertNotSame(block.getBlockPositions(), blockPossiblePosition.getPossiblePositions(block));
    }

    @Test
    public void getIntersectionCount() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(1, 1), new String[]{"1"});
        BlockPossiblePosition blockPossiblePosition = new BlockPossiblePosition(blockPuzzle);
        Block block = blockPuzzle.getBlocks().get(0);
        BlockPosition blockPosition = block.getBlockPositions().get(0);
        assertEquals(0, blockPossiblePosition.getIntersectionCount(blockPosition));
    }
}