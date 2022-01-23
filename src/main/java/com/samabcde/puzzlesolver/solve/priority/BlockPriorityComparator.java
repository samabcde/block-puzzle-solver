package com.samabcde.puzzlesolver.solve.priority;

import com.samabcde.puzzlesolver.component.Block;
import com.samabcde.puzzlesolver.solve.state.BlockPossiblePosition;

import java.util.Comparator;

public class BlockPriorityComparator implements Comparator<Block> {

    private final BlockPossiblePosition blockPossiblePosition;

    public BlockPriorityComparator(BlockPossiblePosition blockPossiblePosition) {
        this.blockPossiblePosition = blockPossiblePosition;
    }

    @Override
    public int compare(Block arg0, Block arg1) {
        if (blockPossiblePosition.getPossiblePositionCountOfBlocks()[arg0.id] != blockPossiblePosition
                .getPossiblePositionCountOfBlocks()[arg1.id]) {
            return Integer.compare(blockPossiblePosition.getPossiblePositionCountOfBlocks()[arg0.id],
                    blockPossiblePosition.getPossiblePositionCountOfBlocks()[arg1.id]);
        }
        if (arg0.getAverageIntersectCount() != arg1.getAverageIntersectCount()) {
            return -Integer.compare(arg0.getAverageIntersectCount(), arg1.getAverageIntersectCount());
        }
        if (arg0.getWeight() != arg1.getWeight()) {
            return -Integer.compare(arg0.getWeight(), arg1.getWeight());
        }
        if (arg0.getSize() != arg1.getSize()) {
            return -Integer.compare(arg0.getSize(), arg1.getSize());
        }
        return Integer.compare(arg0.id, arg1.id);
    }
}
