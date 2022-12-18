package com.samabcde.puzzlesolver.solve.state;

import com.samabcde.puzzlesolver.component.Block;
import com.samabcde.puzzlesolver.component.BlockPosition;
import com.samabcde.puzzlesolver.component.BlockPuzzle;
import com.samabcde.puzzlesolver.component.Shape;

import java.util.*;

public class PointFillState {
    private boolean isFilled = false;
    private int canFillBlockCount = 0;
    private int canFillPositionCountGt1BlockCount = 0;
    private int canFillBlockPositionCount = 0;
    private int canFillBlockWeight = 0;
    private final int[] canFillPositionCountOfBlocks;
    private final int position;
    private final BlockPuzzle blockPuzzle;
    private Optional<Block> onlyBlock = Optional.empty();

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

    public Optional<Block> onlyBlock() {
        if (this.isFilled) {
            return Optional.empty();
        }
        if (canFillPositionCountGt1BlockCount > 0) {
            return Optional.empty();
        }
        Shape shape = null;
        Block block = null;
        for (int i = 0; i < canFillPositionCountOfBlocks.length; i++) {
            if (canFillPositionCountOfBlocks[i] == 0) {
                continue;
            }
            Block blockById = blockPuzzle.getBlockById(i);
            if (shape == null) {
                shape = blockById.getShape();
                block = blockById;
            } else {
                if (!shape.equals(blockById.getShape())) {
                    return Optional.empty();
                }
            }
        }
        if (shape == null) {
            return Optional.empty();
        }
        return Optional.of(block);
//        if (blocks.size() == 1 || blocks.stream().allMatch(block -> block.getShape().equals(blocks.get(0).getShape()))) {
//            return Optional.of(block);
//        }
//        return Optional.empty();
    }

    public int getCanFillBlockWeight() {
        return canFillBlockWeight;
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

    public boolean canFill() {
        return this.canFillBlockCount > 0;
    }

    public List<Integer> getCanFillBlockIds() {
        List<Integer> canFillBlockIds = new LinkedList<>();
        for (int i = 0; i < canFillPositionCountOfBlocks.length; i++) {
            if (canFillPositionCountOfBlocks[i] > 0) {
                canFillBlockIds.add(i);
            }
        }
        return canFillBlockIds;
    }

    public boolean canFillByOnlyOneBlock() {
        boolean hasOneBlock = false;
        for (int i = 0; i < canFillPositionCountOfBlocks.length; i++) {
            if (canFillPositionCountOfBlocks[i] > 0) {
                if (hasOneBlock) {
                    return false;
                }
                hasOneBlock = true;
            }
        }
        return hasOneBlock;
    }

    public boolean canNotFillByAnyBlock() {
        for (int i = 0; i < canFillPositionCountOfBlocks.length; i++) {
            if (canFillPositionCountOfBlocks[i] > 0) {
                return false;
            }
        }
        return true;
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

    public void addCanFillBlockPosition(BlockPosition canFillBlockPosition) {
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
    }

    public void removeCanFillBlockPosition(BlockPosition canFillBlockPosition) {
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

    }

    static class PointFillStateComparator implements Comparator<PointFillState> {

        @Override
        public int compare(PointFillState arg0, PointFillState arg1) {
            return PointFillState.compare(arg0, arg1);

        }
    }

    @Override
    public String toString() {
        return "filled:" + this.isFilled + " position: " + this.position + " canFillBlockCount: "
                + this.canFillBlockCount + " weight " + canFillBlockWeight
                + Arrays.toString(this.canFillPositionCountOfBlocks);
    }

    static int compare(PointFillState arg0, PointFillState arg1) {
        if (arg0.isFilled != arg1.isFilled) {
            if (arg0.isFilled) {
                return -1;
            }
            return 1;
        }
        if (arg0.canFillBlockCount != arg1.canFillBlockCount) {
            return arg0.canFillBlockCount - arg1.canFillBlockCount;
        }
        if (arg0.canFillBlockPositionCount != arg1.canFillBlockPositionCount) {
            return arg0.canFillBlockPositionCount - arg1.canFillBlockPositionCount;
        }
        return arg0.position - arg1.position;
    }
}
