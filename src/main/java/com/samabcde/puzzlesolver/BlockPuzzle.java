package com.samabcde.puzzlesolver;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockPuzzle {
	final Logger logger = LoggerFactory.getLogger(BlockPuzzle.class);
	int blockCount = 0;
	int positionCount = 0;
	int[][] pointFillablePositionId;

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

	final int puzzleWidth;
	private final int puzzleHeight;
	Map<Integer, List<BlockPosition>> blockPositionsOfBlock = new HashMap<Integer, List<BlockPosition>>();
	Block[] blocksById;
	BlockPosition[] blockPositionsById;

	public Map<Integer, List<BlockPosition>> getBlockPositionsOfBlock() {
		return blockPositionsOfBlock;
	}

	public List<BlockPosition> getBlockPositionsByBlockId(Integer blockId) {
		return blockPositionsOfBlock.get(blockId);
	}

	List<Block> blocks = new ArrayList<Block>();

	public List<Block> getBlocks() {
		return blocks;
	}

	public int getSize() {
		return this.puzzleHeight * this.puzzleWidth;
	}

	BlockPuzzle(int width, int height, String[] blockValues) {
		this.puzzleWidth = width;
		this.puzzleHeight = height;
		for (String blockValue : blockValues) {
			blocks.add(new Block(this, blockValue));
		}
		blocksById = new Block[blocks.size()];
		for (Block block : blocks) {
			blocksById[block.id] = block;
		}
		for (Block block : blocks) {
			blockPositionsOfBlock.put(block.id, generateBlockPositions(block));
			if (block.weight == 1) {
				weightOneCount++;
			}
		}
		this.blockPositionsById = new BlockPosition[this.positionCount];
		for (List<BlockPosition> blockPositions : blockPositionsOfBlock.values()) {
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
			List<BlockPosition> blockIPositions = blockPositionsOfBlock.get(i);
			for (int j = i + 1; j < blockCount; j++) {
				List<BlockPosition> blockJPositions = blockPositionsOfBlock.get(j);
				for (BlockPosition blockIPosition : blockIPositions) {
					for (BlockPosition blockJPosition : blockJPositions) {
						if (blockIPosition.isIntersect(blockJPosition)) {
							blockIPosition.intersectCount++;
							blockIPosition.block.totalIntersectCount++;
							blockIPosition.isPositionIdIntersect[blockJPosition.id] = true;
							blockIPosition.isPositionIdIntersectBitSet.set(blockJPosition.id);
							blockIPosition.intersectPositionIds.add(blockJPosition.id);
							// blockIPosition.intersectPositions.add(blockJPosition);
							blockJPosition.intersectCount++;
							blockJPosition.block.totalIntersectCount++;
							blockJPosition.isPositionIdIntersect[blockIPosition.id] = true;
							blockJPosition.isPositionIdIntersectBitSet.set(blockIPosition.id);
							blockJPosition.intersectPositionIds.add(blockIPosition.id);
							// blockJPosition.intersectPositions.add(blockIPosition);
						}
					}
				}
			}

		}
		for (int i = 0; i < blockCount; i++) {
			Block blockI = blocks.get(i);
			List<BlockPosition> blockIPositions = blockPositionsOfBlock.get(i);
			for (BlockPosition blockIPosition : blockIPositions) {
				blockI.minIntersectCount = Math.min(blockI.minIntersectCount, blockIPosition.intersectCount);
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
