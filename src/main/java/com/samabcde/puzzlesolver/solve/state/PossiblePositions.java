package com.samabcde.puzzlesolver.solve.state;

import com.samabcde.puzzlesolver.component.Block;
import com.samabcde.puzzlesolver.component.BlockPosition;

import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

public class PossiblePositions {

    private final BitSet isCommonIntersectBitSet;

    private final List<BlockPosition> blockPositions;

    public PossiblePositions(List<BlockPosition> blockPositions) {
        isCommonIntersectBitSet = new BitSet(blockPositions.get(0).getIsPositionIdIntersectBitSet().size());
        this.isCommonIntersectBitSet.set(0, isCommonIntersectBitSet.size(), true);
        setCommonIntersect(isCommonIntersectBitSet, blockPositions);
        this.blockPositions = blockPositions;
    }

    private static void setCommonIntersect(BitSet isCommonIntersectBitSet, List<BlockPosition> blockPositions) {
        for (BlockPosition blockPosition : blockPositions) {
            isCommonIntersectBitSet.and(blockPosition.getIsPositionIdIntersectBitSet());
            if (isCommonIntersectBitSet.cardinality() == 0) {
                break;
            }
        }
    }

    public boolean hasNoCommonIntersect() {
        return this.isCommonIntersectBitSet.cardinality() == 0;
    }

    private boolean isIntersect(BlockPosition blockPosition) {
        return isCommonIntersectBitSet.get(blockPosition.id);
    }

    public boolean removeCommonIntersect(PossiblePositions other, BoardFillState boardFillState) {
        boolean hasChange = false;
        Iterator<BlockPosition> iterator = this.blockPositions.iterator();
        while (iterator.hasNext()) {
            BlockPosition blockPosition = iterator.next();
            if (other.isIntersect(blockPosition)) {
                hasChange = true;
                boardFillState.removeCanFillBlockPosition(blockPosition);
                iterator.remove();
            }
        }
        if (hasChange) {
            updateCommonIntersect();
        }
        return hasChange;
    }

    public boolean removeCannotFillOneBlockEmptyPoint(BoardFillState boardFillState, PointFillState remainOneBlockEmptyPoint) {
        boolean hasChange = false;
        Iterator<BlockPosition> iterator = this.blockPositions.iterator();
        while (iterator.hasNext()) {
            BlockPosition blockPosition = iterator.next();
            boolean isPointInPosition = blockPosition.canFill(remainOneBlockEmptyPoint);
            if (!isPointInPosition) {
                hasChange = true;
                boardFillState.removeCanFillBlockPosition(blockPosition);
                iterator.remove();
            }
        }
        if (hasChange) {
            updateCommonIntersect();
        }
        return hasChange;
    }

    private void updateCommonIntersect() {
        this.isCommonIntersectBitSet.set(0, isCommonIntersectBitSet.size(), true);
        setCommonIntersect(this.isCommonIntersectBitSet, blockPositions);
    }

    public boolean isEmpty() {
        return this.blockPositions.isEmpty();
    }

    public boolean isDifferentBlock(Block block) {
        if (this.blockPositions.isEmpty()) {
            return false;
        }
        return this.blockPositions.get(0).getBlock() != block;
    }
}
