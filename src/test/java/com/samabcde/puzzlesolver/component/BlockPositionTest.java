package com.samabcde.puzzlesolver.component;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


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

    @Test
    public void isIntersect() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(4, 3), new String[]{"001,111", "1,1111", "111"});
        assertEquals(3, blockPuzzle.getBlocks().size());
        BlockPosition p1 = blockPuzzle.getBlockIdToBlockPositionsMap().get(0).get(0);
        BlockPosition p2 = blockPuzzle.getBlockIdToBlockPositionsMap().get(1).get(0);
        BlockPosition p3 = blockPuzzle.getBlockIdToBlockPositionsMap().get(2).get(0);
        assertTrue(p1.isIntersect(p2));
        assertTrue(p1.isIntersect(p3));

    }

    @Test
    public void toStringTest() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(1, 1), new String[]{"1"});
        assertEquals("""
                position: y:0,x:0, block: 
                1""", blockPuzzle.getBlockPositionById(0).toString());
    }
}