package com.samabcde.puzzlesolver.component;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class BlockPositionTest {
    @Test
    public void getBlock() {
        Block block = new Block("1", 1);
        BlockPosition blockPosition = new BlockPosition(new Dimension(1, 1), block, new Position(0, 0), 0);
        assertEquals(block, blockPosition.getBlock());
        assertEquals(new Position(0, 0), blockPosition.getPosition());
    }

    @Test
    public void blockIntersectionCount() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(2, 1), new String[]{"1", "1"});
        Block block = blockPuzzle.getBlocks().get(0);
        assertEquals(2, block.totalIntersectCount);
        assertEquals(1, block.getAverageIntersectCount());
    }
}