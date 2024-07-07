package com.samabcde.puzzlesolver.solve.state;

import com.samabcde.puzzlesolver.component.Block;
import com.samabcde.puzzlesolver.component.BlockPosition;
import com.samabcde.puzzlesolver.component.BlockPuzzle;

import java.util.*;
import java.util.stream.Stream;

public class BlockPossiblePosition {
    private final BlockPuzzle blockPuzzle;
    private final int[] possiblePositionCountOfBlocks;
    private final int[] intersectionCountOfBlockPositions;
    private final int[] placedPositionOfBlocks;
    private Set<Integer> placedBlockIds = new HashSet<>();
    // TODO add BlockCommonIntersection

    public BlockPossiblePosition(BlockPuzzle blockPuzzle) {
        this.blockPuzzle = blockPuzzle;
        this.intersectionCountOfBlockPositions = new int[blockPuzzle.getPositionCount()];
        List<Block> blocks = blockPuzzle.getBlocks();
        this.placedPositionOfBlocks = new int[blocks.size()];
        this.possiblePositionCountOfBlocks = new int[blocks.size()];
        for (Block block : blocks) {
            this.possiblePositionCountOfBlocks[block.id] = block.getPositionIdTo() - block.getPositionIdFrom() + 1;
            this.placedPositionOfBlocks[block.id] = -1;
        }
    }

    public boolean hasPossiblePosition(Block block) {
        List<BlockPosition> blockPositions = block.getBlockPositions();
        int positionPriorityFrom = this.getPlacedPosition(block) + 1;
        int positionPriorityTo = blockPositions.size() - 1;

        for (int i = positionPriorityFrom; i <= positionPriorityTo; i++) {
            if (!isPossible(blockPositions.get(i))) {
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

        int positionPriorityFrom = getBlockPriorityFrom(block);
        int positionPriorityTo = blockPositions.size() - 1;

        for (int i = positionPriorityFrom; i <= positionPriorityTo; i++) {
            if (isPossible(blockPositions.get(i))) {
                setPlacedPosition(block, i);
                return blockPositions.get(i);
            }
        }
        throw new NoSuchElementException("No possible position to choose");
    }

    public PossiblePositions getPossiblePositions(Block block) {
        List<BlockPosition> possibleBlockPositions = new ArrayList<>(getPossiblePositionCount(block));
        List<BlockPosition> blockPositions = block.getBlockPositions();

        int positionPriorityFrom = getBlockPriorityFrom(block);
        int positionPriorityTo = blockPositions.size() - 1;

        for (int i = positionPriorityFrom; i <= positionPriorityTo; i++) {
            if (isPossible(blockPositions.get(i))) {
                possibleBlockPositions.add(blockPositions.get(i));
            }
        }
        return new PossiblePositions(possibleBlockPositions);
    }

    // possible if not in solution or no intersect
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
        return intersectionCountOfBlockPositions[blockPosition.id] == 0;
    }

    public int incrementIntersectionCount(BlockPosition blockPosition) {
        boolean wasPossible = isPossible(blockPosition);
        this.intersectionCountOfBlockPositions[blockPosition.id]++;
        if (wasPossible) {
            decrementPossiblePositionCount(blockPosition.getBlock());
        }
        return getIntersectionCount(blockPosition);
    }

    public int decrementIntersectionCount(BlockPosition blockPosition) {
        this.intersectionCountOfBlockPositions[blockPosition.id]--;
        boolean nowPossible = isPossible(blockPosition);
        if (nowPossible) {
            incrementPossiblePositionCount(blockPosition.getBlock());
        }
        return getIntersectionCount(blockPosition);
    }

    private void incrementPossiblePositionCount(Block block) {
        this.possiblePositionCountOfBlocks[block.id]++;
    }

    private void decrementPossiblePositionCount(Block block) {
        this.possiblePositionCountOfBlocks[block.id]--;
    }

    public Stream<BlockPosition> placeBlockPosition(BlockPosition blockPosition) {
        placedBlockIds.add(blockPosition.getBlock().id);
        List<Integer> intersectPositionIds = blockPosition.getIntersectPositionIds();
        Stream.Builder<BlockPosition> builder = Stream.builder();
        for (Integer intersectPositionId : intersectPositionIds) {
            BlockPosition intersectBlockPosition = blockPuzzle.getBlockPositionById(intersectPositionId);
            boolean wasPossible = isPossible(intersectBlockPosition);
            this.incrementIntersectionCount(intersectBlockPosition);
            // from possible to not possible
            if (wasPossible && !isPlaced(intersectBlockPosition.getBlock())) {
                builder.add(intersectBlockPosition);
            }
        }
        return Stream.concat(builder.build(), blockPosition.getBlock().getBlockPositions().stream().filter(this::isPossible));
    }

    public Stream<BlockPosition> takeBlockPosition(BlockPosition blockPosition) {
        placedBlockIds.remove(blockPosition.getBlock().id);
        List<Integer> intersectPositionIds = blockPosition.getIntersectPositionIds();
        Stream.Builder<BlockPosition> builder = Stream.builder();
        for (Integer intersectPositionId : intersectPositionIds) {
            BlockPosition intersectBlockPosition = blockPuzzle.getBlockPositionById(intersectPositionId);
            this.decrementIntersectionCount(intersectBlockPosition);
            boolean nowPossible = isPossible(intersectBlockPosition);
            if (nowPossible && !isPlaced(intersectBlockPosition.getBlock())) {
                builder.add(intersectBlockPosition);
            }
        }
        return Stream.concat(builder.build(), blockPosition.getBlock().getBlockPositions().stream().filter(this::isPossible));
    }

    private boolean isPlaced(Block block) {
        return this.placedBlockIds.contains(block.id);
    }

    // check which position is added
    private int getPlacedPosition(Block block) {
        return this.placedPositionOfBlocks[block.id];
    }

    private void setPlacedPosition(Block block, int position) {
        this.placedPositionOfBlocks[block.id] = position;
    }

    private int getBlockPriorityFrom(Block block) {
        return getPlacedPosition(block) + 1;
    }

    public void resetPlacedPositionPriority(Block block) {
        setPlacedPosition(block, -1);
    }

}
