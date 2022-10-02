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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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
        performanceRecorder.init();
        this.blockPossiblePosition = new BlockPossiblePosition(blockPuzzle);
        this.blockPuzzle = blockPuzzle;
        this.blocks = new ArrayList<>(this.blockPuzzle.getBlocks());
        blocks.sort(new BlockComparator());
        for (int i = 0; i < this.blocks.size(); i++) {
            Block block = this.blocks.get(i);
            block.setPriority(i);
        }
        sortBlockPositions();
        boardFillState = new BoardFillState(blockPuzzle);
        this.solution = new Solution(this.blockPuzzle);
        performanceRecorder.addExecution("Complete initialize block puzzle");
    }

    private void sortBlockPositions() {
        BlockPuzzleSolver.BlockPositionComparator blockPositionComparator = new BlockPuzzleSolver.BlockPositionComparator(blockPuzzle);
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

    public Solution solve() {
        long iterateCount = 0;
        while (!isSolved()) {
            iterateCount++;
            Block block = getNextBlockToAdd();

            if (isCurrentBoardSolvable(block)) {
                addNextPossibleBlockPosition(block);
                addBlock(block);
            } else {
                if (isAllBlockTried()) {
                    break;
                }
                BlockPosition lastBlockPosition = removeLastBlockPositionFromSolution();
                removeBlock(block);
            }
            if (iterateCount % 200000 == 0) {
                logger.info("iterate" + iterateCount);
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

    private final BitSet isCommonPositionIntersectBitSet = new BitSet();

    private BitSet getCommonIntersectBlockPositions(List<BlockPosition> blockPositions) {
        isCommonPositionIntersectBitSet.clear();
        isCommonPositionIntersectBitSet.flip(0, isCommonPositionIntersectBitSet.size());
        for (BlockPosition blockPosition : blockPositions) {
            isCommonPositionIntersectBitSet.and(blockPosition.getIsPositionIdIntersectBitSet());
            if (isCommonPositionIntersectBitSet.cardinality() == 0) {
                return isCommonPositionIntersectBitSet;
            }
        }
        return isCommonPositionIntersectBitSet;
    }

    private boolean isCurrentBoardSolvable(Block block) {
        List<Block> remainingBlocks = getRemainingBlocks();
        BlockPossiblePosition cloneBlockPossiblePosition = this.blockPossiblePosition;
        BoardFillState cloneBoardFillState = this.boardFillState.copy();
        if (!this.blockPossiblePosition.hasPossibleBlockPosition(block)) {
            return false;
        }
        if (this.boardFillState.existCannotFillPoint()) {
            return false;
        }
        List<List<BlockPosition>> remainingBlocksBlockPositions = getRemainingBlocksBlockPositions(remainingBlocks, cloneBlockPossiblePosition);
        return isRemainingBlockPositionsSolvable(cloneBoardFillState, remainingBlocksBlockPositions);
    }

    private boolean isRemainingBlockPositionsSolvable(BoardFillState cloneBoardFillState, List<List<BlockPosition>> remainingBlocksBlockPositions) {
        boolean hasChange;
        do {
            hasChange = false;

            for (int i = 0; i < remainingBlocksBlockPositions.size(); i++) {
                BitSet commonIntersectBlockPositions = getCommonIntersectBlockPositions(
                        remainingBlocksBlockPositions.get(i));
                if (commonIntersectBlockPositions.cardinality() == 0) {
                    continue;
                }
                for (int j = 0; j < remainingBlocksBlockPositions.size(); j++) {
                    if (i == j) {
                        continue;
                    }
                    List<BlockPosition> remainingBlockBlockPositions = remainingBlocksBlockPositions.get(j);
                    Iterator<BlockPosition> iterator = remainingBlockBlockPositions.iterator();
                    while (iterator.hasNext()) {
                        BlockPosition blockPosition = iterator.next();
                        if (commonIntersectBlockPositions.get(blockPosition.id)) {
                            hasChange = true;
                            cloneBoardFillState.removeCanFillBlockPosition(blockPosition);
                            iterator.remove();
                        }
                    }
                    if (remainingBlockBlockPositions.isEmpty()) {
                        return false;
                    }
                }
            }
            List<PointFillState> emptyPoints = cloneBoardFillState.getEmptyPoints();
            List<PointFillState> remainOneBlockEmptyPoints = new ArrayList<>();

            for (PointFillState emptyPoint : emptyPoints) {
                if (emptyPoint.getCanFillBlockIds().size() == 1) {
                    remainOneBlockEmptyPoints.add(emptyPoint);
                }
            }

            for (PointFillState remainOneBlockEmptyPoint : remainOneBlockEmptyPoints) {
                if (remainOneBlockEmptyPoint.getCanFillBlockIds().isEmpty()) {
                    return false;
                }
                Block remainBlock = this.blockPuzzle
                        .getBlockById(remainOneBlockEmptyPoint.getCanFillBlockIds().get(0));

                for (int i = 0; i < remainingBlocksBlockPositions.size(); i++) {
                    List<BlockPosition> remainingBlockBlockPositions = remainingBlocksBlockPositions.get(i);
                    boolean isSameBlock = remainingBlockBlockPositions.get(0).getBlock() == remainBlock;
                    Iterator<BlockPosition> iterator = remainingBlockBlockPositions.iterator();
                    while (iterator.hasNext()) {
                        BlockPosition blockPosition = iterator.next();
                        boolean isPointInPosition = blockPosition.canFill(remainOneBlockEmptyPoint);
                        if ((isSameBlock && !isPointInPosition ) || (!isSameBlock && isPointInPosition)) {
                            hasChange = true;
                            cloneBoardFillState.removeCanFillBlockPosition(blockPosition);
                            iterator.remove();
                        }
                    }
                    if (remainingBlockBlockPositions.isEmpty()) {
                        return false;
                    }
                }
            }
            if (cloneBoardFillState.existCannotFillPoint()) {
                return false;
            }
        } while (hasChange);
        return true;
    }

    private List<List<BlockPosition>> getRemainingBlocksBlockPositions(List<Block> remainingBlocks, BlockPossiblePosition cloneBlockPossiblePosition) {
        List<List<BlockPosition>> remainingBlocksBlockPositions = new ArrayList<>();

        boolean[] isBlocksSkippable = new boolean[blocks.size()];
        for (Block remainingBlock : remainingBlocks) {
            if (isBlocksSkippable[remainingBlock.id]) {
                continue;
            }
            List<BlockPosition> possibleBlockPositions = cloneBlockPossiblePosition
                    .getPossibleBlockPosition(remainingBlock);
            if (possibleBlockPositions.size() > 1) {
                for (Integer coverableBlockId : remainingBlock.getCoverableBlockIds()) {
                    isBlocksSkippable[coverableBlockId] = true;
                }
            }
            remainingBlocksBlockPositions.add(possibleBlockPositions);
        }
        return remainingBlocksBlockPositions;
    }

    private void setAllBlockPositionsTried(Block block) {
        this.blockPossiblePosition.getAddedPositionPriorityOfBlocks()[block.id] = -1;
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
        boardFillState.removeBlockPosition(lastBlockPosition);
        return lastBlockPosition;
    }

    private void addNextPossibleBlockPosition(Block block) {
        List<BlockPosition> blockPositions = block.getBlockPositions();
        int positionPriorityFrom = this.blockPossiblePosition.getAddedPositionPriorityOfBlocks()[block.id] + 1;
        int positionPriorityTo = blockPositions.size() - 1;
        for (int i = positionPriorityFrom; i <= positionPriorityTo; i++) {
            BlockPosition nextPossiblePosition = blockPositions.get(i);
            if (blockPossiblePosition.getIntersectionCount(nextPossiblePosition) > 0) {
                continue;
            }

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
            blockPossiblePosition.getAddedPositionPriorityOfBlocks()[block.id] = i;
            solution.push(nextPossiblePosition);
            for (BlockPosition blockPosition : nextPossiblePosition.getBlock().getBlockPositions()) {
                if (blockPossiblePosition.getIntersectionCount(blockPosition) == 0) {
                    boardFillState.removeCanFillBlockPosition(blockPosition);
                }
            }
            boardFillState.addBlockPosition(nextPossiblePosition);
            return;
        }
        throw new RuntimeException("No possible position added");
    }

    private boolean isSolved() {
        return solution.size() == this.blockPuzzle.blockCount;
    }

    private void addBlock(Block block) {
        updateBlockOrder();
        if (this.blockPossiblePosition.getAddedPositionPriorityOfBlocks()[block.id] > -1) {
            return;
        }
    }

    private void removeBlock(Block block) {
        setAllBlockPositionsTried(block);
    }
}
