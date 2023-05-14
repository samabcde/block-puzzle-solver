package com.samabcde.puzzlesolver.solve.state;

import com.samabcde.puzzlesolver.component.Block;
import com.samabcde.puzzlesolver.component.BlockPosition;
import com.samabcde.puzzlesolver.component.BlockPuzzle;

import java.util.*;
import java.util.function.Predicate;

public class BlockPossiblePosition {
    private final int[] possiblePositionCountOfBlocks;
    private final int[] intersectionCountOfBlockPositions;
    private final int[] addedPositionOfBlocks;

    public BlockPossiblePosition(BlockPuzzle blockPuzzle) {
        this.intersectionCountOfBlockPositions = new int[blockPuzzle.getPositionCount()];
        List<Block> blocks = blockPuzzle.getBlocks();
        this.addedPositionOfBlocks = new int[blocks.size()];
        this.possiblePositionCountOfBlocks = new int[blocks.size()];
        for (Block block : blocks) {
            this.possiblePositionCountOfBlocks[block.id] = block.getPositionIdTo() - block.getPositionIdFrom() + 1;
            this.addedPositionOfBlocks[block.id] = -1;
        }

    }

    private BlockPossiblePosition(BlockPossiblePosition blockPossiblePosition) {
        this.intersectionCountOfBlockPositions = Arrays.copyOf(blockPossiblePosition.intersectionCountOfBlockPositions,
                blockPossiblePosition.intersectionCountOfBlockPositions.length);
        this.possiblePositionCountOfBlocks = Arrays.copyOf(blockPossiblePosition.possiblePositionCountOfBlocks,
                blockPossiblePosition.possiblePositionCountOfBlocks.length);
        this.addedPositionOfBlocks = Arrays.copyOf(blockPossiblePosition.addedPositionOfBlocks,
                blockPossiblePosition.addedPositionOfBlocks.length);
    }

    public boolean hasPossiblePosition(Block block, BoardFillState boardFillState) {
        Predicate<BlockPosition> onlyBlockFilter = onlyBlockFilter(block, boardFillState);
        List<BlockPosition> blockPositions = block.getBlockPositions();
        int positionPriorityFrom = this.getAddedPositionOfBlocks()[block.id] + 1;
        int positionPriorityTo = blockPositions.size() - 1;

        for (int i = positionPriorityFrom; i <= positionPriorityTo; i++) {
            if (this.getIntersectionCountOfBlockPositions()[blockPositions.get(i).id] > 0) {
                continue;
            }
            if (!onlyBlockFilter.test(blockPositions.get(i))) {
                continue;
            }
            if (this.getPossiblePositionCountOfBlocks()[block.id] == 0) {
                throw new IllegalStateException("intersection count is 0 but possible position count is also 0");
            }
            return true;
        }
        return false;
    }

    public BlockPosition pollNextPossiblePosition(Block block, BoardFillState boardFillState) {
        Predicate<BlockPosition> onlyBlockFilter = onlyBlockFilter(block, boardFillState);
        List<BlockPosition> blockPositions = block.getBlockPositions();

        int positionPriorityFrom = this.addedPositionOfBlocks[block.id] + 1;
        int positionPriorityTo = blockPositions.size() - 1;

        for (int i = positionPriorityFrom; i <= positionPriorityTo; i++) {
            if (!onlyBlockFilter.test(blockPositions.get(i))) {
                continue;
            }
            if (getIntersectionCount(blockPositions.get(i)) == 0) {
                getAddedPositionOfBlocks()[block.id] = i;
                return blockPositions.get(i);
            }
        }
        throw new NoSuchElementException("No possible position to choose");
    }

    public PossiblePositions getPossiblePositions(Block block, BoardFillState boardFillState) {
        Predicate<BlockPosition> onlyBlockFilter = onlyBlockFilter(block, boardFillState);
        List<BlockPosition> possibleBlockPositions = new ArrayList<>(getPossiblePositionCount(block));
        List<BlockPosition> blockPositions = block.getBlockPositions();

        int positionPriorityFrom = this.addedPositionOfBlocks[block.id] + 1;
        int positionPriorityTo = blockPositions.size() - 1;

        for (int i = positionPriorityFrom; i <= positionPriorityTo; i++) {
            if (!onlyBlockFilter.test(blockPositions.get(i))) {
                // TODO remove properly
                if (getIntersectionCount(blockPositions.get(i)) == 0) {
                    boardFillState.removeCanFillBlockPosition(blockPositions.get(i));
                }
                continue;
            }
            if (getIntersectionCount(blockPositions.get(i)) == 0) {
                possibleBlockPositions.add(blockPositions.get(i));
            }
        }
        return new PossiblePositions(possibleBlockPositions);
    }

    private Predicate<BlockPosition> onlyBlockFilter(Block block, BoardFillState boardFillState) {
        Optional<PointFillState> pointWithOnly1Block = boardFillState.getPointWithOnly1Block();
        if (pointWithOnly1Block.isEmpty()) {
            return (blockPosition) -> true;
        }
        Block onlyBlock = pointWithOnly1Block.get().onlyBlock().get();
        if (block != onlyBlock) {
            return (blockPosition) -> true;
        }
        return (blockPosition) -> blockPosition.canFill(pointWithOnly1Block.get());
    }


    public BlockPossiblePosition copy() {
        return new BlockPossiblePosition(this);
    }

    // possible if not in solution or no intersect
    private int[] getPossiblePositionCountOfBlocks() {
        return possiblePositionCountOfBlocks;
    }

    public int getPossiblePositionCount(Block block) {
        return possiblePositionCountOfBlocks[block.id];
    }

    private int[] getIntersectionCountOfBlockPositions() {
        return intersectionCountOfBlockPositions;
    }

    public int getIntersectionCount(BlockPosition blockPosition) {
        return getIntersectionCountOfBlockPositions()[blockPosition.id];
    }

    public int incrementIntersectionCount(BlockPosition blockPosition) {
        this.getIntersectionCountOfBlockPositions()[blockPosition.id]++;
        if (getIntersectionCount(blockPosition) == 1) {
            getPossiblePositionCountOfBlocks()[blockPosition.getBlock().id]--;
        }
        return getIntersectionCount(blockPosition);
    }

    public int decrementIntersectionCount(BlockPosition blockPosition) {
        getIntersectionCountOfBlockPositions()[blockPosition.id]--;
        if (getIntersectionCountOfBlockPositions()[blockPosition.id] < 0) {
            throw new IllegalStateException("intersection count must not be negative");
        }
        if (getIntersectionCount(blockPosition) == 0) {
            getPossiblePositionCountOfBlocks()[blockPosition.getBlock().id]++;
        }
        return getIntersectionCount(blockPosition);
    }

    // check which position is added
    private int[] getAddedPositionOfBlocks() {
        return addedPositionOfBlocks;
    }

    public void resetAddedPositionPriority(Block block) {
        addedPositionOfBlocks[block.id] = -1;
    }

}
