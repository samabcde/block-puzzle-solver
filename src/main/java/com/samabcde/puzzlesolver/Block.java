package com.samabcde.puzzlesolver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Block {
	final Logger logger = LoggerFactory.getLogger(Block.class);
	/**
	 * 
	 */
	private final BlockPuzzle blockPuzzle;
	final int id;
	private boolean[] value;
	int height;
	int width;
	int totalIntersectCount = 0;
	Set<Integer> coverableBlockIds = new HashSet<Integer>();
	int positionIdFrom;
	int positionIdTo;
	int minIntersectCount = Integer.MAX_VALUE;

	public int getMinIntersectCount() {
		return minIntersectCount;
	}

	public int getPositionIdFrom() {
		return positionIdFrom;
	}

	public int getPositionIdTo() {
		return positionIdTo;
	}

	public Set<Integer> getCoverableBlockIds() {
		return coverableBlockIds;
	}

	public boolean isCoverable(Block block) {
		return this.height >= block.height && this.width >= block.width;
	}

	public int getTotalIntersectCount() {
		return totalIntersectCount;
	}

	public int getAverageIntersectCount() {
		return totalIntersectCount / this.blockPuzzle.blockPositionsOfBlock.get(this.id).size();
	}

	private String valueView;

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getRelativeWidth() {
		return width * this.blockPuzzle.puzzleWidth;
	}

	public int getRelativeHeight() {
		return height * this.blockPuzzle.puzzleWidth;
	}

	public int getRelativeSize() {
		return getRelativeWidth() + getRelativeHeight();
	}

	int weight;

	public int getWeight() {
		return weight;
	}

	int priority;

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	Block(BlockPuzzle blockPuzzle, String value) {
		this.blockPuzzle = blockPuzzle;
		this.id = this.blockPuzzle.blockCount;
		this.blockPuzzle.blockCount++;
		String[] rows = value.split(",");
		initializeSize(rows);
		initializeValueAndWeight(rows);
	}
	
	public List<BlockPosition> getBlockPositions() {
		return this.blockPuzzle.blockPositionsOfBlock.get(this.id);
	}
	
	@Override
	public String toString() {
		if (valueView != null) {
			return valueView;
		}
		String lineSeparator = System.getProperty("line.separator");
		StringBuilder stringBuilder = new StringBuilder(this.width * this.height);
		for (int rowIndex = 0; rowIndex < this.height; rowIndex++) {
			for (int colIndex = 0; colIndex < this.width; colIndex++) {
				if (this.get(rowIndex, colIndex)) {
					stringBuilder.append('1');
				} else {
					stringBuilder.append('0');
				}
			}
			if (rowIndex < this.height - 1) {
				stringBuilder.append(lineSeparator);
			}
		}
		valueView = stringBuilder.toString();
		return valueView;
	}

	private void initializeValueAndWeight(String[] rows) {
		this.weight = 0;
		this.value = new boolean[width * height];
		for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
			char[] row = rows[rowIndex].toCharArray();
			for (int colIndex = 0; colIndex < row.length; colIndex++) {
				if (row[colIndex] == '1') {
					weight++;
					this.set(rowIndex, colIndex, true);
				}
			}
		}
	}

	private void initializeSize(String[] rows) {
		height = rows.length;
		int maxColCount = 0;
		for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
			maxColCount = Math.max(rows[rowIndex].length(), maxColCount);
		}
		this.width = maxColCount;
	}

	public boolean get(int rowIndex, int columnIndex) {
		return value[width * rowIndex + columnIndex];
	}

	private void set(int rowIndex, int columnIndex, boolean to) {
		this.value[width * rowIndex + columnIndex] = to;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Block other = (Block) obj;

		return this.id == other.id;
	}

	@Override
	public int hashCode() {
		return id;
	}
}