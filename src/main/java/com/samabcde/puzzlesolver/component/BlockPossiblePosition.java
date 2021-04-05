package com.samabcde.puzzlesolver.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BlockPossiblePosition {
	private final int[] possiblePositionCountOfBlocks;
	private final int[] intersectionCountOfBlockPositions;
	private final int[] addedPositionPriorityOfBlocks;
	private final BlockPriorityComparator blockPrioriyComparator;

	public BlockPriorityComparator getBlockPrioriyComparator() {
		return blockPrioriyComparator;
	}

	BlockPossiblePosition(BlockPuzzle blockPuzzle) {
		this.blockPrioriyComparator = new BlockPriorityComparator();
		this.intersectionCountOfBlockPositions = new int[blockPuzzle.blockPositionsById.length];
		this.addedPositionPriorityOfBlocks = new int[blockPuzzle.blockCount];
		List<Block> blocks = blockPuzzle.getBlocks();
		this.possiblePositionCountOfBlocks = new int[blocks.size()];
		for (Block block : blocks) {
			this.possiblePositionCountOfBlocks[block.id] = block.getPositionIdTo() - block.getPositionIdFrom() + 1;
			this.addedPositionPriorityOfBlocks[block.id] = -1;
		}

	}

	private BlockPossiblePosition(BlockPossiblePosition blockPossiblePosition) {
		this.blockPrioriyComparator = new BlockPriorityComparator();
		this.intersectionCountOfBlockPositions = Arrays.copyOf(blockPossiblePosition.intersectionCountOfBlockPositions,
				blockPossiblePosition.intersectionCountOfBlockPositions.length);
		this.possiblePositionCountOfBlocks = Arrays.copyOf(blockPossiblePosition.possiblePositionCountOfBlocks,
				blockPossiblePosition.possiblePositionCountOfBlocks.length);
		this.addedPositionPriorityOfBlocks = Arrays.copyOf(blockPossiblePosition.addedPositionPriorityOfBlocks,
				blockPossiblePosition.addedPositionPriorityOfBlocks.length);
	}

	boolean isBlockHasPossibleBlockPosition(Block block) {
		List<BlockPosition> blockPositions = block.getBlockPositions();
		int positionPriorityFrom = this.getAddedPositionPriorityOfBlocks()[block.id] + 1;
		int positionPriorityTo = blockPositions.size() - 1;

		for (int i = positionPriorityFrom; i <= positionPriorityTo; i++) {
			if (this.getIntersectionCountOfBlockPositions()[blockPositions.get(i).id] > 0) {
				continue;
			}
			if (this.getPossiblePositionCountOfBlocks()[block.id] == 0) {
				throw new RuntimeException("should have no possible position");
			}
			return true;
		}
		return false;
	}

	List<BlockPosition> getPossibleBlockPosition(Block block) {
		List<BlockPosition> possibleBlockPositions = new ArrayList<BlockPosition>();
		List<BlockPosition> blockPositions = block.getBlockPositions();

		int positionPriorityFrom = this.addedPositionPriorityOfBlocks[block.id] + 1;
		int positionPriorityTo = blockPositions.size() - 1;

		for (int i = positionPriorityFrom; i <= positionPriorityTo; i++) {
			if (this.intersectionCountOfBlockPositions[blockPositions.get(i).id] == 0) {
				possibleBlockPositions.add(blockPositions.get(i));
			}
		}
		return possibleBlockPositions;
	}

	public BlockPossiblePosition clone() {
		return new BlockPossiblePosition(this);
	}

	public int[] getPossiblePositionCountOfBlocks() {
		return possiblePositionCountOfBlocks;
	}

	public int[] getIntersectionCountOfBlockPositions() {
		return intersectionCountOfBlockPositions;
	}

	public int[] getAddedPositionPriorityOfBlocks() {
		return addedPositionPriorityOfBlocks;
	}

	private class BlockPriorityComparator implements Comparator<Block> {

		@Override
		public int compare(Block arg0, Block arg1) {
			BlockPossiblePosition blockPossiblePosition = BlockPossiblePosition.this;
			if (blockPossiblePosition.getPossiblePositionCountOfBlocks()[arg0.id] != blockPossiblePosition
					.getPossiblePositionCountOfBlocks()[arg1.id]) {
				return Integer.compare(blockPossiblePosition.getPossiblePositionCountOfBlocks()[arg0.id],
						blockPossiblePosition.getPossiblePositionCountOfBlocks()[arg1.id]);
			}
			if (arg0.getAverageIntersectCount() != arg1.getAverageIntersectCount()) {
				return -Integer.compare(arg0.getAverageIntersectCount(), arg1.getAverageIntersectCount());
			}
			if (arg0.getWeight() != arg1.getWeight()) {
				return -Integer.compare(arg0.getWeight(), arg1.getWeight());
			}
			if (arg0.getSize() != arg1.getSize()) {
				return -Integer.compare(arg0.getSize(), arg1.getSize());
			}
			return Integer.compare(arg0.id, arg1.id);
		}
	}
}
