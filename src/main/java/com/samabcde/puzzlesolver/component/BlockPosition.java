package com.samabcde.puzzlesolver.component;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;

public class BlockPosition implements Comparable<BlockPosition> {
    public final int id;
    final Block block;
    private final BlockPuzzle blockPuzzle;
    boolean[] isPositionIdIntersect;
    public BitSet isPositionIdIntersectBitSet;
    private final Position position;
    int intersectCount;
    private int intersectScore = Integer.MIN_VALUE;
    List<Integer> intersectPositionIds = new ArrayList<Integer>();
    int[] canFillPoints;
    private int priority;
    int hashCode = -1;

    public boolean[] getIsPositionIdIntersect() {
        return isPositionIdIntersect;
    }

    public Block getBlock() {
        return block;
    }

    public List<Integer> getIntersectPositionIds() {
        return intersectPositionIds;
    }

    public int[] getCanFillPoints() {
        return canFillPoints;
    }

    public int getIntersectScore() {
        if (intersectScore != Integer.MIN_VALUE) {
            return intersectScore;
        }
        intersectScore = 0;
        for (int i = 0; i < this.blockPuzzle.positionCount; i++) {
            if (!this.isPositionIdIntersect[i]) {
                continue;
            }
            BlockPosition blockPosition = this.blockPuzzle.blockPositionsById[i];
            double blockPriorityScore = 10;
            double blockExtraScoreFactor = 3 / (2 + blockPosition.block.priority);
            double positionExtraScoreFactor = 1;
            if (blockPosition.block.priority < this.block.priority) {
                positionExtraScoreFactor = Math.pow(10, 2 / (1 + blockPosition.priority));
            }
            int score = (int) Math.ceil(Math.pow(blockPriorityScore, blockExtraScoreFactor) * positionExtraScoreFactor);
            intersectScore += score;
        }
        return intersectScore;
    }

    public boolean isPositionIntersect(int id) {
        return this.isPositionIdIntersect[id];
    }

    public boolean isPositionIntersectAll(Collection<BlockPosition> blockPositions) {
        for (BlockPosition blockPosition : blockPositions) {
            if (!this.isPositionIntersect(blockPosition.id)) {
                return false;
            }
        }
        return true;
    }

    public int getIntersectCount() {
        return intersectCount;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


    public BlockPosition(BlockPuzzle blockPuzzle, Block block, Position position) {
        super();
        this.blockPuzzle = blockPuzzle;
        this.id = this.blockPuzzle.positionCount;
        this.blockPuzzle.positionCount++;
        this.block = block;
        this.position = position;
        this.canFillPoints = new int[block.weight];
        int pointIndex = 0;
        for (int rowIndex = this.position.y(); rowIndex < this.position.y() + this.block.height; rowIndex++) {
            for (int columnIndex = this.position.x(); columnIndex < this.position.x() + this.block.width; columnIndex++) {
                if (this.get(rowIndex, columnIndex)) {
                    this.canFillPoints[pointIndex] = this.blockPuzzle.puzzleWidth * rowIndex + columnIndex;
                    pointIndex++;
                }
            }
        }
    }

    public boolean isIntersect(BlockPosition other) {
        int startRow = Math.max(this.position.y(), other.position.y());
        int startCol = Math.max(this.position.x(), other.position.x());
        int endRow = Math.min(this.position.y() + this.block.height, other.position.y() + other.block.height);
        int endCol = Math.min(this.position.x() + this.block.width, other.position.x() + other.block.width);
        for (int i = startRow; i < endRow; i++) {
            for (int j = startCol; j < endCol; j++) {
                if (this.get(i, j) && other.get(i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Position getPosition() {
        return position;
    }

    public boolean get(int row, int col) {
        if (row - position.y() < 0 || row - position.y() > block.height - 1) {
            return false;
        }
        if (col - position.x() < 0 || col - position.x() > block.width - 1) {
            return false;
        }
        return block.get(row - position.y(), col - position.x());
    }

    public void addIntersectPosition(BlockPosition intersect) {
        this.intersectCount++;
        this.block.totalIntersectCount++;
        this.isPositionIdIntersect[intersect.id] = true;
        this.isPositionIdIntersectBitSet.set(intersect.id);
        this.intersectPositionIds.add(intersect.id);
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "position:" + position + ",block:" + System.lineSeparator() + block.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BlockPosition other = (BlockPosition) obj;
        return this.id == other.id;
    }

    @Override
    public int compareTo(BlockPosition arg0) {
        return this.id - arg0.id;
    }

}