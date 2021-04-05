package com.samabcde.puzzlesolver.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class BlockPuzzleSolver {
    private final Logger logger = LoggerFactory.getLogger(BlockPuzzleSolver.class);
    private final BlockPuzzle blockPuzzle;
    private final List<Block> blocks;
    private final Deque<BlockPosition> solutions = new LinkedList<>();
    private final BlockPossiblePosition blockPossiblePosition;
    private final BoardFillState boardFillState;

    private final long[] noPossiblePositionCount;
    private final long[] noFillablePointCount;
    private final long[] pTfFCount;
    private final long[] pFfTCount;
    List<Long> executeTime = new ArrayList<>();

    private List<Block> getRemainingBlocks() {
        return blocks.subList(solutions.size(), blocks.size());
    }

    public BlockPuzzleSolver(BlockPuzzle blockPuzzle) {

        executeTime.add(new Date().getTime());
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
        noFillablePointCount = new long[blockPuzzle.getBlocks().size()];
        pTfFCount = new long[blockPuzzle.getBlocks().size()];
        pFfTCount = new long[blockPuzzle.getBlocks().size()];
        executeTime.add(new Date().getTime());
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
        getRemainingBlocks().sort(this.blockPossiblePosition.getBlockPrioriyComparator());
    }

    public Collection<BlockPosition> solve() {
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
                printSolution();
            }
        }
        executeTime.add(new Date().getTime());
        if (isSolved()) {
            logger.info("Solved");
            logger.info("iterate Count:" + iterateCount);
            logger.info("No Possible Position Count:" + Arrays.toString(noPossiblePositionCount));
            logger.info("Can not fill all points Count:" + Arrays.toString(noFillablePointCount));
            logger.info("Position false point true:" + Arrays.toString(pFfTCount));
            logger.info("Position true point false:" + Arrays.toString(pTfFCount));

            printSolution();
        } else {
            logger.info("Cannot solve");
            logger.info("iterate Count:" + iterateCount);
        }
        executeTime.add(new Date().getTime());
        for (int i = 0; i < executeTime.size() - 1; i++) {
            logger.info("Step " + i + "time:" + (executeTime.get(i + 1) - executeTime.get(i)));
        }
        return solutions;
    }

    private static BitSet isCommonPositionIntersectBitSet = new BitSet();

    private BitSet getCommonIntersectBlockPositions(List<BlockPosition> blockPositions) {
        if (isCommonPositionIntersectBitSet != null) {
            isCommonPositionIntersectBitSet.clear();
            isCommonPositionIntersectBitSet.flip(0, isCommonPositionIntersectBitSet.size());
        }

        for (BlockPosition blockPosition : blockPositions) {
            if (isCommonPositionIntersectBitSet == null) {
                isCommonPositionIntersectBitSet = (BitSet) blockPosition.isPositionIdIntersectBitSet.clone();
                continue;
            }
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
        if (cloneBoardFillState.existNotFillablePoint()) {
            return false;
        }
        List<List<BlockPosition>> remainingBlocksBlockPositions = new ArrayList<List<BlockPosition>>();

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
        boolean hasChange = false;
        do {
            hasChange = false;

            for (int i = 0; i < remainingBlocksBlockPositions.size(); i++) {
                BitSet commonIntersectBlockBlockPositions = getCommonIntersectBlockPositions(
                        remainingBlocksBlockPositions.get(i));
                if (commonIntersectBlockBlockPositions.cardinality() == 0) {
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
                        if (commonIntersectBlockBlockPositions.get(blockPosition.id)) {
                            hasChange = true;
                            cloneBoardFillState.removeFillableBlockPosition(blockPosition);
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
                if (emptyPoint.getFillableBlockIds().size() == 1) {
                    remainOneBlockEmptyPoints.add(emptyPoint);
                }
                if (emptyPoint.getFillableBlockIds().size() > 1) {
                    break;
                }
            }

            for (PointFillState remainOneBlockEmptyPoint : remainOneBlockEmptyPoints) {
                if (remainOneBlockEmptyPoint.getFillableBlockIds().isEmpty()) {
                    return false;
                }
                Block remainBlock = this.blockPuzzle
                        .getBlockById(remainOneBlockEmptyPoint.getFillableBlockIds().get(0));

                for (int i = 0; i < remainingBlocksBlockPositions.size(); i++) {
                    List<BlockPosition> remainingBlockBlockPositions = remainingBlocksBlockPositions.get(i);
                    boolean isSameBlock = remainingBlockBlockPositions.get(0).getBlock() == remainBlock;
                    Iterator<BlockPosition> iterator = remainingBlockBlockPositions.iterator();
                    while (iterator.hasNext()) {
                        BlockPosition blockPosition = iterator.next();
                        int isPointInPosition = Arrays.binarySearch(blockPosition.getFillablePoints(),
                                remainOneBlockEmptyPoint.getPosition());
                        if ((isSameBlock && isPointInPosition < 0) || (!isSameBlock && isPointInPosition > -1)) {
                            hasChange = true;
                            cloneBoardFillState.removeFillableBlockPosition(blockPosition);
                            iterator.remove();
                        }
                    }
                    if (remainingBlockBlockPositions.isEmpty()) {
                        return false;
                    }
                }
            }
            if (cloneBoardFillState.existNotFillablePoint()) {
                return false;
            }
        } while (hasChange);
        return true;
    }

    private boolean existNotFillablePoint() {
        return this.boardFillState.existNotFillablePoint();
    }

    private void printSolution() {
        char[][] solutionView = new char[this.blockPuzzle.getPuzzleHeight()][this.blockPuzzle.getPuzzleWidth()];
        for (BlockPosition blockPosition : solutions) {
            Position position = blockPosition.getPosition();
            Block block = blockPosition.getBlock();
            for (int rowIndex = 0; rowIndex < block.getHeight(); rowIndex++) {
                for (int colIndex = 0; colIndex < block.getWidth(); colIndex++) {
                    if (block.get(rowIndex, colIndex)) {
                        solutionView[rowIndex + position.y][colIndex + position.x] = (char) (block.getPriority() + 65);
                    }
                }
            }
        }
        for (char[] solutionRowView : solutionView) {
            logger.info(Arrays.toString(solutionRowView));
        }
    }

    private void setAllBlockPositionsTried(Block block) {
        this.blockPossiblePosition.getAddedPositionPriorityOfBlocks()[block.id] = -1;
    }

    private boolean isAllBlockTried() {
        return solutions.isEmpty();
    }

    private BlockPosition removeLastBlockPositionFromSolution(Block block) {
        BlockPosition lastBlockPosition = solutions.poll();

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
            for (BlockPosition solution : solutions) {
                if (solution.block == intersectBlockPosition.block) {
                    isInSolution = true;
                    break;
                }
            }
            if (!isInSolution) {

                boardFillState.addFillableBlockPosition(intersectBlockPosition);

            }
            blockPossiblePosition.getPossiblePositionCountOfBlocks()[blockPuzzle
                    .getBlockIdByBlockPositionId(intersectPositionId)]++;

        }

        for (BlockPosition blockPosition : lastBlockPosition.block.getBlockPositions()) {
            if (blockPossiblePosition.getIntersectionCountOfBlockPositions()[blockPosition.id] == 0) {
                boardFillState.addFillableBlockPosition(blockPosition);
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
                    for (BlockPosition solution : solutions) {
                        if (solution.block == intersectBlockPosition.block) {
                            isInSolution = true;
                            break;
                        }
                    }
                    if (!isInSolution) {
                        boardFillState
                                .removeFillableBlockPosition(blockPuzzle.getBlockPositionById(intersectPositionId));

                    }
                    blockPossiblePosition.getPossiblePositionCountOfBlocks()[blockPuzzle
                            .getBlockIdByBlockPositionId(intersectPositionId)]--;
                }
            }
            this.blockPossiblePosition.getAddedPositionPriorityOfBlocks()[block.id] = i;
            blockPossiblePosition.getPossiblePositionCountOfBlocks()[block.id]--;
            solutions.push(nextPossiblePosition);
            for (BlockPosition blockPosition : nextPossiblePosition.block.getBlockPositions()) {
                if (blockPossiblePosition.getIntersectionCountOfBlockPositions()[blockPosition.id] == 0) {
                    boardFillState.removeFillableBlockPosition(blockPosition);
                }
            }
            boardFillState.addBlockPosition(nextPossiblePosition);
            return;
        }
        throw new RuntimeException("No possible position added");
    }

    private boolean isSolved() {
        return solutions.size() == this.blockPuzzle.blockCount;
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
