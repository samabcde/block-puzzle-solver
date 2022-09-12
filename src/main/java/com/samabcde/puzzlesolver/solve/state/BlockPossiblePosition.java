package com.samabcde.puzzlesolver.solve.state;

import com.samabcde.puzzlesolver.component.Block;
import com.samabcde.puzzlesolver.component.BlockPosition;
import com.samabcde.puzzlesolver.component.BlockPuzzle;
import com.samabcde.puzzlesolver.solve.priority.BlockPriorityComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockPossiblePosition {
    private final int[] possiblePositionCountOfBlocks;
    private final int[] intersectionCountOfBlockPositions;
    private final int[] addedPositionPriorityOfBlocks;

    public BlockPossiblePosition(BlockPuzzle blockPuzzle) {
        this.intersectionCountOfBlockPositions = new int[blockPuzzle.blockPositionsById.length];
        this.addedPositionPriorityOfBlocks = new int[blockPuzzle.blockCount];
        List<Block> blocks = blockPuzzle.getBlocks();
        this.possiblePositionCountOfBlocks = new int[blocks.size()];
        for (Block block : blocks) {
            this.possiblePositionCountOfBlocks[block.id] = block.getPositionIdTo() - block.getPositionIdFrom() + 1;
            this.addedPositionPriorityOfBlocks[block.id] = -1;
        }

    }

    private BlockPossiblePosition(BlockPossiblePosition blockPossiblePosition) {
        this.intersectionCountOfBlockPositions = Arrays.copyOf(blockPossiblePosition.intersectionCountOfBlockPositions,
                blockPossiblePosition.intersectionCountOfBlockPositions.length);
        this.possiblePositionCountOfBlocks = Arrays.copyOf(blockPossiblePosition.possiblePositionCountOfBlocks,
                blockPossiblePosition.possiblePositionCountOfBlocks.length);
        this.addedPositionPriorityOfBlocks = Arrays.copyOf(blockPossiblePosition.addedPositionPriorityOfBlocks,
                blockPossiblePosition.addedPositionPriorityOfBlocks.length);
    }

    public boolean isBlockHasPossibleBlockPosition(Block block) {
        List<BlockPosition> blockPositions = block.getBlockPositions();
        int positionPriorityFrom = this.getAddedPositionPriorityOfBlocks()[block.id] + 1;
        int positionPriorityTo = blockPositions.size() - 1;

        for (int i = positionPriorityFrom; i <= positionPriorityTo; i++) {
            if (this.getIntersectionCountOfBlockPositions()[blockPositions.get(i).id] > 0) {
                continue;
            }
            if (this.getPossiblePositionCountOfBlocks()[block.id] == 0) {
                throw new RuntimeException("should have no possible position");
            }
            return true;
        }
        return false;
    }

    public List<BlockPosition> getPossibleBlockPosition(Block block) {
        List<BlockPosition> possibleBlockPositions = new ArrayList<>();
        List<BlockPosition> blockPositions = block.getBlockPositions();

        int positionPriorityFrom = this.addedPositionPriorityOfBlocks[block.id] + 1;
        int positionPriorityTo = blockPositions.size() - 1;

        for (int i = positionPriorityFrom; i <= positionPriorityTo; i++) {
            if (this.intersectionCountOfBlockPositions[blockPositions.get(i).id] == 0) {
                possibleBlockPositions.add(blockPositions.get(i));
            }
        }
        return possibleBlockPositions;
    }

    public BlockPossiblePosition clone() {
        return new BlockPossiblePosition(this);
    }

    public int[] getPossiblePositionCountOfBlocks() {
        return possiblePositionCountOfBlocks;
    }

    public int[] getIntersectionCountOfBlockPositions() {
        return intersectionCountOfBlockPositions;
    }

    public int[] getAddedPositionPriorityOfBlocks() {
        return addedPositionPriorityOfBlocks;
    }

}
