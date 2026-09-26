package com.samabcde.puzzlesolver.solve;

import static org.fusesource.jansi.Ansi.ansi;

import com.samabcde.puzzlesolver.component.Block;
import com.samabcde.puzzlesolver.component.BlockPosition;
import com.samabcde.puzzlesolver.component.BlockPuzzle;
import com.samabcde.puzzlesolver.component.Position;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Solution implements Iterable<BlockPosition> {
    private static final int MAX_UNPLACED_BLOCK_ROW_WIDTH = 120;
    private static final int UNPLACED_BLOCK_SPACING = 2;
    private static final String display =
            "0123456789"
                    + "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    + "`~!@#$%^&*()-_=+[]{}\\|:;'\"<>,./?"
                    + "€‚ƒ„…†‡ˆ‰Š‹ŒŽ"
                    + "‘’“”•–—˜"
                    + "™š›œžŸ¡¢£¤¥¦§¨©ª«¬®¯°±²³´µ¶·¸¹º»¼½¾¿"
                    + "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ";
    private static final Logger logger = LoggerFactory.getLogger(Solution.class);

    private record RenderedBlock(String[] rows, int width) {}

    private final BlockPuzzle blockPuzzle;
    private final Deque<BlockPosition> positionSolutions = new LinkedList<>();
    private final BitSet addedBlocks;
    private long iterateCount;
    private final Color[] colors;

    public Solution(BlockPuzzle blockPuzzle) {
        this.blockPuzzle = blockPuzzle;
        this.addedBlocks = new BitSet(blockPuzzle.blockCount);
        this.colors = generateColorsRGB(this.blockPuzzle.blockCount);
    }

    private char[] getDisplay(int count) {
        if (count > display.length()) {
            throw new IllegalArgumentException(
                    "Not enough display, count:%d, available:%d"
                            .formatted(count, display.length()));
        }
        return display.toCharArray();
    }

    void print() {
        String[][] solutionView =
                new String[this.blockPuzzle.getPuzzleHeight()][this.blockPuzzle.getPuzzleWidth()];
        for (BlockPosition blockPosition : positionSolutions) {
            Position position = blockPosition.getPosition();
            Block block = blockPosition.getBlock();
            for (int rowIndex = 0; rowIndex < block.getHeight(); rowIndex++) {
                for (int colIndex = 0; colIndex < block.getWidth(); colIndex++) {
                    if (block.get(rowIndex, colIndex)) {
                        solutionView[rowIndex + position.y()][colIndex + position.x()] =
                                styledPoint(block.getPriority());
                    }
                }
            }
        }

        List<Block> unplacedBlocks =
                this.blockPuzzle.getBlocks().stream()
                        .filter(block -> !containsBlock(block))
                        .toList();
        List<String> unplacedBlockRows = getUnplacedBlockRows(unplacedBlocks);
        int unplacedViewHeight = unplacedBlockRows.size();
        int boardHeight = this.blockPuzzle.getPuzzleHeight();
        int boardWidth = this.blockPuzzle.getPuzzleWidth();

        logger.info("+" + "-".repeat(boardWidth) + "+");
        for (int rowIndex = 0; rowIndex < Math.max(boardHeight, unplacedViewHeight); rowIndex++) {
            if (rowIndex == boardHeight) {
                logger.info("+" + "-".repeat(boardWidth) + "+");
            }
            StringBuilder stringBuilder = new StringBuilder();
            if (rowIndex < boardHeight) {
                stringBuilder.append('|');
                for (String c : solutionView[rowIndex]) {
                    stringBuilder.append(Objects.requireNonNullElse(c, " "));
                }
                stringBuilder.append('|');
            } else {
                stringBuilder.repeat(" ", boardWidth + 2);
            }
            if (rowIndex < unplacedViewHeight) {
                stringBuilder.append("  ").append(unplacedBlockRows.get(rowIndex));
            }
            logger.info(stringBuilder.toString());
        }
        if (unplacedViewHeight <= boardHeight) {
            logger.info("+" + "-".repeat(boardWidth) + "+");
        }
    }

    private RenderedBlock getBlockView(Block block) {
        String[] blockView = new String[block.getHeight()];
        for (int rowIndex = 0; rowIndex < block.getHeight(); rowIndex++) {
            StringBuilder row = new StringBuilder(block.getWidth());
            for (int colIndex = 0; colIndex < block.getWidth(); colIndex++) {
                row.append(block.get(rowIndex, colIndex) ? styledPoint(block.getPriority()) : " ");
            }
            blockView[rowIndex] = row.toString();
        }
        return new RenderedBlock(blockView, block.getWidth());
    }

    private List<String> getUnplacedBlockRows(List<Block> unplacedBlocks) {
        List<String> rows = new ArrayList<>();
        List<RenderedBlock> blockViews = new ArrayList<>();
        int rowWidth = 0;
        int rowHeight = 0;

        for (Block block : unplacedBlocks) {
            if (!blockViews.isEmpty()
                    && rowWidth + UNPLACED_BLOCK_SPACING + block.getWidth()
                            > MAX_UNPLACED_BLOCK_ROW_WIDTH) {
                appendUnplacedBlockRow(rows, blockViews, rowHeight);
                blockViews.clear();
                rowWidth = 0;
                rowHeight = 0;
            }

            blockViews.add(getBlockView(block));
            rowWidth += (blockViews.size() == 1 ? 0 : UNPLACED_BLOCK_SPACING) + block.getWidth();
            rowHeight = Math.max(rowHeight, block.getHeight());
        }
        if (!blockViews.isEmpty()) {
            appendUnplacedBlockRow(rows, blockViews, rowHeight);
        }
        return rows;
    }

    private void appendUnplacedBlockRow(
            List<String> rows, List<RenderedBlock> blockViews, int rowHeight) {
        for (int rowIndex = 0; rowIndex < rowHeight; rowIndex++) {
            StringBuilder row = new StringBuilder();
            for (int blockIndex = 0; blockIndex < blockViews.size(); blockIndex++) {
                if (blockIndex > 0) {
                    row.repeat(" ", UNPLACED_BLOCK_SPACING);
                }
                RenderedBlock blockView = blockViews.get(blockIndex);
                if (rowIndex < blockView.rows().length) {
                    row.append(blockView.rows()[rowIndex]);
                } else {
                    row.repeat(" ", blockView.width());
                }
            }
            rows.add(row.toString());
        }
    }

    private static Color[] generateColorsRGB(int n) {
        Color[] colors = new Color[n];
        int cubeRoot = (int) Math.ceil(Math.pow(n, 1.0 / 3.0));

        // Calculate step size for each component
        int step = cubeRoot > 1 ? 255 / (cubeRoot - 1) : 255;

        int count = 0;
        for (int b = 0; b <= 255 && count < n; b += step) {
            for (int g = 0; g <= 255 && count < n; g += step) {
                for (int r = 0; r <= 255 && count < n; r += step) {
                    colors[count] = new Color(r, g, b);
                    count++;
                }
            }
        }

        return colors;
    }

    private String styledPoint(int priority) {
        char[] visible = getDisplay(this.blockPuzzle.blockCount);
        Color color = this.colors[priority];
        return ansi().bgRgb(color.getRed(), color.getGreen(), color.getBlue())
                .a(visible[priority])
                .reset()
                .toString();
    }

    public BlockPosition poll() {
        if (positionSolutions.isEmpty()) {
            throw new NoSuchElementException();
        }
        BlockPosition blockPosition = positionSolutions.poll();
        addedBlocks.set(blockPosition.getBlock().id, false);
        return blockPosition;
    }

    public void push(BlockPosition blockPosition) {
        positionSolutions.push(blockPosition);
        addedBlocks.set(blockPosition.getBlock().id, true);
    }

    public int size() {
        return positionSolutions.size();
    }

    public boolean isEmpty() {
        return positionSolutions.isEmpty();
    }

    public boolean containsBlock(Block block) {
        return addedBlocks.get(block.id);
    }

    @Override
    public Iterator<BlockPosition> iterator() {
        return positionSolutions.iterator();
    }

    @Override
    public void forEach(Consumer<? super BlockPosition> action) {
        positionSolutions.forEach(action);
    }

    @Override
    public Spliterator<BlockPosition> spliterator() {
        return positionSolutions.spliterator();
    }

    public long getIterateCount() {
        return iterateCount;
    }

    public void setIterateCount(long iterateCount) {
        this.iterateCount = iterateCount;
    }
}
