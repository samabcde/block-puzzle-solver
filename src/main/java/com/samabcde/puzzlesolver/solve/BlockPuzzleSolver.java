package com.samabcde.puzzlesolver.solve;

import com.samabcde.puzzlesolver.component.Block;
import com.samabcde.puzzlesolver.component.BlockPosition;
import com.samabcde.puzzlesolver.component.BlockPuzzle;
import com.samabcde.puzzlesolver.performance.PerformanceRecorder;
import com.samabcde.puzzlesolver.solve.priority.BlockComparator;
import com.samabcde.puzzlesolver.solve.priority.BlockPriorityComparator;
import com.samabcde.puzzlesolver.solve.state.BlockPossiblePosition;
import com.samabcde.puzzlesolver.solve.state.BoardFillState;
import com.samabcde.puzzlesolver.solve.state.PointFillState;
import com.samabcde.puzzlesolver.solve.state.PossiblePositions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BlockPuzzleSolver {
    private static final Logger logger = LoggerFactory.getLogger(BlockPuzzleSolver.class);
    private final BlockPuzzle blockPuzzle;
    private final List<Block> blocks;
    private final Solution solution;
    private final BlockPossiblePosition blockPossiblePosition;
    private final BoardFillState boardFillState;
    private final PerformanceRecorder performanceRecorder = new PerformanceRecorder();

    private List<Block> getRemainingBlocks() {
        return blocks.subList(solution.size(), blocks.size());
    }

    public BlockPuzzleSolver(BlockPuzzle blockPuzzle) {
        blockPuzzle.assertValid();
        performanceRecorder.init();
        this.blockPossiblePosition = new BlockPossiblePosition(blockPuzzle);
        this.blockPuzzle = blockPuzzle;
        // TODO check how to simplify
        this.blocks = new ArrayList<>(this.blockPuzzle.getBlocks());
        blocks.sort(new BlockComparator());
        for (int i = 0; i < this.blocks.size(); i++) {
            Block block = this.blocks.get(i);
            block.setPriority(i);
        }
        sortBlockPositions();
        boardFillState = new BoardFillState(this.blockPuzzle);
        this.solution = new Solution(this.blockPuzzle);
        performanceRecorder.addExecution("Complete initialize block puzzle");
    }

    private void sortBlockPositions() {
        BlockPositionComparator blockPositionComparator = new BlockPositionComparator(blockPuzzle);
        for (Block block : this.blockPuzzle.getBlocks()) {
            List<BlockPosition> blockPositions = block.getBlockPositions();
            blockPositions.sort(blockPositionComparator);
            for (int i = 0; i < blockPositions.size(); i++) {
                blockPositions.get(i).setPriority(i);
            }
        }
    }

    private record BlockPositionComparator(BlockPuzzle blockPuzzle) implements Comparator<BlockPosition> {

        @Override
        public int compare(BlockPosition arg0, BlockPosition arg1) {
            int compareIntersectScore = Integer.compare(arg0.getIntersectScore(blockPuzzle), arg1.getIntersectScore(blockPuzzle));
            if (compareIntersectScore != 0) {
                return compareIntersectScore;
            }
            return Integer.compare(arg0.id, arg1.id);
        }
    }

    private Block getNextBlockToAdd() {
        return this.getRemainingBlocks().get(0);
    }

    private void updateBlockOrder() {
        getRemainingBlocks().sort(new BlockPriorityComparator(blockPossiblePosition, boardFillState));
    }

    long iterateCount = 0;

    public Solution solve() {
        while (!isSolved()) {
            iterateCount++;
            Block block = getNextBlockToAdd();

            if (isCurrentBoardSolvable(block)) {
                addNextPossibleBlockPosition(block);
                updateBlockOrder();
            } else {
                if (isAllBlockTried()) {
                    break;
                }
                BlockPosition lastBlockPosition = removeLastBlockPositionFromSolution();
                resetAddedPosition(block);
            }
            if (iterateCount % 200000 == 0) {
                logger.info("iterate{}", iterateCount);
                solution.print();
            }
        }
        performanceRecorder.addExecution("Solve complete");
        if (isSolved()) {
            logger.info("Solved");
            logger.info("iterate Count: " + iterateCount);
            logger.info("Solution: ");
            solution.print();
        } else {
            logger.info("Cannot solve");
            logger.info("iterate Count: " + iterateCount);
        }
        performanceRecorder.addExecution("Print solution");
        performanceRecorder.print();
        return solution;
    }

    private boolean isCurrentBoardSolvable(Block block) {
        if (!this.blockPossiblePosition.hasPossiblePosition(block, boardFillState)) {
            return false;
        }
        List<Block> remainingBlocks = getRemainingBlocks();
        if (this.boardFillState.countPointCanOnlyFillByWeight1Block() > remainingBlocks.stream().filter(b -> b.getWeight() == 1).count()) {
            return false;
        }
        if (this.boardFillState.existCannotFillPoint()) {
            return false;
        }
        for (Block remainingBlock : remainingBlocks) {
            if (!blockPossiblePosition.hasPossiblePosition(remainingBlock, boardFillState)) {
                return false;
            }
        }
        BoardFillState cloneBoardFillState = this.boardFillState.copy();
        List<PossiblePositions> remainingBlockPossiblePositions = getRemainingBlocksPossiblePositions(cloneBoardFillState, remainingBlocks, this.blockPossiblePosition);
        return isRemainingBlockPositionsSolvable(cloneBoardFillState, remainingBlockPossiblePositions);
    }

    private boolean isRemainingBlockPositionsSolvable(BoardFillState cloneBoardFillState,
                                                      List<PossiblePositions> remainingBlocksPossiblePositions) {

        boolean hasChange;
        do {
            hasChange = false;

            for (int i = 0; i < remainingBlocksPossiblePositions.size(); i++) {
                PossiblePositions possiblePositions = remainingBlocksPossiblePositions.get(i);
                if (possiblePositions.hasNoCommonIntersect()) {
                    continue;
                }
                for (int j = 0; j < remainingBlocksPossiblePositions.size(); j++) {
                    if (i == j) {
                        continue;
                    }
                    boolean removed = remainingBlocksPossiblePositions.get(j).removeCommonIntersect(possiblePositions, cloneBoardFillState);
                    hasChange = hasChange || removed;
                    if (!removed) {
                        continue;
                    }
                    if (remainingBlocksPossiblePositions.get(j).isEmpty()) {
                        return false;
                    }
                    if (cloneBoardFillState.existCannotFillPoint()) {
                        return false;
                    }
                }
            }

            List<PointFillState> remainOneBlockEmptyPoints = cloneBoardFillState.getOneBlockCanFillEmptyPoints();

            for (PointFillState remainOneBlockEmptyPoint : remainOneBlockEmptyPoints) {
                Block remainBlock = this.blockPuzzle
                        .getBlockById(remainOneBlockEmptyPoint.getFirstCanFillBlockId());

                for (PossiblePositions remainingBlockPossiblePositions : remainingBlocksPossiblePositions) {
                    if (remainingBlockPossiblePositions.isDifferentBlock(remainBlock)) {
                        continue;
                    }
                    boolean removed = remainingBlockPossiblePositions.removeCannotFillOneBlockEmptyPoint(cloneBoardFillState, remainOneBlockEmptyPoint);
                    hasChange = hasChange || removed;
                    if (!removed) {
                        continue;
                    }
                    if (remainingBlockPossiblePositions.isEmpty()) {
                        return false;
                    }
                    if (cloneBoardFillState.existCannotFillPoint()) {
                        return false;
                    }
                }
            }

        } while (hasChange);
        return true;
    }

    private List<PossiblePositions> getRemainingBlocksPossiblePositions(BoardFillState cloneBoardFillState, List<Block> remainingBlocks, BlockPossiblePosition cloneBlockPossiblePosition) {
        List<PossiblePositions> remainingBlocksPossiblePositions = new ArrayList<>();

        boolean[] isBlocksSkippable = new boolean[blocks.size()];
        for (Block remainingBlock : remainingBlocks) {
            if (isBlocksSkippable[remainingBlock.id]
//                    && possibleBlockPositions.hasNoCommonIntersect()
            ) {
                continue;
            }
            PossiblePositions possibleBlockPositions = cloneBlockPossiblePosition
                    .getPossiblePositions(remainingBlock, cloneBoardFillState);
            if (possibleBlockPositions.hasNoCommonIntersect()) {
                for (Integer coverableBlockId : remainingBlock.getCoverableBlockIds()) {
                    isBlocksSkippable[coverableBlockId] = true;
                }
            }
            remainingBlocksPossiblePositions.add(possibleBlockPositions);
        }
        return remainingBlocksPossiblePositions;
    }

    private void setAllBlockPositionsTried(Block block) {
        this.blockPossiblePosition.resetAddedPositionPriority(block);
    }

    private boolean isAllBlockTried() {
        return solution.isEmpty();
    }

    private BlockPosition removeLastBlockPositionFromSolution() {
        BlockPosition lastBlockPosition = solution.poll();

        List<Integer> intersectPositionIds = lastBlockPosition.getIntersectPositionIds();
        for (Integer intersectPositionId : intersectPositionIds) {
            BlockPosition intersectBlockPosition = blockPuzzle.getBlockPositionById(intersectPositionId);
            int intersectCount = blockPossiblePosition.decrementIntersectionCount(intersectBlockPosition);
            if (intersectCount > 0) {
                continue;
            }

            boolean isInSolution = solution.containsBlock(intersectBlockPosition.getBlock());

            if (!isInSolution) {
                boardFillState.addCanFillBlockPosition(intersectBlockPosition);
            }
        }

        for (BlockPosition blockPosition : lastBlockPosition.getBlock().getBlockPositions()) {
            if (blockPossiblePosition.getIntersectionCount(blockPosition) == 0) {
                boardFillState.addCanFillBlockPosition(blockPosition);
            }
        }
        boardFillState.clearBlockPosition(lastBlockPosition);
        return lastBlockPosition;
    }

    private void addNextPossibleBlockPosition(Block block) {
        BlockPosition nextPossiblePosition = blockPossiblePosition.pollNextPossiblePosition(block, boardFillState);

        List<Integer> intersectPositionIds = nextPossiblePosition.getIntersectPositionIds();
        for (Integer intersectPositionId : intersectPositionIds) {
            BlockPosition intersectBlockPosition = blockPuzzle.getBlockPositionById(intersectPositionId);
            int intersectCount = blockPossiblePosition.incrementIntersectionCount(intersectBlockPosition);
            if (intersectCount == 1) {
                boolean isInSolution = solution.containsBlock(intersectBlockPosition.getBlock());
                if (!isInSolution) {
                    boardFillState.removeCanFillBlockPosition(intersectBlockPosition);
                }
            }
        }
        solution.push(nextPossiblePosition);
        for (BlockPosition blockPosition : nextPossiblePosition.getBlock().getBlockPositions()) {
            if (blockPossiblePosition.getIntersectionCount(blockPosition) == 0) {
                boardFillState.removeCanFillBlockPosition(blockPosition);
            }
        }
        boardFillState.fillBlockPosition(nextPossiblePosition);
    }

    private boolean isSolved() {
        return solution.size() == this.blockPuzzle.blockCount;
    }

    private void resetAddedPosition(Block block) {
        setAllBlockPositionsTried(block);
    }
}
