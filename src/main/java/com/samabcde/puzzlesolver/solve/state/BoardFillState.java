package com.samabcde.puzzlesolver.solve.state;

import com.samabcde.puzzlesolver.component.BlockPosition;
import com.samabcde.puzzlesolver.component.BlockPuzzle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BoardFillState {
    private static final Logger logger = LoggerFactory.getLogger(BoardFillState.class);
    private final LinkedList<PointFillState> emptyPoints;
    private final List<PointFillState> pointFillStatesOrderByPosition;
    private final BlockPuzzle blockPuzzle;
    private int canNotFillPointCount = 0;

    public BoardFillState(BlockPuzzle blockPuzzle) {
        this.blockPuzzle = blockPuzzle;
        int pointCount = blockPuzzle.getSize();
        pointFillStatesOrderByPosition = new ArrayList<>(pointCount);
        for (int i = 0; i < pointCount; i++) {
            pointFillStatesOrderByPosition.add(new PointFillState(blockPuzzle, i));
        }
        this.emptyPoints = new LinkedList<>(pointFillStatesOrderByPosition);
    }

    private BoardFillState(BoardFillState boardFillState) {
        this.blockPuzzle = boardFillState.blockPuzzle;
        this.pointFillStatesOrderByPosition = boardFillState.pointFillStatesOrderByPosition.stream().map(PointFillState::copy).toList();
        this.emptyPoints = new LinkedList<>(boardFillState.emptyPoints);
        this.emptyPoints.replaceAll(pointFillState -> this.pointFillStatesOrderByPosition.get(pointFillState.getPosition()));
        this.canNotFillPointCount = boardFillState.canNotFillPointCount;
    }

    public BoardFillState copy() {
        return new BoardFillState(this);
    }

    public boolean existCannotFillPoint() {
        return canNotFillPointCount > 0;
    }

    // TODO cache this instead of loop through
    public long countPointCanOnlyFillByWeight1Block() {
        return getEmptyPoints().stream().filter(PointFillState::canOnlyFillByWeight1Block).count();
    }

    private List<PointFillState> getEmptyPoints() {
        return this.emptyPoints;
    }

    public List<PointFillState> getOneBlockCanFillEmptyPoints() {
        return emptyPoints.stream()
                .filter(PointFillState::canFillByOnlyOneBlock).toList();
    }

    public void placeBlockPosition(BlockPosition blockPosition) {
        for (int canFillPoint : blockPosition.getCanFillPoints()) {
            PointFillState pointFillState = pointFillStatesOrderByPosition.get(canFillPoint);
            pointFillState.setIsFilled(true);
            if (!pointFillState.canFill()) {
                canNotFillPointCount--;
            }
            emptyPoints.removeFirstOccurrence(pointFillStatesOrderByPosition.get(canFillPoint));
        }
    }

    public void removeCanFillBlockPosition(BlockPosition blockPosition) {
        for (int canFillPoint : blockPosition.getCanFillPoints()) {
            boolean isNewCanNotFill = pointFillStatesOrderByPosition.get(canFillPoint).removeCanFillBlockPosition(blockPosition);
            if (isNewCanNotFill) {
                canNotFillPointCount++;
            }
        }
    }

    public void takeBlockPosition(BlockPosition blockPosition) {
        for (int canFillPoint : blockPosition.getCanFillPoints()) {
            PointFillState pointFillState = pointFillStatesOrderByPosition.get(canFillPoint);
            pointFillState.setIsFilled(false);
            if (!pointFillState.canFill()) {
                canNotFillPointCount++;
            }
            emptyPoints.addFirst(pointFillStatesOrderByPosition.get(canFillPoint));
        }
    }

    public void addCanFillBlockPosition(BlockPosition blockPosition) {
        for (int canFillPoint : blockPosition.getCanFillPoints()) {
            boolean isNewCallFill = pointFillStatesOrderByPosition.get(canFillPoint).addCanFillBlockPosition(blockPosition);
            if (isNewCallFill) {
                canNotFillPointCount--;
            }
        }
    }
}
