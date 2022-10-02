package com.samabcde.puzzlesolver.solve.priority;

import com.samabcde.puzzlesolver.component.Block;
import com.samabcde.puzzlesolver.solve.state.BlockPossiblePosition;
import com.samabcde.puzzlesolver.solve.state.BoardFillState;

import java.util.Comparator;
import java.util.Optional;

public class BlockPriorityComparator implements Comparator<Block> {

    private final BlockPossiblePosition blockPossiblePosition;
    private final Optional<Block> onlyBlock;

    public BlockPriorityComparator(BlockPossiblePosition blockPossiblePosition, BoardFillState boardFillState) {
        this.blockPossiblePosition = blockPossiblePosition;
        this.onlyBlock = boardFillState.getOnlyBlock();
    }

    @Override
    public int compare(Block arg0, Block arg1) {
//        if (onlyBlock.isPresent()) {
//            if (arg0 == onlyBlock.get()) {
//                return -1;
//            }
//            if (arg1 == onlyBlock.get()) {
//                return 1;
//            }
//        }
        if (blockPossiblePosition.getPossiblePositionCount(arg0) != blockPossiblePosition
                .getPossiblePositionCount(arg1)) {
            return Integer.compare(blockPossiblePosition.getPossiblePositionCount(arg0),
                    blockPossiblePosition.getPossiblePositionCount(arg1));
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
