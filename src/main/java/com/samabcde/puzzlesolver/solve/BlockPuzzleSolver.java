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
    private BlockPossiblePosition blockPossiblePosition;
    private BoardFillState boardFillState;
    private final long[] noPossiblePositionCount;
    private final long[] noCanFillPointCount;
    private final long[] pTfFCount;
    private final long[] pFfTCount;
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
        noPossiblePositionCount = new long[blockPuzzle.getBlocks().size()];
        noCanFillPointCount = new long[blockPuzzle.getBlocks().size()];
        pTfFCount = new long[blockPuzzle.getBlocks().size()];
        pFfTCount = new long[blockPuzzle.getBlocks().size()];
        this.solution = new Solution(this.blockPuzzle);
        performanceRecorder.addExecution("Complete initialize block puzzle");
    }

    private void sortBlockPositions() {
        BlockPuzzleSolver.BlockPositionComparator blockPositionComparator = new BlockPuzzleSolver.BlockPositionComparator();
        for (Block block : this.blockPuzzle.getBlocks()) {
            List<BlockPosition> blockPositions = block.getBlockPositions();
            blockPositions.sort(blockPositionComparator);
            for (int i = 0; i < blockPositions.size(); i++) {
                blockPositions.get(i).setPriority(i);
            }
        }
    }

    private static class BlockPositionComparator implements Comparator<BlockPosition> {

        @Override
        public int compare(BlockPosition arg0, BlockPosition arg1) {
            int compareIntersectScore = Integer.compare(arg0.getIntersectScore(), arg1.getIntersectScore());
            if (compareIntersectScore != 0) {
                return compareIntersectScore;
            }
            return Integer.compare(arg0.id, arg1.id);
        }
    }

    public Block getNextBlockToAdd() {
        return this.getRemainingBlocks().get(0);
    }

    void updateBlockOrder() {
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
                BlockPosition lastBlockPosition = removeLastBlockPositionFromSolution(block);
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
            logger.debug("No Possible Position Count: " + Arrays.toString(noPossiblePositionCount));
            logger.debug("Can not fill all points Count: " + Arrays.toString(noCanFillPointCount));
            logger.debug("Position false point true: " + Arrays.toString(pFfTCount));
            logger.debug("Position true point false: " + Arrays.toString(pTfFCount));
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
            isCommonPositionIntersectBitSet.and(blockPosition.isPositionIdIntersectBitSet);
            if (isCommonPositionIntersectBitSet.cardinality() == 0) {
                return isCommonPositionIntersectBitSet;
            }
        }
        return isCommonPositionIntersectBitSet;
    }

    private boolean isCurrentBoardSolvable(Block block) {
        List<Block> remainingBlocks = getRemainingBlocks();
        BlockPossiblePosition cloneBlockPossiblePosition = this.blockPossiblePosition.clone();
        BoardFillState cloneBoardFillState = this.boardFillState.clone();
        if (!cloneBlockPossiblePosition.isBlockHasPossibleBlockPosition(block)) {
            return false;
        }
        if (cloneBoardFillState.existCannotFillPoint()) {
            return false;
        }
        List<List<BlockPosition>> remainingBlocksBlockPositions = getRemainingBlocksBlockPositions(remainingBlocks, cloneBlockPossiblePosition);
        for (List<BlockPosition> p : remainingBlocksBlockPositions) {
            if (p.isEmpty()) {
                return false;
            }
        }
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
                        int isPointInPosition = Arrays.binarySearch(blockPosition.getCanFillPoints(),
                                remainOneBlockEmptyPoint.getPosition());
                        if ((isSameBlock && isPointInPosition < 0) || (!isSameBlock && isPointInPosition > -1)) {
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

    private BlockPosition removeLastBlockPositionFromSolution(Block block) {
        BlockPosition lastBlockPosition = solution.poll();

        List<Integer> intersectPositionIds = lastBlockPosition.getIntersectPositionIds();
        for (Integer intersectPositionId : intersectPositionIds) {
            blockPossiblePosition.getIntersectionCountOfBlockPositions()[intersectPositionId]--;
            if (blockPossiblePosition.getIntersectionCountOfBlockPositions()[intersectPositionId] < 0) {
                throw new RuntimeException("");
            }
            if (blockPossiblePosition.getIntersectionCountOfBlockPositions()[intersectPositionId] > 0) {
                continue;
            }

            BlockPosition intersectBlockPosition = blockPuzzle.getBlockPositionById(intersectPositionId);
            boolean isInSolution = false;
            for (BlockPosition blockPosition : solution) {
                if (blockPosition.getBlock() == intersectBlockPosition.getBlock()) {
                    isInSolution = true;
                    break;
                }
            }
            if (!isInSolution) {
                boardFillState.addCanFillBlockPosition(intersectBlockPosition);
            }
            blockPossiblePosition.getPossiblePositionCountOfBlocks()[blockPuzzle
                    .getBlockIdByBlockPositionId(intersectPositionId)]++;

        }

        for (BlockPosition blockPosition : lastBlockPosition.getBlock().getBlockPositions()) {
            if (blockPossiblePosition.getIntersectionCountOfBlockPositions()[blockPosition.id] == 0) {
                boardFillState.addCanFillBlockPosition(blockPosition);
            }
        }
        boardFillState.removeBlockPosition(lastBlockPosition);
        blockPossiblePosition.getPossiblePositionCountOfBlocks()[lastBlockPosition.getBlock().id]++;
        return lastBlockPosition;
    }

    private void addNextPossibleBlockPosition(Block block) {

        List<BlockPosition> blockPositions = block.getBlockPositions();
        int positionPriorityFrom = this.blockPossiblePosition.getAddedPositionPriorityOfBlocks()[block.id] + 1;
        int positionPriorityTo = blockPositions.size() - 1;
        for (int i = positionPriorityFrom; i <= positionPriorityTo; i++) {
            BlockPosition nextPossiblePosition = blockPositions.get(i);
            if (blockPossiblePosition.getIntersectionCountOfBlockPositions()[nextPossiblePosition.id] > 0) {
                continue;
            }

            List<Integer> intersectPositionIds = nextPossiblePosition.getIntersectPositionIds();
            for (Integer intersectPositionId : intersectPositionIds) {
                blockPossiblePosition.getIntersectionCountOfBlockPositions()[intersectPositionId]++;
                if (blockPossiblePosition.getIntersectionCountOfBlockPositions()[intersectPositionId] == 1) {

                    BlockPosition intersectBlockPosition = blockPuzzle.getBlockPositionById(intersectPositionId);
                    boolean isInSolution = false;
                    for (BlockPosition blockPosition : solution) {
                        if (blockPosition.getBlock() == intersectBlockPosition.getBlock()) {
                            isInSolution = true;
                            break;
                        }
                    }
                    if (!isInSolution) {
                        boardFillState.removeCanFillBlockPosition(intersectBlockPosition);
                    }
                    blockPossiblePosition.getPossiblePositionCountOfBlocks()[blockPuzzle
                            .getBlockIdByBlockPositionId(intersectPositionId)]--;
                }
            }
            blockPossiblePosition.getAddedPositionPriorityOfBlocks()[block.id] = i;
            blockPossiblePosition.getPossiblePositionCountOfBlocks()[block.id]--;
            solution.push(nextPossiblePosition);
            for (BlockPosition blockPosition : nextPossiblePosition.getBlock().getBlockPositions()) {
                if (blockPossiblePosition.getIntersectionCountOfBlockPositions()[blockPosition.id] == 0) {
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

    void addBlock(Block block) {
        updateBlockOrder();
        if (this.blockPossiblePosition.getAddedPositionPriorityOfBlocks()[block.id] > -1) {
            return;
        }
    }

    void removeBlock(Block block) {
        setAllBlockPositionsTried(block);
    }
}
