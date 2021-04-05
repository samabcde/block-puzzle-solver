package com.samabcde.puzzlesolver.component;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class PointFillState {
    private boolean isFilled = false;
    private int fillableBlockCount = 0;
    private int fillableBlockPositionCount = 0;
    private int fillableBlockWeight = 0;
    private int[] fillablePositionCountOfBlocks;
    private final int position;

    private PointFillState(PointFillState pointFillState) {
        this.fillableBlockCount = pointFillState.fillableBlockCount;
        this.fillableBlockPositionCount = pointFillState.fillableBlockPositionCount;
        this.fillablePositionCountOfBlocks = Arrays.copyOf(pointFillState.fillablePositionCountOfBlocks,
                pointFillState.fillablePositionCountOfBlocks.length);
        this.fillableBlockWeight = pointFillState.fillableBlockWeight;
        this.isFilled = pointFillState.isFilled;
        this.position = pointFillState.position;
    }

    public PointFillState(BlockPuzzle blockPuzzle, int position) {
        fillablePositionCountOfBlocks = new int[blockPuzzle.getBlocks().size()];
        Arrays.fill(fillablePositionCountOfBlocks, 0);
        for (Block block : blockPuzzle.getBlocks()) {
            for (BlockPosition blockPosition : block.getBlockPositions()) {
                for (int fillablePoint : blockPosition.getFillablePoints()) {
                    if (fillablePoint != position) {
                        continue;
                    }
                    if (fillablePositionCountOfBlocks[block.id] == 0) {
                        fillableBlockCount++;
                        fillableBlockWeight += block.weight;
                    }
                    fillablePositionCountOfBlocks[block.id]++;
                    fillableBlockPositionCount++;
                }
            }
        }
        this.position = position;
    }


    public int getFillableBlockWeight() {
        return fillableBlockWeight;
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

    public int getPosition() {
        return position;
    }


    public boolean isFillable() {
        return this.fillableBlockCount > 0;
    }

    public List<Integer> getFillableBlockIds() {
        List<Integer> fillableBlockIds = new LinkedList<Integer>();
        for (int i = 0; i < fillablePositionCountOfBlocks.length; i++) {
            if (fillablePositionCountOfBlocks[i] > 0) {
                fillableBlockIds.add(i);
            }
        }
        return fillableBlockIds;
    }


    public PointFillState clone() {
        return new PointFillState(this);
    }


    public Boolean getIsFilled() {
        return isFilled;
    }

    public void setIsFilled(Boolean isFilled) {
        this.isFilled = isFilled;
    }

    public void addFillableBlockPosition(BlockPosition fillableBlockPosition) {
        if (fillablePositionCountOfBlocks[fillableBlockPosition.getBlock().id] == 0) {
            fillableBlockCount++;
            fillableBlockWeight += fillableBlockPosition.getBlock().weight;
        }

        fillablePositionCountOfBlocks[fillableBlockPosition.getBlock().id]++;
        fillableBlockPositionCount++;
    }

    public void removeFillableBlockPosition(BlockPosition fillableBlockPosition) {
        if (fillablePositionCountOfBlocks[fillableBlockPosition.getBlock().id] == 1) {
            fillableBlockCount--;
            fillableBlockWeight -= fillableBlockPosition.getBlock().weight;
        }

        fillablePositionCountOfBlocks[fillableBlockPosition.getBlock().id]--;
        fillableBlockPositionCount--;

    }

    static class PointFillStateComparator implements Comparator<PointFillState> {

        @Override
        public int compare(PointFillState arg0, PointFillState arg1) {
            return PointFillState.compare(arg0, arg1);

        }
    }

    @Override
    public String toString() {
        return "filled:" + this.isFilled + " position: " + this.position + " fillableBlockCount: "
                + this.fillableBlockCount + " weight " + fillableBlockWeight
                + Arrays.toString(this.fillablePositionCountOfBlocks);
    }

    static int compare(PointFillState arg0, PointFillState arg1) {
        if (arg0.isFilled != arg1.isFilled) {
            if (arg0.isFilled) {
                return -1;
            }
            return 1;
        }
        if (arg0.fillableBlockCount != arg1.fillableBlockCount) {
            return arg0.fillableBlockCount - arg1.fillableBlockCount;
        }
        if (arg0.fillableBlockPositionCount != arg1.fillableBlockPositionCount) {
            return arg0.fillableBlockPositionCount - arg1.fillableBlockPositionCount;
        }
        return arg0.position - arg1.position;
    }
}
