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
}