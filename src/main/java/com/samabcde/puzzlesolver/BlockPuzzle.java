package com.samabcde.puzzlesolver;

import java.util.*;

public class BlockPuzzle {
    int blockCount = 0;
    int positionCount = 0;
    int[][] pointFillablePositionId;
    final int puzzleWidth;
    final int puzzleHeight;
    private Map<Integer, List<BlockPosition>> blockIdToBlockPositionsMap = new HashMap<Integer, List<BlockPosition>>();
    Block[] blocksById;
    BlockPosition[] blockPositionsById;
    List<Block> blocks = new ArrayList<Block>();

    int getBlockIdByBlockPositionId(int blockPositionId) {
        return this.blockPositionsById[blockPositionId].block.id;
    }

    public int getPositionCount() {
        return positionCount;
    }

    private int weightOneCount = 0;

    public int getWeightOneCount() {
        return weightOneCount;
    }

    public int getPuzzleWidth() {
        return puzzleWidth;
    }

    public int getPuzzleHeight() {
        return puzzleHeight;
    }

    public Map<Integer, List<BlockPosition>> getBlockIdToBlockPositionsMap() {
        return blockIdToBlockPositionsMap;
    }

    public List<BlockPosition> getBlockPositionsByBlockId(Integer blockId) {
        return blockIdToBlockPositionsMap.get(blockId);
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public int getSize() {
        return this.puzzleHeight * this.puzzleWidth;
    }

    BlockPuzzle(int width, int height, String[] blockValues) {
        this.puzzleWidth = width;
        this.puzzleHeight = height;
        int blockId = 0;
        for (String blockValue : blockValues) {
            blocks.add(new Block(this, blockValue, blockId));
            blockId++;
        }
        this.blockCount = blocks.size();
        blocksById = new Block[blocks.size()];
        for (Block block : blocks) {
            blocksById[block.id] = block;
        }
        for (Block block : blocks) {
            blockIdToBlockPositionsMap.put(block.id, generateBlockPositions(block));
            if (block.weight == 1) {
                weightOneCount++;
            }
        }
        this.blockPositionsById = new BlockPosition[this.positionCount];
        for (List<BlockPosition> blockPositions : blockIdToBlockPositionsMap.values()) {
            for (BlockPosition blockPosition : blockPositions) {
                this.blockPositionsById[blockPosition.id] = blockPosition;
            }
        }
        intersectBlockPositions();
        setCoverableBlocks();
    }

    Block getBlockById(int id) {
        return this.blocksById[id];
    }

    BlockPosition getBlockPositionById(int id) {
        return this.blockPositionsById[id];
    }

    private void setCoverableBlocks() {
        for (int i = 0; i < blockCount; i++) {
            Block blockI = blocks.get(i);
            for (int j = i + 1; j < blockCount; j++) {
                Block blockJ = blocks.get(j);
                if (blockI.isCoverable(blockJ)) {
                    blockI.coverableBlockIds.add(blockJ.id);
                }
            }
        }
    }

    private void intersectBlockPositions() {
        for (BlockPosition blockPosition : this.blockPositionsById) {
            blockPosition.isPositionIdIntersect = new boolean[this.positionCount];
            blockPosition.isPositionIdIntersectBitSet = new BitSet(this.positionCount);
        }
        for (int i = 0; i < blockCount; i++) {
            List<BlockPosition> blockIPositions = blockIdToBlockPositionsMap.get(i);
            for (int j = i + 1; j < blockCount; j++) {
                List<BlockPosition> blockJPositions = blockIdToBlockPositionsMap.get(j);
                for (BlockPosition blockIPosition : blockIPositions) {
                    for (BlockPosition blockJPosition : blockJPositions) {
                        if (blockIPosition.isIntersect(blockJPosition)) {
                            blockIPosition.addIntersectPosition(blockJPosition);
                            blockJPosition.addIntersectPosition(blockIPosition);
                        }
                    }
                }
            }
        }
    }

    private List<BlockPosition> generateBlockPositions(Block block) {
        List<BlockPosition> blockPositions = new ArrayList<BlockPosition>();
        block.positionIdFrom = this.positionCount;
        for (int i = 0; i < puzzleWidth - block.width + 1; i++) {
            for (int j = 0; j < puzzleHeight - block.height + 1; j++) {
                blockPositions.add(new BlockPosition(this, block, new Position(i, j)));
            }
        }
        block.positionIdTo = this.positionCount - 1;
        return blockPositions;
    }
}
