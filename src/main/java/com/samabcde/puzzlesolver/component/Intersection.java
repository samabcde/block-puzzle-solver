package com.samabcde.puzzlesolver.component;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class Intersection {
    final BitSet isPositionIdIntersectBitSet;
    final List<Integer> intersectPositionIds = new ArrayList<>();
    private int intersectCount;

    Intersection(int positionCount) {
        this.isPositionIdIntersectBitSet = new BitSet(positionCount);
    }

    List<Integer> getIntersectPositionIds() {
        return intersectPositionIds;
    }

    BitSet getIsPositionIdIntersectBitSet() {
        return isPositionIdIntersectBitSet;
    }

    void add(BlockPosition intersect) {
        this.intersectCount++;
        this.getIsPositionIdIntersectBitSet().set(intersect.id);
        this.getIntersectPositionIds().add(intersect.id);
    }

    int getIntersectCount() {
        return intersectCount;
    }

}