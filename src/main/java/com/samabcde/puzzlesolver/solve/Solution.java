package com.samabcde.puzzlesolver.solve;

import com.samabcde.puzzlesolver.component.Block;
import com.samabcde.puzzlesolver.component.BlockPosition;
import com.samabcde.puzzlesolver.component.BlockPuzzle;
import com.samabcde.puzzlesolver.component.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Solution implements Iterable<BlockPosition> {
    private static final Logger logger = LoggerFactory.getLogger(Solution.class);
    private final BlockPuzzle blockPuzzle;
    private final Deque<BlockPosition> positionSolutions = new LinkedList<>();

    public Solution(BlockPuzzle blockPuzzle) {
        this.blockPuzzle = blockPuzzle;
    }

    void print() {
        char[][] solutionView = new char[this.blockPuzzle.getPuzzleHeight()][this.blockPuzzle.getPuzzleWidth()];
        for (BlockPosition blockPosition : positionSolutions) {
            Position position = blockPosition.getPosition();
            Block block = blockPosition.getBlock();
            for (int rowIndex = 0; rowIndex < block.getHeight(); rowIndex++) {
                for (int colIndex = 0; colIndex < block.getWidth(); colIndex++) {
                    if (block.get(rowIndex, colIndex)) {
                        solutionView[rowIndex + position.y()][colIndex + position.x()] = (char) (block.getPriority() + 65);
                    }
                }
            }
        }
        for (char[] solutionRowView : solutionView) {
            StringBuilder stringBuilder = new StringBuilder();
            for (char c : solutionRowView) {
                if (c == 0) {
                    stringBuilder.append(" ");
                } else {
                    stringBuilder.append(c);
                }
            }
            logger.info(stringBuilder.toString());
        }
    }

    public BlockPosition poll() {
        return positionSolutions.poll();
    }

    public void push(BlockPosition blockPosition) {
        positionSolutions.push(blockPosition);
    }

    public int size() {
        return positionSolutions.size();
    }

    public boolean isEmpty() {
        return positionSolutions.isEmpty();
    }

    @Override
    public Iterator<BlockPosition> iterator() {
        return positionSolutions.iterator();
    }

    @Override
    public void forEach(Consumer<? super BlockPosition> action) {
        positionSolutions.forEach(action);
    }

    @Override
    public Spliterator<BlockPosition> spliterator() {
        return positionSolutions.spliterator();
    }
}
