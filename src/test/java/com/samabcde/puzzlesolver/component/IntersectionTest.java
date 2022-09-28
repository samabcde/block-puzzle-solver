package com.samabcde.puzzlesolver.component;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntersectionTest {
    @Test
    public void add() {
        Intersection intersection = new Intersection(2);
        assertEquals(0, intersection.getIntersectCount());
        assertEquals(0, intersection.getIntersectPositionIds().size());
        assertEquals(0, intersection.getIsPositionIdIntersectBitSet().cardinality());

        BlockPosition blockPosition = new BlockPosition(new Dimension(2, 1), new Block("", 1), new Position(1, 1), 1);
        intersection.add(blockPosition);

        assertEquals(1, intersection.getIntersectCount());
        assertEquals(1, intersection.getIntersectPositionIds().size());
        assertEquals(1, intersection.getIsPositionIdIntersectBitSet().cardinality());
    }

}