package com.samabcde.puzzlesolver.component;

import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Block {
    public int getId() {
        return id;
    }

    public final int id;
    // store the 2D block in 1D array with true as present
    private boolean[] value;

    private Shape shape;
    private int height;
    private int width;
    int weight;
    int totalIntersectCount = 0;
    private final Set<Integer> coverableBlockIds = new HashSet<>();
    int positionIdFrom;
    int positionIdTo;
    private String valueView;
    int priority;
    private List<BlockPosition> blockPositions;

    Block(String value, int blockId) {
        this.id = blockId;
        String[] rows = value.split(",");
        initializeSize(rows);
        initializeValueAndWeight(rows);
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
        return (this.height >= block.height && this.width >= block.width && this.weight >= block.weight)
                // TODO below can reduce iterate count, but also reduce isRemainingBlockPositionsSolvable speed
//                || (this.height >= block.height && this.width > block.width && this.weight >= block.weight)
//                || (this.height > block.height && this.width >= block.width && this.weight >= block.weight)
                ;
    }

    public int getAverageIntersectCount() {
        return totalIntersectCount / this.blockPositions.size();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getSize() {
        return getWidth() + getHeight();
    }

    public int getWeight() {
        return weight;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    void setBlockPositions(List<BlockPosition> blockPositions) {
        this.blockPositions = blockPositions;
        this.totalIntersectCount = blockPositions.stream().map(BlockPosition::getIntersectCount).reduce(Integer::sum).orElse(0);
    }

    public List<BlockPosition> getBlockPositions() {
        return this.blockPositions;
    }

    @Override
    public String toString() {
        if (valueView != null) {
            return valueView;
        }
        String lineSeparator = System.lineSeparator();
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
        BitSet bitSet = new BitSet(width * height);
        for (int i = 0; i < value.length; i++) {
            bitSet.set(i, value[i]);
        }
        this.shape = new Shape(width, height, bitSet);
    }

    public Shape getShape() {
        return shape;
    }

    private void initializeSize(String[] rows) {
        // TODO incorrect, e.g. 1000,0100,0000
        int maxColCount = 0;
        for (String row : rows) {
            maxColCount = Math.max(row.length(), maxColCount);
        }
        this.height = rows.length;
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