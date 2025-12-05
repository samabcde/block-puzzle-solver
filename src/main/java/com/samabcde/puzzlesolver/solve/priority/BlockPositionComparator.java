package com.samabcde.puzzlesolver.solve.priority;

import com.samabcde.puzzlesolver.component.BlockPosition;
import com.samabcde.puzzlesolver.component.BlockPuzzle;
import java.util.Comparator;

public record BlockPositionComparator(BlockPuzzle blockPuzzle)
        implements Comparator<BlockPosition> {

    @Override
    public int compare(BlockPosition arg0, BlockPosition arg1) {
        int compareIntersectScore =
                Integer.compare(
                        arg0.getIntersectScore(blockPuzzle), arg1.getIntersectScore(blockPuzzle));
        if (compareIntersectScore != 0) {
            return compareIntersectScore;
        }
        return Integer.compare(arg0.id, arg1.id);
    }
}
