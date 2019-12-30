package com.samabcde.puzzlesolver;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class BlockPosition implements Comparable<BlockPosition> {
	final Logger logger = LoggerFactory.getLogger(BlockPosition.class);
	/**
	 * 
	 */
	private final BlockPuzzle blockPuzzle;
	boolean[] isPositionIdIntersect;
	BitSet isPositionIdIntersectBitSet;

	public boolean[] getIsPositionIdIntersect() {
		return isPositionIdIntersect;
	}

	Block block;

	public Block getBlock() {
		return block;
	}

	private Position position;
	int intersectCount;
	private int intersectScore = Integer.MIN_VALUE;
	private int intersectScore2 = Integer.MIN_VALUE;
	List<Integer> intersectPositionIds = new ArrayList<Integer>();

	public List<Integer> getIntersectPositionIds() {
		return intersectPositionIds;
	}

	int iterateCount = 0;
	int equalsCount = 0;
	int[] fillablePoints;

	public int[] getFillablePoints() {
		return fillablePoints;
	}

	public int getIntersectScore() {
		if (intersectScore != Integer.MIN_VALUE) {
			return intersectScore;
		}
		int blockCount = this.blockPuzzle.blockCount;
		intersectScore = this.intersectCount;

		// for (Block block : BlockPuzzle.this.blocks) {
		for (int i = 0; i < this.blockPuzzle.positionCount; i++) {
			if (!this.isPositionIdIntersect[i]) {
				continue;
			}
			BlockPosition blockPosition = this.blockPuzzle.blockPositionsById[i];
			double blockPriorityScore = 10;
			double blockExtraScoreFactor = 3 / (2 + blockPosition.block.priority);
			double positionExtraScoreFactor = 1;
			if (blockPosition.block.priority < this.block.priority) {
				positionExtraScoreFactor = Math.pow(10, 2 / (1 + blockPosition.priority));
			}
			// double blockFactor = blockCount /
			// (blockPosition.block.priority + blockCount);
			// double positionFactor =
			// blockPositionsOfBlock.get(blockPosition.block.id).size()
			// / (1 + blockPosition.priority);
			// int score = (int) Math.ceil(100 * Math.pow(positionFactor,
			// blockFactor));
			int score = (int) Math.ceil(Math.pow(blockPriorityScore, blockExtraScoreFactor) * positionExtraScoreFactor);
			// if (Collections.binarySearch(this.getIntersectPositions(),
			// blockPosition) > -1) {
			intersectScore += score;
			// }
			// else {
			// intersectScore -= score;
			// }
		}
		return intersectScore;
	}

	public int getIntersectScore2() {
		if (intersectScore2 != Integer.MIN_VALUE) {
			return intersectScore2;
		}
		int blockCount = this.blockPuzzle.blockCount;
		if (blockCount > 0) {
			return 0;
		}
		intersectScore2 = 0;
		int averageIntersectCount = this.block.getAverageIntersectCount();
		for (Block block : this.blockPuzzle.blocks) {
			for (BlockPosition blockPosition : this.blockPuzzle.blockPositionsOfBlock.get(block.id)) {
				double blockPriorityScore = 10;
				double blockExtraScoreFactor = 3 / (2 + blockPosition.block.priority);
				double positionExtraScoreFactor = 1;
				if (blockPosition.block.priority < this.block.priority) {
					double ratio1 = (double) blockPosition.intersectCount
							/ (double) blockPosition.block.getAverageIntersectCount();
					// double ratio2 = (double) this.intersectCount /
					// (double) this.block.getAverageIntersectCount();
					positionExtraScoreFactor = Math.pow(10, 2 / (1 + ratio1));
				}
				// double blockFactor = blockCount /
				// (blockPosition.block.priority + blockCount);
				// double positionFactor =
				// blockPositionsOfBlock.get(blockPosition.block.id).size()
				// / (1 + blockPosition.priority);
				// int score = (int) Math.ceil(100 *
				// Math.pow(positionFactor,
				// blockFactor));
				int score = (int) Math
						.ceil(Math.pow(blockPriorityScore, blockExtraScoreFactor) * positionExtraScoreFactor);
				if (this.isPositionIntersect(blockPosition.id)) {
					intersectScore2 += score;
				} /*
					 * else { intersectScore2 -= score; }
					 */
			}
		}

		return intersectScore2;
	}

	public boolean isPositionIntersect2(int id) {
		return Collections.binarySearch(this.intersectPositionIds, id) > -1;
	}

	public boolean isPositionIntersect(int id) {
		return this.isPositionIdIntersect[id];
	}

	public boolean isPositionIntersectAll(Collection<BlockPosition> blockPositions) {
		for (BlockPosition blockPosition : blockPositions) {
			if (!this.isPositionIntersect(blockPosition.id)) {
				return false;
			}
		}
		return true;
	}

	public int getIntersectCount() {
		return intersectCount;
	}

	private int priority;

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	final int id;

	public BlockPosition(BlockPuzzle blockPuzzle, Block block, Position position) {
		super();
		this.blockPuzzle = blockPuzzle;
		this.id = this.blockPuzzle.positionCount;
		this.blockPuzzle.positionCount++;
		this.block = block;
		this.position = position;
		this.fillablePoints = new int[block.weight];
		int pointIndex = 0;
		for (int rowIndex = this.position.y; rowIndex < this.position.y + this.block.height; rowIndex++) {
			for (int columnIndex = this.position.x; columnIndex < this.position.x + this.block.width; columnIndex++) {
				if (this.get(rowIndex, columnIndex)) {
					this.fillablePoints[pointIndex] = this.blockPuzzle.puzzleWidth * rowIndex + columnIndex;
					pointIndex++;
				}
			}
		}
	}

	public boolean isIntersect(BlockPosition other) {
		int startRow = Math.max(this.position.y, other.position.y);
		int startCol = Math.max(this.position.x, other.position.x);
		int endRow = Math.min(this.position.y + this.block.height, other.position.y + other.block.height);
		int endCol = Math.min(this.position.x + this.block.width, other.position.x + other.block.width);
		for (int i = startRow; i < endRow; i++) {
			for (int j = startCol; j < endCol; j++) {
				if (this.get(i, j) && other.get(i, j)) {
					return true;
				}
			}
		}
		return false;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public boolean get(int row, int col) {
		if (row - position.y < 0 || row - position.y > block.height - 1) {
			return false;
		}
		if (col - position.x < 0 || col - position.x > block.width - 1) {
			return false;
		}
		return block.get(row - position.y, col - position.x);

	}

	int hashCode = -1;

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public String toString() {
		return "position:" + position + ",block:" + System.lineSeparator() + block.toString();
	}

	@Override
	public boolean equals(Object obj) {
		equalsCount++;
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BlockPosition other = (BlockPosition) obj;
		return this.id == other.id;
	}

	@Override
	public int compareTo(BlockPosition arg0) {

		return this.id - arg0.id;
	}

}