package com.samabcde.puzzlesolver.solve.state;

import com.samabcde.puzzlesolver.component.Block;
import com.samabcde.puzzlesolver.component.BlockPosition;
import com.samabcde.puzzlesolver.component.BlockPuzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class BlockPossiblePosition {
    private final BlockPuzzle blockPuzzle;
    private final int[] possiblePositionCountOfBlocks;
    private final int[] intersectionCountOfBlockPositions;
    private final int[] addedPositionOfBlocks;
    // TODO add BlockCommonIntersection

    public BlockPossiblePosition(BlockPuzzle blockPuzzle) {
        this.blockPuzzle = blockPuzzle;
        this.intersectionCountOfBlockPositions = new int[blockPuzzle.getPositionCount()];
        List<Block> blocks = blockPuzzle.getBlocks();
        this.addedPositionOfBlocks = new int[blocks.size()];
        this.possiblePositionCountOfBlocks = new int[blocks.size()];
        for (Block block : blocks) {
            this.possiblePositionCountOfBlocks[block.id] = block.getPositionIdTo() - block.getPositionIdFrom() + 1;
            this.addedPositionOfBlocks[block.id] = -1;
        }
    }

    public boolean hasPossiblePosition(Block block) {
        List<BlockPosition> blockPositions = block.getBlockPositions();
        int positionPriorityFrom = this.getAddedPositionOfBlocks()[block.id] + 1;
        int positionPriorityTo = blockPositions.size() - 1;

        for (int i = positionPriorityFrom; i <= positionPriorityTo; i++) {
            if (this.getIntersectionCount(blockPositions.get(i)) > 0) {
                continue;
            }
            if (this.getPossiblePositionCount(block) == 0) {
                throw new IllegalStateException("intersection count is 0 but possible position count is also 0");
            }
            return true;
        }
        return false;
    }

    public BlockPosition pollNextPossiblePosition(Block block) {
        List<BlockPosition> blockPositions = block.getBlockPositions();

        int positionPriorityFrom = this.addedPositionOfBlocks[block.id] + 1;
        int positionPriorityTo = blockPositions.size() - 1;

        for (int i = positionPriorityFrom; i <= positionPriorityTo; i++) {
            if (getIntersectionCount(blockPositions.get(i)) == 0) {
                getAddedPositionOfBlocks()[block.id] = i;
                return blockPositions.get(i);
            }
        }
        throw new NoSuchElementException("No possible position to choose");
    }

    public PossiblePositions getPossiblePositions(Block block) {
        List<BlockPosition> possibleBlockPositions = new ArrayList<>(getPossiblePositionCount(block));
        List<BlockPosition> blockPositions = block.getBlockPositions();

        int positionPriorityFrom = this.addedPositionOfBlocks[block.id] + 1;
        int positionPriorityTo = blockPositions.size() - 1;

        for (int i = positionPriorityFrom; i <= positionPriorityTo; i++) {
            if (getIntersectionCount(blockPositions.get(i)) == 0) {
                possibleBlockPositions.add(blockPositions.get(i));
            }
        }
        return new PossiblePositions(possibleBlockPositions);
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

    int getIntersectionCount(BlockPosition blockPosition) {
        return getIntersectionCountOfBlockPositions()[blockPosition.id];
    }

    private boolean isPossible(BlockPosition blockPosition) {
        return getIntersectionCount(blockPosition) == 0;
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

    public List<BlockPosition> placeBlockPosition(BlockPosition blockPosition) {
        List<Integer> intersectPositionIds = blockPosition.getIntersectPositionIds();
        Stream.Builder<BlockPosition> builder = Stream.builder();
        for (Integer intersectPositionId : intersectPositionIds) {
            BlockPosition intersectBlockPosition = blockPuzzle.getBlockPositionById(intersectPositionId);
            int intersectCount = this.incrementIntersectionCount(intersectBlockPosition);
            // from possible to not possible
            if (intersectCount == 1 && !isPlaced(intersectBlockPosition.getBlock())) {
                builder.add(intersectBlockPosition);
            }
        }
        return Stream.concat(builder.build(), blockPosition.getBlock().getBlockPositions().stream().filter(this::isPossible)).toList();
    }

    public List<BlockPosition> takeBlockPosition(BlockPosition blockPosition) {
        List<Integer> intersectPositionIds = blockPosition.getIntersectPositionIds();
        Stream.Builder<BlockPosition> builder = Stream.builder();
        for (Integer intersectPositionId : intersectPositionIds) {
            BlockPosition intersectBlockPosition = blockPuzzle.getBlockPositionById(intersectPositionId);
            int intersectCount = this.decrementIntersectionCount(intersectBlockPosition);
            if (intersectCount == 0 && !isPlaced(intersectBlockPosition.getBlock())) {
                builder.add(intersectBlockPosition);
            }
        }
        return Stream.concat(builder.build(), blockPosition.getBlock().getBlockPositions().stream().filter(this::isPossible)).toList();
    }

    private boolean isPlaced(Block block) {
        return this.addedPositionOfBlocks[block.id] != -1;
    }

    // check which position is added
    private int[] getAddedPositionOfBlocks() {
        return addedPositionOfBlocks;
    }

    public void resetAddedPositionPriority(Block block) {
        addedPositionOfBlocks[block.id] = -1;
    }

}
