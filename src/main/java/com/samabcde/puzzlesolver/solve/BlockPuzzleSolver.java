package com.samabcde.puzzlesolver.solve;

import com.samabcde.puzzlesolver.component.Block;
import com.samabcde.puzzlesolver.component.BlockPosition;
import com.samabcde.puzzlesolver.component.BlockPuzzle;
import com.samabcde.puzzlesolver.performance.PerformanceRecorder;
import com.samabcde.puzzlesolver.solve.priority.BlockComparator;
import com.samabcde.puzzlesolver.solve.priority.BlockPositionComparator;
import com.samabcde.puzzlesolver.solve.priority.BlockPriorityComparator;
import com.samabcde.puzzlesolver.solve.state.BlockPossiblePosition;
import com.samabcde.puzzlesolver.solve.state.BoardFillState;
import com.samabcde.puzzlesolver.solve.state.PointFillState;
import com.samabcde.puzzlesolver.solve.state.PossiblePositions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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

    private Block getNextBlockToAdd() {
        return this.getRemainingBlocks().getFirst();
    }

    private void updateBlockOrder() {
        getRemainingBlocks().sort(new BlockPriorityComparator(blockPossiblePosition, boardFillState));
    }

    public Solution solve() {
        long iterateCount = 0;
        while (!isSolved()) {
            iterateCount++;
            Block block = getNextBlockToAdd();

            if (isCurrentBoardSolvable(block)) {
                placeNextBlockPosition(block);
                updateBlockOrder();
            } else {
                if (isAllBlockTried()) {
                    break;
                }
                resetAddedPosition(block);
                takeLastBlockPosition();
            }
            if (iterateCount % 10000 == 0) {
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
        solution.setIterateCount(iterateCount);
        return solution;
    }

    private boolean isCurrentBoardSolvable(Block block) {
        if (!this.blockPossiblePosition.hasPossiblePosition(block)) {
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
            if (!blockPossiblePosition.hasPossiblePosition(remainingBlock)) {
                return false;
            }
        }
        BoardFillState cloneBoardFillState = this.boardFillState.copy();
        List<PossiblePositions> remainingBlockPossiblePositions = getRemainingBlocksPossiblePositions(remainingBlocks, this.blockPossiblePosition);
        return isRemainingBlockPositionsSolvable(cloneBoardFillState, remainingBlockPossiblePositions);
    }

    private boolean isRemainingBlockPositionsSolvable(BoardFillState cloneBoardFillState,
                                                      List<PossiblePositions> remainingBlocksPossiblePositions) {

        long weight1BlockCount = getRemainingBlocks().stream().filter(b -> b.getWeight() == 1).count();
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
                    if (cloneBoardFillState.countPointCanOnlyFillByWeight1Block() > weight1BlockCount) {
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
                    if (cloneBoardFillState.countPointCanOnlyFillByWeight1Block() > weight1BlockCount) {
                        return false;
                    }
                }
            }
        } while (hasChange);
        return true;
    }

    private List<PossiblePositions> getRemainingBlocksPossiblePositions(List<Block> remainingBlocks, BlockPossiblePosition cloneBlockPossiblePosition) {
        List<PossiblePositions> remainingBlocksPossiblePositions = new ArrayList<>();

        boolean[] isBlocksSkippable = new boolean[blocks.size()];
        for (Block remainingBlock : remainingBlocks) {
            if (isBlocksSkippable[remainingBlock.id]) {
                continue;
            }
            PossiblePositions possibleBlockPositions = cloneBlockPossiblePosition
                    .getPossiblePositions(remainingBlock);
            if (possibleBlockPositions.hasNoCommonIntersect()) {
                for (Integer coverableBlockId : remainingBlock.getCoverableBlockIds()) {
                    isBlocksSkippable[coverableBlockId] = true;
                }
            }
            remainingBlocksPossiblePositions.add(possibleBlockPositions);
        }
        return remainingBlocksPossiblePositions;
    }

    private boolean isAllBlockTried() {
        return solution.isEmpty();
    }

    private void placeNextBlockPosition(Block block) {
        BlockPosition nextPossiblePosition = blockPossiblePosition.pollNextPossiblePosition(block);

        boardFillState.placeBlockPosition(nextPossiblePosition);
        blockPossiblePosition.placeBlockPosition(nextPossiblePosition)
                .forEach(boardFillState::removeCanFillBlockPosition);
        solution.push(nextPossiblePosition);
    }

    private void takeLastBlockPosition() {
        BlockPosition lastBlockPosition = solution.poll();

        boardFillState.takeBlockPosition(lastBlockPosition);
        blockPossiblePosition.takeBlockPosition(lastBlockPosition)
                .forEach(boardFillState::addCanFillBlockPosition);
    }

    private boolean isSolved() {
        return solution.size() == this.blockPuzzle.blockCount;
    }

    private void resetAddedPosition(Block block) {
        setAllBlockPositionsTried(block);
    }

    private void setAllBlockPositionsTried(Block block) {
        this.blockPossiblePosition.resetPlacedPositionPriority(block);
    }

}
