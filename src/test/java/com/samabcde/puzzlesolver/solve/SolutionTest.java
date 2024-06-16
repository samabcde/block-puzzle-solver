package com.samabcde.puzzlesolver.solve;

import com.samabcde.puzzlesolver.component.Block;
import com.samabcde.puzzlesolver.component.BlockPosition;
import com.samabcde.puzzlesolver.component.BlockPuzzle;
import com.samabcde.puzzlesolver.component.Dimension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {
    @Nested
    public class Given_EmptySolution {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(1, 1), new String[]{"1"});
        Solution solution = new Solution(blockPuzzle);

        @Test
        public void itSizeIs0() {
            assertEquals(0, solution.size());
        }

        @Test
        public void itIsEmpty() {
            assertTrue(solution.isEmpty());
        }

        @Test
        public void itDoesNotContainsBlock() {
            blockPuzzle.getBlocks().forEach(block ->
                    assertFalse(solution.containsBlock(block)));
        }

        @Test
        public void pollThrowException() {
            assertThrows(NoSuchElementException.class, () -> solution.poll());
        }
    }

    @Nested
    public class AddOnePosition {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(1, 1), new String[]{"1"});
        Solution solution = new Solution(blockPuzzle);
        Block block = blockPuzzle.getBlocks().getFirst();
        BlockPosition blockPosition = block.getBlockPositions().getFirst();

        @BeforeEach
        public void setup() {
            solution.push(blockPuzzle.getBlocks().getFirst().getBlockPositions().getFirst());
        }

        @Test
        public void itSizeIs1() {
            assertEquals(1, solution.size());
        }

        @Test
        public void itIsNotEmpty() {
            assertFalse(solution.isEmpty());
        }

        @Test
        public void itContainsBlock() {
            assertTrue(solution.containsBlock(block));
        }

        @Test
        public void pollReturnPushedPosition() {
            assertEquals(blockPosition, solution.poll());
        }
    }
}