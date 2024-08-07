package com.samabcde.puzzlesolver.solve.priority;

import com.samabcde.puzzlesolver.component.Block;
import com.samabcde.puzzlesolver.solve.state.BlockPossiblePosition;
import com.samabcde.puzzlesolver.solve.state.BoardFillState;

import java.util.Comparator;

public class BlockPriorityComparator implements Comparator<Block> {

    private final BlockPossiblePosition blockPossiblePosition;
    private final BoardFillState boardFillState;

    public BlockPriorityComparator(BlockPossiblePosition blockPossiblePosition, BoardFillState boardFillState) {
        this.blockPossiblePosition = blockPossiblePosition;
        this.boardFillState = boardFillState;
    }

    @Override
    public int compare(Block arg0, Block arg1) {
        int possiblePositionCount0 = blockPossiblePosition.getPossiblePositionCount(arg0);
        int possiblePositionCount1 = blockPossiblePosition.getPossiblePositionCount(arg1);
        if (possiblePositionCount0 != possiblePositionCount1) {
            return Integer.compare(possiblePositionCount0,
                    possiblePositionCount1);
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
