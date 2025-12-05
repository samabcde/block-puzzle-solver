package com.samabcde.puzzlesolver.solve.priority;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import com.samabcde.puzzlesolver.component.Block;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BlockComparatorTest {
    @Mock
    private Block left;
    @Mock
    private Block right;

    BlockComparator blockComparator = new BlockComparator();

    @Order(1)
    @Test
    public void firstCompareByAverageIntersectCountDesc() {
        when(left.getAverageIntersectCount()).thenReturn(1);
        when(right.getAverageIntersectCount()).thenReturn(2);

        int actual = blockComparator.compare(left, right);

        assertTrue(actual > 0);
    }

    @Order(2)
    @Test
    public void thenCompareByWeightDesc() {
        when(left.getAverageIntersectCount()).thenReturn(1);
        when(right.getAverageIntersectCount()).thenReturn(1);
        when(left.getWeight()).thenReturn(1);
        when(right.getWeight()).thenReturn(2);

        int actual = blockComparator.compare(left, right);

        assertTrue(actual > 0);
    }

    @Order(3)
    @Test
    public void thenCompareBySizeDesc() {
        when(left.getAverageIntersectCount()).thenReturn(1);
        when(right.getAverageIntersectCount()).thenReturn(1);
        when(left.getWeight()).thenReturn(1);
        when(right.getWeight()).thenReturn(1);
        when(left.getSize()).thenReturn(1);
        when(right.getSize()).thenReturn(2);

        int actual = blockComparator.compare(left, right);

        assertTrue(actual > 0);
    }

    @Order(4)
    @Test
    public void lastCompareByIdAsc() {
        when(left.getAverageIntersectCount()).thenReturn(1);
        when(right.getAverageIntersectCount()).thenReturn(1);
        when(left.getWeight()).thenReturn(1);
        when(right.getWeight()).thenReturn(1);
        when(left.getSize()).thenReturn(1);
        when(right.getSize()).thenReturn(1);
        when(left.getId()).thenReturn(1);
        when(right.getId()).thenReturn(2);

        int actual = blockComparator.compare(left, right);

        assertTrue(actual < 0);
    }
}