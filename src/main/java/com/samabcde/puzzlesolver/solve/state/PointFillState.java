package com.samabcde.puzzlesolver.solve.state;

import com.samabcde.puzzlesolver.component.Block;
import com.samabcde.puzzlesolver.component.BlockPosition;
import com.samabcde.puzzlesolver.component.BlockPuzzle;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class PointFillState {
    private boolean isFilled = false;
    private int canFillBlockCount = 0;
    private int canFillPositionCountGt1BlockCount = 0;
    private int canFillBlockPositionCount = 0;
    private int canFillBlockWeight = 0;
    private final int[] canFillPositionCountOfBlocks;
    private final int position;
    private final BlockPuzzle blockPuzzle;

    private PointFillState(PointFillState pointFillState) {
        this.canFillBlockCount = pointFillState.canFillBlockCount;
        this.canFillBlockPositionCount = pointFillState.canFillBlockPositionCount;
        this.canFillPositionCountOfBlocks = Arrays.copyOf(pointFillState.canFillPositionCountOfBlocks,
                pointFillState.canFillPositionCountOfBlocks.length);
        this.canFillBlockWeight = pointFillState.canFillBlockWeight;
        this.isFilled = pointFillState.isFilled;
        this.position = pointFillState.position;
        this.blockPuzzle = pointFillState.blockPuzzle;
        this.canFillPositionCountGt1BlockCount = pointFillState.canFillPositionCountGt1BlockCount;
    }

    public PointFillState(BlockPuzzle blockPuzzle, int position) {
        canFillPositionCountOfBlocks = new int[blockPuzzle.getBlocks().size()];
        Arrays.fill(canFillPositionCountOfBlocks, 0);
        for (Block block : blockPuzzle.getBlocks()) {
            for (BlockPosition blockPosition : block.getBlockPositions()) {
                if (!blockPosition.canFill(position)) {
                    continue;
                }
                if (canFillPositionCountOfBlocks[block.id] == 0) {
                    canFillBlockCount++;
                    canFillBlockWeight += block.getWeight();
                }
                canFillPositionCountOfBlocks[block.id]++;
                if (canFillPositionCountOfBlocks[block.id] == 2) {
                    canFillPositionCountGt1BlockCount++;
                }
                canFillBlockPositionCount++;
            }
        }
        this.position = position;
        this.blockPuzzle = blockPuzzle;
    }

    public boolean canOnlyFillByWeight1Block() {
        return canFillBlockCount > 0 && canFillBlockCount == canFillBlockWeight;
    }

    public int getPosition() {
        return position;
    }

    public boolean canFill() {
        return this.canFillBlockCount > 0;
    }

    public boolean canFillByOnlyOneBlock() {
        return this.canFillBlockCount == 1;
    }

    public int getFirstCanFillBlockId() {
        for (int i = 0; i < canFillPositionCountOfBlocks.length; i++) {
            if (canFillPositionCountOfBlocks[i] > 0) {
                return i;
            }
        }
        throw new NoSuchElementException("no can fill block id");
    }

    public PointFillState copy() {
        return new PointFillState(this);
    }

    public Boolean getIsFilled() {
        return isFilled;
    }

    public void setIsFilled(Boolean isFilled) {
        this.isFilled = isFilled;
    }

    public boolean addCanFillBlockPosition(BlockPosition canFillBlockPosition) {
        boolean originalCallFill = canFill();
        if (canFillPositionCountOfBlocks[canFillBlockPosition.getBlock().id] == 0) {
            canFillBlockCount++;
            canFillBlockWeight += canFillBlockPosition.getBlock().getWeight();
        }
        canFillPositionCountOfBlocks[canFillBlockPosition.getBlock().id]++;
        // from 1 -> 2
        if (canFillPositionCountOfBlocks[canFillBlockPosition.getBlock().id] == 2) {
            canFillPositionCountGt1BlockCount++;
        }
        canFillBlockPositionCount++;
        return !this.isFilled && !originalCallFill && canFill();
    }

    public boolean removeCanFillBlockPosition(BlockPosition canFillBlockPosition) {
        boolean originalCallFill = canFill();
        if (canFillPositionCountOfBlocks[canFillBlockPosition.getBlock().id] == 1) {
            canFillBlockCount--;
            canFillBlockWeight -= canFillBlockPosition.getBlock().getWeight();
        }
        canFillPositionCountOfBlocks[canFillBlockPosition.getBlock().id]--;
        // from 2 -> 1
        if (canFillPositionCountOfBlocks[canFillBlockPosition.getBlock().id] == 1) {
            canFillPositionCountGt1BlockCount--;
        }
        canFillBlockPositionCount--;
        return !this.isFilled && originalCallFill && !canFill();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + position;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PointFillState other = (PointFillState) obj;
        if (position != other.position)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "filled:" + this.isFilled + " position: " + this.position + " canFillBlockCount: "
                + this.canFillBlockCount + " weight " + canFillBlockWeight
                + Arrays.toString(this.canFillPositionCountOfBlocks);
    }
}
