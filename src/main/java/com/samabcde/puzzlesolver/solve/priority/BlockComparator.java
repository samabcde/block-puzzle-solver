package com.samabcde.puzzlesolver.solve.priority;

import com.samabcde.puzzlesolver.component.Block;
import java.util.Comparator;

public class BlockComparator implements Comparator<Block> {

    @Override
    public int compare(Block arg0, Block arg1) {
        if (arg0.getAverageIntersectCount() != arg1.getAverageIntersectCount()) {
            return -Integer.compare(
                    arg0.getAverageIntersectCount(), arg1.getAverageIntersectCount());
        }
        if (arg0.getWeight() != arg1.getWeight()) {
            return -Integer.compare(arg0.getWeight(), arg1.getWeight());
        }
        if (arg0.getSize() != arg1.getSize()) {
            return -Integer.compare(arg0.getSize(), arg1.getSize());
        }
        return Integer.compare(arg0.getId(), arg1.getId());
    }
}
