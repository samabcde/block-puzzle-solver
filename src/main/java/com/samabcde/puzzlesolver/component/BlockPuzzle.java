package com.samabcde.puzzlesolver.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BlockPuzzle {
    public final int blockCount;
    private int positionCount = 0;
    private final Dimension puzzleDimension;
    private final Map<Integer, List<BlockPosition>> blockIdToBlockPositionsMap = new HashMap<>();
    private Block[] blocksById;
    private final Map<Shape, List<Block>> shapeToBlocksMap;
    private final BlockPosition[] blockPositionsById;
    private final List<Block> blocks = new ArrayList<>();

    public int getBlockIdByBlockPositionId(int blockPositionId) {
        return this.blockPositionsById[blockPositionId].getBlock().id;
    }

    public int getPositionCount() {
        return positionCount;
    }

    public int getPuzzleWidth() {
        return puzzleDimension.width();
    }

    public int getPuzzleHeight() {
        return puzzleDimension.height();
    }

    public Map<Integer, List<BlockPosition>> getBlockIdToBlockPositionsMap() {
        return blockIdToBlockPositionsMap;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public int getSize() {
        return puzzleDimension.height() * puzzleDimension.width();
    }

    public BlockPuzzle(Dimension dimension, String[] blockValues) {
        this.puzzleDimension = dimension;
        int blockId = 0;
        for (String blockValue : blockValues) {
            blocks.add(new Block(blockValue, blockId));
            blockId++;
        }
        this.blockCount = blocks.size();
        blocksById = new Block[blocks.size()];
        for (Block block : blocks) {
            blocksById[block.id] = block;
        }
        for (Block block : blocks) {
            blockIdToBlockPositionsMap.put(block.id, generateBlockPositions(block));
        }
        this.blockPositionsById = new BlockPosition[this.positionCount];
        for (List<BlockPosition> blockPositions : blockIdToBlockPositionsMap.values()) {
            for (BlockPosition blockPosition : blockPositions) {
                this.blockPositionsById[blockPosition.id] = blockPosition;
            }
        }
        intersectBlockPositions();
        for (Block block : blocks) {
            block.setBlockPositions(blockIdToBlockPositionsMap.get(block.id));
        }
        setCoverableBlocks();
        this.shapeToBlocksMap = this.blocks.stream().collect(Collectors.groupingBy(Block::getShape));
    }

    public Block getBlockById(int id) {
        return this.blocksById[id];
    }

    public BlockPosition getBlockPositionById(int id) {
        return this.blockPositionsById[id];
    }

    public List<Block> getBlocksWithSameShape(Shape shape) {
        return shapeToBlocksMap.get(shape);
    }

    private void setCoverableBlocks() {
        for (int i = 0; i < blockCount; i++) {
            Block blockI = blocks.get(i);
            for (int j = i + 1; j < blockCount; j++) {
                Block blockJ = blocks.get(j);
                if (blockI.isCoverable(blockJ)) {
                    blockI.getCoverableBlockIds().add(blockJ.id);
                }
            }
        }
    }

    private void intersectBlockPositions() {
        for (BlockPosition blockPosition : this.blockPositionsById) {
            blockPosition.initializeIntersection(positionCount);
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
        List<BlockPosition> blockPositions = new ArrayList<>();
        block.positionIdFrom = positionCount;
        int noOfPosition = (getPuzzleWidth() - block.getWidth() + 1) * (getPuzzleHeight() - block.getHeight() + 1);
        block.positionIdTo = block.positionIdFrom + noOfPosition - 1;
        for (int i = 0; i < getPuzzleWidth() - block.getWidth() + 1; i++) {
            for (int j = 0; j < getPuzzleHeight() - block.getHeight() + 1; j++) {
                blockPositions.add(new BlockPosition(this.puzzleDimension, block, new Position(i, j), positionCount));
                positionCount++;
            }
        }
        return blockPositions;
    }

    public void assertValid() {
        if (blocks.stream().mapToInt(Block::getWeight).sum() < this.getPuzzleHeight() * this.getPuzzleWidth()) {
            throw new IllegalStateException("Block weight:%d does not match Puzzle size:%d".formatted(blocks.stream().mapToInt(Block::getWeight).sum(), this.getPuzzleHeight() * this.getPuzzleWidth()));
        }
    }
}
