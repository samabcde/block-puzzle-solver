package com.samabcde.puzzlesolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockPuzzleSolver {
	final Logger logger = LoggerFactory.getLogger(BlockPuzzleSolver.class);
	BlockPuzzle blockPuzzle;
	Deque<BlockPosition> solutions = new LinkedList<BlockPosition>();
	IntersectionsOfBlockPosition intersectionsOfBlockPosition;
	SolutionBoard solutionBoard;
	long[] intersectSolutionCount;
	long[] noRemainingCount;
	long[] canNotFillAllPointsCount;
	long[] noRemainingCountBy;
	long[] noRemainingCountByLastOneIntersect;
	long[] noRemainingCountByAllIntersect;

	BlockPuzzleSolver(BlockPuzzle blockPuzzle) {
		this.solutionBoard = new SolutionBoard(blockPuzzle);
		this.blockPuzzle = blockPuzzle;
		this.intersectionsOfBlockPosition = new IntersectionsOfBlockPosition();
		intersectSolutionCount = new long[blockPuzzle.getBlocks().size()];
		noRemainingCount = new long[blockPuzzle.getBlocks().size()];
		noRemainingCountByAllIntersect = new long[blockPuzzle.getBlocks().size()];
		noRemainingCountByLastOneIntersect = new long[blockPuzzle.getBlocks().size()];
		canNotFillAllPointsCount = new long[blockPuzzle.getBlocks().size()];
	}

	public class IntersectionsOfBlockPosition {
		int[] intersectionCountOfBlockPosition;

		IntersectionsOfBlockPosition() {
			intersectionCountOfBlockPosition = new int[BlockPuzzleSolver.this.blockPuzzle.getPositionCount()];
		}

		void add(BlockPosition intersection) {
			List<Integer> intersectPositionIds = intersection.getIntersectPositionIds();
			for (Integer intersectPositionId : intersectPositionIds) {
				intersectionCountOfBlockPosition[intersectPositionId]++;
			}
		}

		void remove(BlockPosition intersection) {
			List<Integer> intersectPositionIds = intersection.getIntersectPositionIds();
			for (Integer intersectPositionId : intersectPositionIds) {
				intersectionCountOfBlockPosition[intersectPositionId]--;
			}
		}

		boolean isIntersectSolutions(BlockPosition blockPosition) {
			return intersectionCountOfBlockPosition[blockPosition.id] > 0;
		}
	}

	public class RemainingPositions {
		int[] intersectionCountOfBlockPosition;

		List<BlockPosition> remainingPositions = new ArrayList<BlockPosition>();
		Set<Integer> remainingPositionIds = new HashSet<Integer>();
		Set<Integer> remainingPositionAllIntersectIds = null;
		boolean remainingPositionsRemoved = false;
		int size = 0;
		int positionIdFrom = -1;
		int positionIdTo = -1;

		RemainingPositions(Block block) {
			positionIdFrom = block.getPositionIdFrom();
			positionIdTo = block.getPositionIdTo();
			intersectionCountOfBlockPosition = new int[BlockPuzzleSolver.this.blockPuzzle.getPositionCount()];
		}

		public Set<Integer> getRemainingPositionAllIntersectIds() {
			if (remainingPositionAllIntersectIds == null) {
				remainingPositionAllIntersectIds = new HashSet<Integer>();
			}
			if (!remainingPositionsRemoved) {
				return remainingPositionAllIntersectIds;
			}
			for (int i = 0, length = intersectionCountOfBlockPosition.length; i < length; i++) {
				if (intersectionCountOfBlockPosition[i] == remainingPositionIds.size()) {
					remainingPositionAllIntersectIds.add(i);
				}
			}
			remainingPositionsRemoved = false;
			return remainingPositionAllIntersectIds;
		}

		void addRemaining(Integer remainingPositionId) {
			if (remainingPositionId < positionIdFrom || remainingPositionId > positionIdTo) {
				return;
			}
			BlockPosition remainingPosition = BlockPuzzleSolver.this.blockPuzzle
					.getBlockPositionById(remainingPositionId);

			remainingPositionIds.add(remainingPosition.id);

			size++;
			for (Integer intersectPositionId : remainingPosition.getIntersectPositionIds()) {
				intersectionCountOfBlockPosition[intersectPositionId]++;
			}

		}

		void removeRemaining(Integer remainingPositionId) {
			if (remainingPositionId < positionIdFrom || remainingPositionId > positionIdTo) {
				return;
			}
			BlockPosition remainingPosition = BlockPuzzleSolver.this.blockPuzzle
					.getBlockPositionById(remainingPositionId);

			remainingPositionsRemoved = remainingPositionIds.remove(remainingPosition.id);
			if (remainingPositionsRemoved) {
				size--;
				for (Integer intersectPositionId : remainingPosition.getIntersectPositionIds()) {
					intersectionCountOfBlockPosition[intersectPositionId]--;
				}
			}
		}

		boolean isEmpty() {
			return size == 0;
		}

		int size() {
			return size;
		}
	}

	public class Board {

		private Map<Block, List<BlockPosition>> remainingPositions;
		private int[] intersectCountOfBlockPosition;
		private Map<Position, List<BlockPosition>> fillablePoint;
		private List<BlockPosition> currentPositionsOnBoard;
		
	}

	public Collection<BlockPosition> solve() {
		List<Long> executeTime = new ArrayList<Long>();
		executeTime.add(new Date().getTime());
		sortBlocks();
		sortBlockPositions();
		executeTime.add(new Date().getTime());
		Map<Integer, List<BlockPosition>> blockPositionsOfBlock = this.blockPuzzle.getBlockPositionsOfBlock();

		int startFromPositionIndex = 0;
		boolean canSolve = false;
		long totalIterate = 0;
		List<Block> blocks = this.blockPuzzle.getBlocks();
		long[] blockIterateCount = new long[blocks.size()];
		for (int i = 0; i < blocks.size();) {
			Block block = blocks.get(i);
			List<BlockPosition> blockPositions = blockPositionsOfBlock.get(block.id);
			boolean hasValidBlockPosition = false;

			for (int j = startFromPositionIndex; j < blockPositions.size(); j++) {
				totalIterate++;
				blockIterateCount[i]++;
				BlockPosition blockPosition = blockPositions.get(j);
				blockPosition.iterateCount++;
				for (BlockPosition solution : solutions) {
					solution.iterateCount++;
				}
				if (isValidBlockPosition(blockPosition)) {
					hasValidBlockPosition = true;
					addBlockPositionToSolution(blockPosition);
					break;
				}
			}
			if (hasValidBlockPosition) {
				startFromPositionIndex = 0;
				i++;
			} else {
				startFromPositionIndex = removeLastBlockPositionFromSolution();
				i--;
			}
			if (i == -1) {
				canSolve = false;
				break;
			}
			if (i == blocks.size()) {
				canSolve = true;
				break;
			}
		}
		executeTime.add(new Date().getTime());
		if (canSolve) {

			for (BlockPosition blockPosition : solutions) {
				logger.info("Block Priority:" + blockPosition.getBlock().getPriority()
						+ "Position Priority/Total Priority:" + blockPosition.getPriority() + "/"
						+ (blockPositionsOfBlock.get(blockPosition.getBlock().id).size() - 1));
			}
			for (Block block : blocks) {
				logger.info("Block Priority:" + block.getPriority());
				logger.info("ID\t\tP1\t\tP2\t\tIC\t\tIS\t\tIS2\t\tIT");
				List<Integer> intersectScores2 = new ArrayList<Integer>();
				for (BlockPosition blockPosition : blockPositionsOfBlock.get(block.id)) {
					intersectScores2.add(blockPosition.getIntersectScore2());
				}
				Collections.sort(intersectScores2);
				for (BlockPosition blockPosition : blockPositionsOfBlock.get(block.id)) {
					logger.info((solutions.contains(blockPosition) ? "*" : " ")
							+ blockPosition.getPosition().toString() + "\t\t" + blockPosition.getPriority() + "\t\t"
							+ intersectScores2.indexOf(blockPosition.getIntersectScore2()) + "\t\t"
							+ blockPosition.getIntersectCount() + "\t\t" + blockPosition.getIntersectScore() + "\t\t"
							+ blockPosition.getIntersectScore2() + "\t\t" + blockPosition.iterateCount);
				}
			}

			logger.info("Solved");

			logger.info(Arrays.toString(blockIterateCount));
			logger.info("Intersect Solution Count:" + Arrays.toString(intersectSolutionCount));
			logger.info("No Remaining Count:" + Arrays.toString(noRemainingCount));
			System.out
					.println("No Remaining Count(By all intersect):" + Arrays.toString(noRemainingCountByAllIntersect));
			logger.info("No Remaining Count(By remaining intersect):"
					+ Arrays.toString(noRemainingCountByLastOneIntersect));
			logger.info("Can not fill all points Count:" + Arrays.toString(canNotFillAllPointsCount));
			// logger.info("Can not fill hole Count:" +
			// Arrays.toString(canNotFillHoleCount));
			logger.info("total iterate:" + totalIterate);
			printSolution();

		} else {
			logger.info("Can not solve");
			logger.info("total iterate:" + totalIterate);
		}
		executeTime.add(new Date().getTime());
		for (int i = 0; i < executeTime.size() - 1; i++) {
			logger.info("Step " + i + "time:" + (executeTime.get(i + 1) - executeTime.get(i)));
		}
		return solutions;
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

	private String solutionView() {
		StringBuilder sb = new StringBuilder();
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
			sb.append(Arrays.toString(solutionRowView));
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}

	private void addBlockPositionToSolution(BlockPosition blockPosition) {
		intersectionsOfBlockPosition.add(blockPosition);
		solutions.addLast(blockPosition);
		solutionBoard.add(blockPosition);
	}

	private boolean isPreAddedBlock(Block block) {
		return false;
	}

	private int removeLastBlockPositionFromSolution() {
		BlockPosition last = solutions.pollLast();
		if (last == null) {
			return -1;
		}
		intersectionsOfBlockPosition.remove(last);
		solutionBoard.remove(last);
		return last.getPriority() + 1;
	}

	private boolean isValidBlockPosition(BlockPosition checkingBlockPosition) {
		int solutionSize = this.solutions.size();
		if (this.intersectionsOfBlockPosition.isIntersectSolutions(checkingBlockPosition)) {
			intersectSolutionCount[solutionSize]++;
			return false;
		}
		List<Set<BlockPosition>> remainingPositionsOfBlocks = new ArrayList<Set<BlockPosition>>();
		if (!existPossiblePositionsForRemainingBlocks(checkingBlockPosition, remainingPositionsOfBlocks)) {
			noRemainingCount[solutionSize]++;
			return false;
		}
		Set<BlockPosition> remainLastPositions = new HashSet<BlockPosition>();
		for (Set<BlockPosition> remainingPositionsOfBlock : remainingPositionsOfBlocks) {
			if (remainingPositionsOfBlock.size() == 1) {
				remainLastPositions
						.add(this.blockPuzzle.getBlockPositionById(remainingPositionsOfBlock.iterator().next().id));
			}
		}
		if (!canFillAllPoints(checkingBlockPosition, remainLastPositions)) {
			canNotFillAllPointsCount[solutionSize]++;
			return false;
		}
		return true;
	}

	private boolean existPossiblePositionsForRemainingBlocks(BlockPosition checkingBlockPosition,
			List<Set<BlockPosition>> remainingPositionsOfBlocks) {
		Map<Integer, List<BlockPosition>> blockPositionsOfBlock = this.blockPuzzle.getBlockPositionsOfBlock();
		int index = checkingBlockPosition.getBlock().getPriority();

		List<Block> blocks = this.blockPuzzle.getBlocks();
		boolean[] isBlocksSkippable = new boolean[blocks.size()];
		for (int i = index + 1; i < blocks.size(); i++) {
			Block block = blocks.get(i);
			if (isBlocksSkippable[block.id]) {
				continue;
			}
			List<BlockPosition> blockPositions = blockPositionsOfBlock.get(block.id);
			boolean hasNonIntersectPosition = false;
			int remainingCount = 0;
			Set<BlockPosition> remainingBlockPositions = new HashSet<BlockPosition>();
			// RemainingPositions remainingBlockPositions = new
			// RemainingPositions(block);
			for (BlockPosition blockPosition : blockPositions) {
				if (this.intersectionsOfBlockPosition.isIntersectSolutions(blockPosition)) {
					continue;
				}
				if (checkingBlockPosition.isPositionIntersect(blockPosition.id)) {
					continue;
				}
				hasNonIntersectPosition = true;
				remainingCount++;
				remainingBlockPositions.add(blockPosition);
				// remainingBlockPositions.addRemaining(blockPosition.id);
			}
			if (remainingCount > 1) {
				for (Integer coverableBlockId : block.getCoverableBlockIds()) {
					isBlocksSkippable[coverableBlockId] = true;
				}
			}
			if (!hasNonIntersectPosition) {
				noRemainingCountByAllIntersect[this.solutions.size()]++;
				return false;
			}
			remainingPositionsOfBlocks.add(remainingBlockPositions);

			if (remainingCount == 1) {
				for (int j = remainingPositionsOfBlocks.size() - 1; j >= 0; j--) {
					Set<BlockPosition> remainingBlockPositionsOfBlock = remainingPositionsOfBlocks.get(j);
					if (remainingBlockPositionsOfBlock.size() > 1) {
						continue;
					}
					BlockPosition lastRemaining = remainingBlockPositionsOfBlock.iterator().next();
					// for (int k = j - 1; k >= 0; k--) {
					int redoIndex = -1;
					for (int k = remainingPositionsOfBlocks.size() - 1; k >= 0; k--) {
						if (k == j) {
							continue;
						}
						Set<BlockPosition> otherRemainingBlockPositionsOfBlock = remainingPositionsOfBlocks.get(k);
						Iterator<BlockPosition> remainingBlockPositionsOfBlockIterator = otherRemainingBlockPositionsOfBlock
								.iterator();
						boolean existRemove = false;
						while (remainingBlockPositionsOfBlockIterator.hasNext()) {

							BlockPosition remainingBlockPositionOfBlock = remainingBlockPositionsOfBlockIterator.next();
							if (lastRemaining.isPositionIntersect(remainingBlockPositionOfBlock.id)) {
								remainingBlockPositionsOfBlockIterator.remove();
								existRemove = true;
							}
						}
						if (otherRemainingBlockPositionsOfBlock.size() == 1 && k > j && existRemove) {
							if (redoIndex == -1) {
								redoIndex = k;
							}
						}
						if (otherRemainingBlockPositionsOfBlock.isEmpty()) {
							noRemainingCountByLastOneIntersect[this.solutions.size()]++;
							return false;
						}
					}
					if (redoIndex > -1) {
						j = redoIndex + 1;
					}
				}
			}

		}
		return true;
	}

	private boolean canFillAllPoints(BlockPosition checkingBlockPosition, Set<BlockPosition> remainLastPositions) {
		Map<Integer, List<BlockPosition>> blockPositionsOfBlock = this.blockPuzzle.getBlockPositionsOfBlock();
		int index = checkingBlockPosition.getBlock().getPriority();
		// List<BlockPosition> checkBlockPositionIntersections =
		// checkingBlockPosition.getIntersectPositions();
		boolean[] isIntersectRemaining = new boolean[blockPuzzle.getPositionCount()];
		List<Block> blocks = this.blockPuzzle.getBlocks();
		for (BlockPosition remainLastPosition : remainLastPositions) {
			for (Integer intersectPositionId : remainLastPosition.getIntersectPositionIds()) {
				isIntersectRemaining[intersectPositionId] = true;
			}
			this.solutionBoard.add(remainLastPosition);
		}
		for (int i = blocks.size() - 1; i > index; i--) {
			Block block = blocks.get(i);
			List<BlockPosition> blockPositions = blockPositionsOfBlock.get(block.id);
			for (BlockPosition blockPosition : blockPositions) {
				if (this.intersectionsOfBlockPosition.isIntersectSolutions(blockPosition)) {
					continue;
				}
				if (checkingBlockPosition.isPositionIntersect(blockPosition.id)) {
					continue;
				}
				if (isIntersectRemaining[blockPosition.id]) {
					continue;
				}
				this.solutionBoard.addFillablePositions(blockPosition);
			}
			if (this.solutionBoard.getMinFillableBlockCount(checkingBlockPosition) > 1) {
				this.solutionBoard.clearPointToFillablePositionMap();
				for (BlockPosition remainLastPosition : remainLastPositions) {
					this.solutionBoard.remove(remainLastPosition);
				}
				return true;
			}
		}
		if (this.solutionBoard.existNotFillablePoint(checkingBlockPosition)) {
			for (BlockPosition remainLastPosition : remainLastPositions) {
				this.solutionBoard.remove(remainLastPosition);
			}
			return false;
		}
		for (BlockPosition remainLastPosition : remainLastPositions) {
			this.solutionBoard.remove(remainLastPosition);
		}
		return true;
	}
 
	private void sortBlocks() {
		List<Block> blocks = this.blockPuzzle.getBlocks();
		Collections.sort(blocks, new BlockComparator());
		for (int i = 0; i < blocks.size(); i++) {
			blocks.get(i).setPriority(i);
		}
	}

	private void sortBlockPositions() {
		BlockPositionComparator blockPositionComparator = new BlockPositionComparator();
		for (Block block : this.blockPuzzle.getBlocks()) {
			List<BlockPosition> blockPositions = blockPuzzle.getBlockPositionsOfBlock().get(block.id);
			Collections.sort(blockPositions, blockPositionComparator);
			for (int i = 0; i < blockPositions.size(); i++) {
				blockPositions.get(i).setPriority(i);
			}
		}
	}

	private static class BlockComparator implements Comparator<Block> {

		@Override
		public int compare(Block arg0, Block arg1) {
			/*
			 * if (arg0.getMinIntersectCount() != arg1.getMinIntersectCount()) {
			 * return -Integer.compare(arg0.getMinIntersectCount(),
			 * arg1.getMinIntersectCount()); }
			 */
			if (arg0.getAverageIntersectCount() != arg1.getAverageIntersectCount()) {
				return -Integer.compare(arg0.getAverageIntersectCount(), arg1.getAverageIntersectCount());
			}
			if (arg0.getWeight() != arg1.getWeight()) {
				return -Integer.compare(arg0.getWeight(), arg1.getWeight());
			}
			if (arg0.getRelativeSize() != arg1.getRelativeSize()) {
				return -Integer.compare(arg0.getRelativeSize(), arg1.getRelativeSize());
			}
			return Integer.compare(arg0.id, arg1.id);
		}
	}

	private static class BlockPositionComparator implements Comparator<BlockPosition> {

		@Override
		public int compare(BlockPosition arg0, BlockPosition arg1) {
			if (Integer.compare(arg0.getIntersectScore(), arg1.getIntersectScore()) != 0) {
				return Integer.compare(arg0.getIntersectScore(), arg1.getIntersectScore());
			}
			return Integer.compare(arg0.id, arg1.id);
		}
	}

	private class SolutionBoard {
		private final int width;
		private final int height;
		boolean[] board;
		Point[] points;

		private class Point {
			boolean isFilled = false;
			boolean[] isFilledByPosition;
			boolean[] isFilledByBlock;
			int fillableBlockCount = 0;
			int fillablePositionCount = 0;

			Point(int blockCount, int positionCount) {
				this.isFilledByBlock = new boolean[blockCount];
				this.isFilledByPosition = new boolean[positionCount];
			}

			public int getFirstFillableBlockId() {
				for (int i = 0; i < this.isFilledByBlock.length; i++) {
					if (this.isFilledByBlock[i]) {
						return i;
					}
				}
				throw new RuntimeException("no fillable block");
			}

			public int getFirstFillablePositionId() {
				for (int i = 0; i < this.isFilledByPosition.length; i++) {
					if (this.isFilledByPosition[i]) {
						return i;
					}
				}
				throw new RuntimeException("no fillable position");
			}

			public void addPosition(BlockPosition blockPosition) {
				if (!isFilledByPosition[blockPosition.id]) {
					fillablePositionCount++;
				}
				isFilledByPosition[blockPosition.id] = true;
				if (!isFilledByBlock[blockPosition.getBlock().id]) {
					fillableBlockCount++;
				}
				isFilledByBlock[blockPosition.getBlock().id] = true;
			}

			public void clearFillablePositions() {
				this.fillableBlockCount = 0;
				this.fillablePositionCount = 0;
				Arrays.fill(isFilledByBlock, false);
				Arrays.fill(isFilledByPosition, false);
			}

			public int removeSingularPosition(BlockPosition singularPosition, Block singularBlock) {
				if (this.isFilledByBlock[singularBlock.id]) {
					if (!this.isFilledByPosition[singularPosition.id]) {
						this.isFilledByBlock[singularBlock.id] = false;
						this.fillableBlockCount--;
					}

					for (int i = singularBlock.getPositionIdFrom(); i <= singularBlock.getPositionIdTo(); i++) {
						if (i == singularPosition.id) {
							continue;
						}
						if (this.isFilledByPosition[i]) {
							this.isFilledByPosition[i] = false;
							this.fillablePositionCount--;
						}
					}
				}
				List<Integer> intersectBlockPositionIds = singularPosition.getIntersectPositionIds();
				Set<Block> intersectBlocks = new HashSet<Block>();
				for (Integer intersectBlockPositionId : intersectBlockPositionIds) {
					if (this.isFilledByPosition[intersectBlockPositionId]) {
						intersectBlocks.add(BlockPuzzleSolver.this.blockPuzzle
								.getBlockPositionById(intersectBlockPositionId).getBlock());
						this.isFilledByPosition[intersectBlockPositionId] = false;
						this.fillablePositionCount--;
					}
				}
				int solutionSize = BlockPuzzleSolver.this.solutions.size();

				for (Block block : intersectBlocks) {
					// Block block = blocks.get(i);
					if (!this.isFilledByBlock[block.id]) {
						continue;
					}
					boolean hasPositionFilled = false;
					for (int j = block.getPositionIdFrom(); j <= block.getPositionIdTo(); j++) {
						if (this.isFilledByPosition[j]) {
							hasPositionFilled = true;
							break;
						}
					}
					if (!hasPositionFilled) {
						this.isFilledByBlock[block.id] = false;
						this.fillableBlockCount--;
					}
				}
				return this.fillablePositionCount;
			}

			public int removeOtherPositionWithSameBlock(Block singularBlock, boolean[] isRemovePositionIds) {
				// Block block = blockPositions.iterator().next().getBlock();
				if (!this.isFilledByBlock[singularBlock.id]) {
					return this.fillablePositionCount;
				}

				boolean hasSamePosition = false;
				for (int i = singularBlock.getPositionIdFrom(); i <= singularBlock.getPositionIdTo(); i++) {
					if (isRemovePositionIds[i] && this.isFilledByPosition[i]) {
						hasSamePosition = true;
						continue;
					}
					if (this.isFilledByPosition[i]) {
						this.isFilledByPosition[i] = false;
						this.fillablePositionCount--;
					}
				}

				if (!hasSamePosition) {
					// this.fillableBlocks.remove(block);
					this.isFilledByBlock[singularBlock.id] = false;
					this.fillableBlockCount--;
				}
				return this.fillablePositionCount;
			}

		}

		SolutionBoard(BlockPuzzle blockPuzzle) {
			this.width = blockPuzzle.getPuzzleWidth();
			this.height = blockPuzzle.getPuzzleHeight();
			blockPuzzle.getPositionCount();
			this.board = new boolean[width * height];
			points = new Point[width * height];
			for (int i = 0; i < this.points.length; i++) {
				points[i] = new Point(blockPuzzle.getBlocks().size(), blockPuzzle.getPositionCount());
			}
		}

		public void clearPointToFillablePositionMap() {
			for (int i = 0; i < this.points.length; i++) {
				if (points[i].isFilled) {
					continue;
				}
				points[i].clearFillablePositions();
			}
		}

		public void addFillablePositions(BlockPosition fillableBlockPosition) {
			int[] fillablePoints = fillableBlockPosition.getFillablePoints();

			for (int i = 0; i < fillablePoints.length; i++) {
				points[fillablePoints[i]].addPosition(fillableBlockPosition);
			}

		}

		public int getMinFillableBlockCount(BlockPosition checkingBlockPosition) {
			boolean[] isAlreadyChecked = new boolean[points.length];
			int[] checkedFillablePositions = checkingBlockPosition.getFillablePoints();
			int minFillableBlockCount = Integer.MAX_VALUE;
			for (int i = 0; i < checkedFillablePositions.length; i++) {
				isAlreadyChecked[checkedFillablePositions[i]] = true;
			}
			for (int i = 0; i < points.length; i++) {
				if (points[i].isFilled || isAlreadyChecked[i]) {
					continue;
				}
				minFillableBlockCount = Math.min(minFillableBlockCount, points[i].fillableBlockCount);
			}
			return minFillableBlockCount;
		}

		public boolean existNotFillablePoint(BlockPosition checkingBlockPosition) {
			addFillablePositions(checkingBlockPosition);
			for (int i = 0; i < points.length; i++) {
				if (!points[i].isFilled && points[i].fillablePositionCount == 0) {
					clearPointToFillablePositionMap();
					return true;
				}
			}
			// Set<Block> singularBlocks = new HashSet<Block>();
			boolean hasNewSingularBlock = false;
			boolean hasNewSingularPosition = false;
			boolean[] isAlreadyChecked = new boolean[points.length];
			int[] checkedFillablePositions = checkingBlockPosition.getFillablePoints();
			for (int i = 0; i < checkedFillablePositions.length; i++) {
				isAlreadyChecked[checkedFillablePositions[i]] = true;
			}
			Set<Integer> singularPoints = new HashSet<Integer>();
			Set<Integer> singularPositionIds = new HashSet<Integer>();
			boolean[] isRemovePositionIds = null;
			int singularBlockId = -1;
			int singularPositionId = -1;
			do {
				hasNewSingularBlock = false;
				hasNewSingularPosition = false;
				for (int i = 0; i < points.length; i++) {
					if (points[i].isFilled || isAlreadyChecked[i]) {
						continue;
					}
					if (points[i].fillablePositionCount == 1) {
						singularBlockId = points[i].getFirstFillableBlockId();
						singularPositionId = points[i].getFirstFillablePositionId();
						if (singularPositionIds.add(singularPositionId)) {
							hasNewSingularPosition = true;
							hasNewSingularBlock = true;
							break;
						}
					} else {
						if (points[i].fillableBlockCount == 1) {
							singularBlockId = points[i].getFirstFillableBlockId();
							if (singularPoints.add(i)) {
								hasNewSingularBlock = true;
								isRemovePositionIds = points[i].isFilledByPosition;
								break;
							}
						}
					}

				}
				if (!hasNewSingularBlock) {
					break;
				}

				for (int i = 0; i < points.length; i++) {
					if (points[i].isFilled || isAlreadyChecked[i]) {
						continue;
					}
					int remainingSize = -1;
					Block block = BlockPuzzleSolver.this.blockPuzzle.getBlockById(singularBlockId);
					if (hasNewSingularPosition) {
						BlockPosition singularPosition = BlockPuzzleSolver.this.blockPuzzle
								.getBlockPositionById(singularPositionId);
						remainingSize = points[i].removeSingularPosition(singularPosition, block);

					} else {
						remainingSize = points[i].removeOtherPositionWithSameBlock(block, isRemovePositionIds);
					}

					if (remainingSize == 0) {
						clearPointToFillablePositionMap();
						return true;
					}
				}
			} while (hasNewSingularBlock || hasNewSingularPosition);

			clearPointToFillablePositionMap();
			return false;
		}

		public boolean get(int rowIndex, int columnIndex) {
			return board[width * rowIndex + columnIndex];
		}

		public void set(int rowIndex, int columnIndex, boolean value) {
			board[width * rowIndex + columnIndex] = value;
			points[width * rowIndex + columnIndex].isFilled = value;
		}

		public void add(BlockPosition blockPosition) {
			Position position = blockPosition.getPosition();
			Block block = blockPosition.getBlock();
			for (int rowIndex = 0; rowIndex < block.getHeight(); rowIndex++) {
				for (int colIndex = 0; colIndex < block.getWidth(); colIndex++) {
					if (block.get(rowIndex, colIndex)) {
						this.set(rowIndex + position.y, colIndex + position.x, true);
					}
				}
			}
		}

		public void remove(BlockPosition blockPosition) {
			Position position = blockPosition.getPosition();
			Block block = blockPosition.getBlock();
			for (int rowIndex = 0; rowIndex < block.getHeight(); rowIndex++) {
				for (int colIndex = 0; colIndex < block.getWidth(); colIndex++) {
					if (block.get(rowIndex, colIndex)) {
						this.set(rowIndex + position.y, colIndex + position.x, false);
					}
				}
			}
		}
	}
}
