package com.samabcde.puzzlesolver.component;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlockPuzzleUnitTest {

    @Test
    public void testBlockPuzzle() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(4, 3, new String[]{"001,111", "1,1111", "111"});
        assertTrue(blockPuzzle.getBlocks().size() == 3);

        BlockPosition p1 = blockPuzzle.getBlockIdToBlockPositionsMap().get(0).get(0);
        BlockPosition p2 = blockPuzzle.getBlockIdToBlockPositionsMap().get(1).get(0);
        BlockPosition p3 = blockPuzzle.getBlockIdToBlockPositionsMap().get(2).get(0);
        assertTrue(p1.isIntersect(p2));
        assertTrue(p1.isIntersect(p3));

    }

    @Test
    public void testBlockPuzzle2() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(2, 2, new String[]{"1,1", "1,1"});
        assertTrue(blockPuzzle.getBlocks().size() == 2);

        BlockPosition p1 = blockPuzzle.getBlockIdToBlockPositionsMap().get(0).get(0);
        BlockPosition p2 = blockPuzzle.getBlockIdToBlockPositionsMap().get(0).get(1);

    }

    @Test
    public void testBlockPuzzle3() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(12, 12,
                new String[]{"1", "11", "1,1", "111", "1,1,1", "01,11", "11,1", "11,01", "11,11", "1111", "1,1,1,1",
                        "111,1", "111,01", "111,001", "1,111", "01,111", "001,111", "11,011", "011,11", "1,11,1",
                        "01,11,01", "01,11,1", "1,11,01", "11111", "1,1,1,1,1", "1111,1", "1111,01", "1111,001",
                        "1111,0001", "111,101", "111,110", "111,011", "101,111", "110,111", "011,111", "111,1"});
        long startTime = new Date().getTime();
        for (int k = 0; k < 5; k++) {
            for (int i = 0; i < blockPuzzle.getPositionCount(); i++) {
                for (int j = i + 1; j < blockPuzzle.getPositionCount(); j++) {
                    blockPuzzle.getBlockPositionById(i).isPositionIntersect(j);
                    blockPuzzle.getBlockPositionById(j).isPositionIntersect(i);
                }
            }
        }
        long endTime = new Date().getTime();

        long endTime2 = new Date().getTime();
        System.out.println("alg 1 time :" + (endTime - startTime));
        System.out.println("alg 2 time :" + (endTime2 - endTime));
    }

    @Test
    public void testBlockPuzzle4() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(12, 12,
                new String[]{"1", "11", "1,1", "111", "1,1,1", "01,11", "11,1", "11,01", "11,11", "1111", "1,1,1,1",
                        "111,1", "111,01", "111,001", "1,111", "01,111", "001,111", "11,011", "011,11", "1,11,1",
                        "01,11,01", "01,11,1", "1,11,01", "11111", "1,1,1,1,1", "1111,1", "1111,01", "1111,001",
                        "1111,0001", "111,101", "111,110", "111,011", "101,111", "110,111", "011,111", "111,1"});
        int[] intersectionCountOfBlockPosition = new int[blockPuzzle.getPositionCount()];
        int[] intersectionCountOfBlockPosition2 = new int[blockPuzzle.getPositionCount()];
        long startTime = new Date().getTime();

        for (int k = 0; k < 200; k++) {
            for (int i = 0; i < blockPuzzle.getPositionCount(); i++) {
                boolean[] isPositionIdIntersect = blockPuzzle.getBlockPositionById(i).getIsPositionIdIntersect();
                for (int j = 0; j < isPositionIdIntersect.length; j++) {
                    if (!isPositionIdIntersect[j]) {
                        continue;
                    }
                    intersectionCountOfBlockPosition2[j]++;
                }
            }
        }
        long endTime = new Date().getTime();
        for (int k = 0; k < 200; k++) {
            for (int i = 0; i < blockPuzzle.getPositionCount(); i++) {
                List<Integer> intersectPositionIds = blockPuzzle.getBlockPositionById(i).getIntersectPositionIds();
                for (Integer intersectPositionId : intersectPositionIds) {
                    intersectionCountOfBlockPosition[intersectPositionId]++;
                }
            }
        }
        long endTime2 = new Date().getTime();
        System.out.println("alg 1 time :" + (endTime - startTime));
        System.out.println("alg 2 time :" + (endTime2 - endTime));
    }
}
