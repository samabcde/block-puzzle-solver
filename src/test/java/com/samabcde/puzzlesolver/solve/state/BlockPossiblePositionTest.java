package com.samabcde.puzzlesolver.solve.state;

import static org.junit.jupiter.api.Assertions.*;

import com.samabcde.puzzlesolver.component.Block;
import com.samabcde.puzzlesolver.component.BlockPosition;
import com.samabcde.puzzlesolver.component.BlockPuzzle;
import com.samabcde.puzzlesolver.component.Dimension;
import org.junit.jupiter.api.Test;

class BlockPossiblePositionTest {

    @Test
    public void getIntersectionCount() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(1, 1), new String[] {"1"});
        BlockPossiblePosition blockPossiblePosition = new BlockPossiblePosition(blockPuzzle);
        Block block = blockPuzzle.getBlocks().get(0);
        BlockPosition blockPosition = block.getBlockPositions().get(0);
        assertEquals(0, blockPossiblePosition.getIntersectionCount(blockPosition));
    }

    @Test
    public void incrementIntersectionCount() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(1, 1), new String[] {"1"});
        BlockPossiblePosition blockPossiblePosition = new BlockPossiblePosition(blockPuzzle);
        Block block = blockPuzzle.getBlocks().get(0);
        BlockPosition blockPosition = block.getBlockPositions().get(0);
        assertEquals(1, blockPossiblePosition.incrementIntersectionCount(blockPosition));
        assertEquals(1, blockPossiblePosition.getIntersectionCount(blockPosition));
        assertEquals(0, blockPossiblePosition.getPossiblePositionCount(block));
        assertFalse(blockPossiblePosition.hasPossiblePosition(block));
    }

    @Test
    public void decrementIntersectionCount() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(1, 2), new String[] {"1", "1"});
        BlockPossiblePosition blockPossiblePosition = new BlockPossiblePosition(blockPuzzle);
        Block block = blockPuzzle.getBlocks().get(0);
        BlockPosition blockPosition = block.getBlockPositions().get(0);
        blockPossiblePosition.incrementIntersectionCount(blockPosition);
        assertEquals(0, blockPossiblePosition.decrementIntersectionCount(blockPosition));
        assertEquals(0, blockPossiblePosition.getIntersectionCount(blockPosition));
        assertEquals(2, blockPossiblePosition.getPossiblePositionCount(block));
        assertTrue(blockPossiblePosition.hasPossiblePosition(block));
    }
}
