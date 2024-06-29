package com.samabcde.puzzlesolver.solve.state;

import com.samabcde.puzzlesolver.component.Block;
import com.samabcde.puzzlesolver.component.BlockPosition;
import com.samabcde.puzzlesolver.component.BlockPuzzle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class BoardFillState {
    private static final Logger logger = LoggerFactory.getLogger(BoardFillState.class);
    private final LinkedList<PointFillState> emptyPoints;

    private final List<PointFillState> pointFillStatesOrderByPosition;
    private final BlockPuzzle blockPuzzle;

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
    }

    public BoardFillState copy() {
        return new BoardFillState(this);
    }

    public boolean existCannotFillPoint() {
        List<PointFillState> emptyPoints = getEmptyPoints();
        return emptyPoints.stream().anyMatch(emptyPoint -> !emptyPoint.canFill());
    }

    public static boolean isEnableOnly = false;

    public Optional<Block> getOnlyBlock() {
        if (isEnableOnly) {
            for (PointFillState pointFillState : getEmptyPoints()) {
                Optional<Block> onlyBlock = pointFillState.onlyBlock();
                if (onlyBlock.isPresent()) {
                    return onlyBlock;
                }
            }
        }
        return Optional.empty();
    }

    public Optional<PointFillState> getPointWithOnly1Block() {
        if (isEnableOnly) {
            return getEmptyPoints().stream().filter(pointFillState -> pointFillState.onlyBlock().isPresent()).findFirst();
        }
        return Optional.empty();
    }

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
            pointFillStatesOrderByPosition.get(canFillPoint).setIsFilled(true);
            emptyPoints.remove(pointFillStatesOrderByPosition.get(canFillPoint));
        }
    }

    public void removeCanFillBlockPosition(BlockPosition blockPosition) {
        for (int canFillPoint : blockPosition.getCanFillPoints()) {
            pointFillStatesOrderByPosition.get(canFillPoint).removeCanFillBlockPosition(blockPosition);
        }
    }


    public void takeBlockPosition(BlockPosition blockPosition) {
        for (int canFillPoint : blockPosition.getCanFillPoints()) {
            pointFillStatesOrderByPosition.get(canFillPoint).setIsFilled(false);
            emptyPoints.addFirst(pointFillStatesOrderByPosition.get(canFillPoint));
        }
    }

    public void addCanFillBlockPosition(BlockPosition blockPosition) {
        for (int canFillPoint : blockPosition.getCanFillPoints()) {
            pointFillStatesOrderByPosition.get(canFillPoint).addCanFillBlockPosition(blockPosition);
        }
    }
}
