package com.samabcde.puzzlesolver.component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.samabcde.puzzlesolver.component.PointFillState.PointFillStateComparator;

public class BoardFillState {
	private final Logger logger = LoggerFactory.getLogger(BoardFillState.class);
//	private final List<PointFillState> pointFillStatesOrderByFillability;
	private final List<PointFillState> filledPoints;
	private final List<PointFillState> emptyPoints;

	private final List<PointFillState> pointFillStatesOrderByPosition;
	private final PointFillStateComparator pointFillStateComparator;
	// private PointFillState[] pointFillStates;
	private int noOfFillPoint = 0;
	private final BlockPuzzle blockPuzzle;
	private boolean isFillabilityChanged = false;

	private BoardFillState(BoardFillState boardFillState) {
		this.blockPuzzle = boardFillState.blockPuzzle;

		this.pointFillStateComparator = new PointFillStateComparator();
		this.noOfFillPoint = boardFillState.noOfFillPoint;
		this.pointFillStatesOrderByPosition = new ArrayList<>();
		for (PointFillState pointFillState : boardFillState.pointFillStatesOrderByPosition) {
			this.pointFillStatesOrderByPosition.add(pointFillState.clone());
		}

//		this.pointFillStatesOrderByFillability = new ArrayList<>(boardFillState.pointFillStatesOrderByFillability);
//		for (int i = 0; i < this.pointFillStatesOrderByFillability.size(); i++) {
//			this.pointFillStatesOrderByFillability.set(i, this.pointFillStatesOrderByPosition
//					.get(this.pointFillStatesOrderByFillability.get(i).getPosition()));
//		}
		this.isFillabilityChanged = boardFillState.isFillabilityChanged;
		this.filledPoints = boardFillState.filledPoints;
		for (int i = 0; i < this.filledPoints.size(); i++) {
			this.filledPoints.set(i, this.pointFillStatesOrderByPosition.get(this.filledPoints.get(i).getPosition()));
		}
		this.emptyPoints = boardFillState.emptyPoints;
		for (int i = 0; i < this.emptyPoints.size(); i++) {
			this.emptyPoints.set(i, this.pointFillStatesOrderByPosition.get(this.emptyPoints.get(i).getPosition()));
		}
	}

	public BoardFillState clone() {
		return new BoardFillState(this);
	}

	public BoardFillState(BlockPuzzle blockPuzzle) {
		this.blockPuzzle = blockPuzzle;
		pointFillStateComparator = new PointFillStateComparator();
		int pointCount = blockPuzzle.getSize();
		pointFillStatesOrderByPosition = new ArrayList<>(pointCount);
		for (int i = 0; i < pointCount; i++) {
			pointFillStatesOrderByPosition.add(new PointFillState(blockPuzzle, i));
		}
		this.filledPoints = new LinkedList<PointFillState>();
		this.emptyPoints = new LinkedList<PointFillState>(pointFillStatesOrderByPosition);
		// pointFillStatesOrderByFillability = new
		// ArrayList<>(pointFillStatesOrderByPosition);
		// sortPointFillStates();
	}

	private static long count = 0;

	public boolean existNotFillablePoint() {
		List<PointFillState> emptyPoints = getEmptyPoints();
//		if (this.isFillabilityChanged) {
//
//			Collections.sort(emptyPoints, this.pointFillStateComparator);
//			this.isFillabilityChanged = false;
//		}
//		if (!emptyPoints.get(0).isFillable()) {
//			return true;
//		}
//
//		Set<Integer> blockIds = new HashSet<Integer>(emptyPoints.get(0).getFillableBlockIds());
//		int noOfPointToFill = 1;
//		int totalFillableBlockWeight = emptyPoints.get(0).getFillableBlockWeight();
//		if (blockIds.size() == 1) {
//
//		}
//		count++;
//		if (count % 2000 == 0) {
//			logger.info(" ");
//			for (PointFillState emptyPoint : emptyPoints) {
//				logger.info(emptyPoint.toString());
//			}
//		}
		//
		for (int i = 0; i < emptyPoints.size(); i++) {
			if(!emptyPoints.get(i).isFillable()) {
				return true;
			}
//			noOfPointToFill++;
//			List<Integer> fillableBlockIds = emptyPoints.get(i).getFillableBlockIds();
//
//			for (Integer fillableBlockId : fillableBlockIds) {
//				if (blockIds.add(fillableBlockId)) {
//					totalFillableBlockWeight += blockPuzzle.getBlockById(fillableBlockId).weight;
//					if (noOfPointToFill < totalFillableBlockWeight) {
//						return false;
//					}
//				}
//			}
//			if (noOfPointToFill > totalFillableBlockWeight) {
//				return true;
//			}
		}
		return false;

	}

	private List<PointFillState> getFilledPoints() {
//		if (this.isFillabilityChanged) {
//			this.sortPointFillStates();
//		}
//		return pointFillStatesOrderByFillability.subList(0, noOfFillPoint);
		return this.filledPoints;
	}

	List<PointFillState> getEmptyPoints() {
//		if (this.isFillabilityChanged) {
//			this.sortPointFillStates();
//		}
//		return pointFillStatesOrderByFillability.subList(noOfFillPoint, pointFillStatesOrderByFillability.size());
		return this.emptyPoints;
	}

	public void addBlockPosition(BlockPosition addBlockPosition) {
		for (int fillablePoint : addBlockPosition.getFillablePoints()) {
			pointFillStatesOrderByPosition.get(fillablePoint).setIsFilled(true);
			emptyPoints.remove(pointFillStatesOrderByPosition.get(fillablePoint));
			filledPoints.add(pointFillStatesOrderByPosition.get(fillablePoint));
			noOfFillPoint++;
			this.isFillabilityChanged = true;
			// moveForward(pointFillStatesOrderByPosition.get(fillablePoint));
		}
		// sortPointFillStates();
	}

	public void removeFillableBlockPosition(BlockPosition blockPosition) {
		for (int fillablePoint : blockPosition.getFillablePoints()) {
			pointFillStatesOrderByPosition.get(fillablePoint).removeFillableBlockPosition(blockPosition);
			this.isFillabilityChanged = true;
			// moveForward(pointFillStatesOrderByPosition.get(fillablePoint));
		}
	}


	public void removeBlockPosition(BlockPosition removeBlockPosition) {
		for (int fillablePoint : removeBlockPosition.getFillablePoints()) {
			pointFillStatesOrderByPosition.get(fillablePoint).setIsFilled(false);
			noOfFillPoint--;
			emptyPoints.add(pointFillStatesOrderByPosition.get(fillablePoint));
			filledPoints.remove(pointFillStatesOrderByPosition.get(fillablePoint));
			this.isFillabilityChanged = true;
			// moveBackward(pointFillStatesOrderByPosition.get(fillablePoint));
		}
		// sortPointFillStates();
	}

//	private void sortPointFillStates() {
//		pointFillStatesOrderByFillability.sort(pointFillStateComparator);
//		this.isFillabilityChanged = false;
//	}

	public void addFillableBlockPosition(BlockPosition blockPosition) {
		for (int fillablePoint : blockPosition.getFillablePoints()) {
			pointFillStatesOrderByPosition.get(fillablePoint).addFillableBlockPosition(blockPosition);
			this.isFillabilityChanged = true;
			// moveBackward(pointFillStatesOrderByPosition.get(fillablePoint));
		}
	}
}
