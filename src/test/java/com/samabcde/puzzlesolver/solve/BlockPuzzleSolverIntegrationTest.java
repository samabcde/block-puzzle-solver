package com.samabcde.puzzlesolver.solve;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.samabcde.puzzlesolver.component.BlockPuzzle;
import com.samabcde.puzzlesolver.component.Dimension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class BlockPuzzleSolverIntegrationTest {
    @Test
    public void testSolve() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(4, 4), new String[]{"1111", "111", "001,111", "1,1111"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve2() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(5, 5), new String[]{"1111", "111", "001,111", "1,1111", "111", "01,01,01,11,01"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve2a() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(5, 5), new String[]{"11", "1,1", "111,111", "11", "11", "1,1111", "01,01,11,01,01"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve3a() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(6, 7), new String[]{"1,1", "1,1,1", "11,11", "01,11,1", "001,111", "11,11,01",
                "1,11,01,01", "101,111", "011,01,11", "1,1,1,1,1"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve3() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(8, 8), new String[]{"11", "111,11", "001,111", "1,1111", "111", "01,01,01,11,01",
                "111,1,1,1,1,1,1,1", "11,11,11,11,1,1,1", "111111,1,111111", "11111"

        });
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve4() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(10, 10), new String[]{"11", "111,11", "001,111", "1,1111", "111", "01,01,01,11,01", "111,1,1,1,1,1,1,1",
                "11,11,11,11,1,1,1", "111111,1,111111", "11111", "0111111,11", "111111", "01,11", "1,11,11",
                "11,11,01", "01,011,111,111"

        });
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve4A() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(10, 10), new String[]{"1", "1", "111,11", "001,111", "1,1111", "111", "01,01,01,11,01", "111,1,1,1,1,1,1,1",
                "11,11,11,11,1,1,1", "111111,1,111111", "11111", "0111111,11", "111111", "01,11", "1,11,11",
                "11,11,01", "01,011,111,111"

        });
        assertPuzzleSolvable(blockPuzzle);
//        2022-10-10 23:38:46 INFO  BlockPuzzleSolver - Solved
//        2022-10-10 23:38:46 INFO  BlockPuzzleSolver - iterate Count: 41821
//        2022-10-10 23:38:46 INFO  BlockPuzzleSolver - Solution:
//        2022-10-10 23:38:46 INFO  Solution - 111333333c
//        2022-10-10 23:38:46 INFO  Solution - 1933eeeccc
//        2022-10-10 23:38:46 INFO  Solution - 1996666665
//        2022-10-10 23:38:46 INFO  Solution - 19922bbbd5
//        2022-10-10 23:38:46 INFO  Solution - 1aa22bbdd5
//        2022-10-10 23:38:46 INFO  Solution - 1aa227gf55
//        2022-10-10 23:38:46 INFO  Solution - 14a2277775
//        2022-10-10 23:38:46 INFO  Solution - 1442000000
//        2022-10-10 23:38:46 INFO  Solution - 4442088888
//        2022-10-10 23:38:46 INFO  Solution - 4442000000
//        2022-10-10 23:38:46 INFO  PerformanceRecorder - Step Complete initialize block puzzle time: 38ms
//        2022-10-10 23:38:46 INFO  PerformanceRecorder - Step Solve complete time: 970ms
//        2022-10-10 23:38:46 INFO  PerformanceRecorder - Step Print solution time: 7ms
    }

    @Test
    public void testSolve5() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(12, 12), new String[]{"11", "111,11", "001,111", "1,1111", "111", "01,01,01,11,01", "111,1,1,1,1,1,1,1",
                "11,11,11,11,1,1,1", "111111,1,111111", "11111", "0111111,11", "111111", "01,11", "1,11,11",
                "11,11,01", "01,011,111,111", "0000001,0000001,0000001,0000001,0000011,1111111,0000011",
                "111,011,011,011,01,01,01,01", "0011111,1111", "1111111"

        });
        assertPuzzleSolvable(blockPuzzle);
//        BlockPuzzleSolver - Solved
//        BlockPuzzleSolver - iterate Count: 21566
//        BlockPuzzleSolver - Solution:
//        Solution - 999999955555
//        Solution - ccccc5555111
//        Solution - 222666666g11
//        Solution - 2d66iiiggg11
//        Solution - 2ddaaaaaa811
//        Solution - 2dd44fffh810
//        Solution - 2ee44ffhh810
//        Solution - 2ee44bjj8810
//        Solution - 27e44bbbb810
//        Solution - 277433333300
//        Solution - 777430000000
//        Solution - 777433333300
//        PerformanceRecorder - Step Complete initialize block puzzle time: 77ms
//        PerformanceRecorder - Step Solve complete time: 2203ms
//        PerformanceRecorder - Step Print solution time: 2ms
    }

    @Test
    public void testSolve6() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(13, 13), new String[]{"11", "111,11", "001,111", "1,1111", "111", "01,01,01,11,01",
                "0111,01,01,01,01,01,11,11,1,1,1", "11,11,11,11,1,1,1", "111111,1,111111,00111111",
                "1,111111,1,1,1,1,1,1", "0111111,11", "111111", "01,11", "1,11,11", "11,11,01",
                "01,011,111,111,111111", "0000001,0000001,0000001,0000001,0000011,1111111,0000011",
                "111,011,011,011,01,01,01,01", "0011111,1111", "1111111"

        });
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve9() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(8, 8), new String[]{"1", "11", "01,11", "11,1", "11,01", "1111", "1,1,1,1", "11,11", "111,1", "111,01",
                "111,001", "1,111", "01,111", "001,111", "11,011", "011,11", "1,11,1", "01,11,01"

        });
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve10() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(10, 10), new String[]{"1", "11", "01,11", "11,1", "11,01", "1111", "1,1,1,1", "1,1", "111,1", "111,01",
                "111,001", "1,111", "01,111", "001,111", "11,011", "011,11", "1,11,1", "01,11,01", "01,11,1",
                "1,11,01", "11111", "1,1,1,1,1", "1111,1", "1111,01", "1111,001", "111,101"
        });
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve10a() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(10, 10), new String[]{"1", "1", "1", "01,11", "11,1", "11,01", "1111", "1,1,1,1", "1,1", "111,1", "111,01",
                "111,001", "1,111", "01,111", "001,111", "11,011", "011,11", "1,11,1", "01,11,01", "01,11,1",
                "1,11,01", "11111", "1,1,1,1,1", "1111,1", "1111,01", "1111,001", "111,101"
        });
        assertPuzzleSolvable(blockPuzzle);
//        2023-05-21 17:32:52 INFO  BlockPuzzleSolver - Solved
//        2023-05-21 17:32:52 INFO  BlockPuzzleSolver - iterate Count: 2190893
//        2023-05-21 17:32:52 INFO  BlockPuzzleSolver - Solution:
//        1111ll3333
//        1bbblddd3q
//        cccbeed555
//        c8kk7ee5j5
//        88mk779jjj
//        8mmgg799a0
//        6nggif9aa0
//        6niiifffa0
//        62222hhhh0
//        6o2p444440
//        2023-05-21 17:32:52 INFO  PerformanceRecorder - Step Complete initialize block puzzle time: 331ms
//        2023-05-21 17:32:52 INFO  PerformanceRecorder - Step Solve complete time: 41607ms
//        2023-05-21 17:32:52 INFO  PerformanceRecorder - Step Print solution time: 1ms

//        2023-05-21 17:28:43 INFO  BlockPuzzleSolver - Solved
//        2023-05-21 17:28:43 INFO  BlockPuzzleSolver - iterate Count: 1875
//        2023-05-21 17:28:43 INFO  BlockPuzzleSolver - Solution:
//        1111ll3333
//        1bbblddd3q
//        cccbeed555
//        c8kk7ee5j5
//        88mk779jjj
//        8mmgg799a0
//        6nggif9aa0
//        6niiifffa0
//        62222hhhh0
//        6o2p444440
//        2023-05-21 17:28:43 INFO  PerformanceRecorder - Step Complete initialize block puzzle time: 52ms
//        2023-05-21 17:28:43 INFO  PerformanceRecorder - Step Solve complete time: 135ms
//        2023-05-21 17:28:43 INFO  PerformanceRecorder - Step Print solution time: 7ms
    }

    @Test
    public void testSolve11() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(12, 12), new String[]{"1", "11", "1,1", "111", "1,1,1", "01,11", "11,1", "11,01", "11,11", "1111", "1,1,1,1",
                "111,1", "111,01", "111,001", "1,111", "01,111", "001,111", "11,011", "011,11", "1,11,1",
                "01,11,01", "01,11,1", "1,11,01", "11111", "1,1,1,1,1", "1111,1", "1111,01", "1111,001",
                "1111,0001", "111,101", "111,110", "111,011", "101,111", "110,111", "011,111", "111,1"});
        assertPuzzleSolvable(blockPuzzle);
        // 153478
        // 95158
    }

    @Test
    @Disabled
    public void testSolve12() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(13, 13), new String[]{"1", "1", "1", "1", "1", "1", "1", "1", "11,1", "1", "1,1", "11", "11", "1", "1", "1",
                "1", "11,11,111,1", "111,01", "111,001", "1,111", "01,111,001,111", "11,011,011,11",
                "1,111111,01", "01,11,11,11,01", "111111,1,1,1,1", "1111,11111,01", "1111,1111,101",
                "111,011,111,11,11,0111", "011,111,1,11111,111,011", "111,001,001,111", "01,1111,111111",
                "01,111,01,01,111,001,001,111,1"

        });
        // iterate Count: 110452847
        // bbii222333333
        // bbddd223r0aak
        // bbbd2223000aa
        // b88822t3h0saa
        // p11822v3h0aam
        // 11187222000qc
        // 18887777770cc
        // 111117wggu0cc
        // 1115555g000cc
        // o11555550jj9c
        // 666654eeef999
        // 66664444efff9
        // 6l6n444444999
        // Step Complete initialize block puzzle time: 158ms
        // Step Solve complete time: 6073620ms
        // Step Print solution time: 1ms

        // iterate Count: 110452847
        // Solution:
        // bbii222333333
        // bbddd223r0aak
        // bbbd2223000aa
        // b88822t3h0saa
        // p11822v3h0aam
        // 11187222000qc
        // 18887777770cc
        // 111117wggu0cc
        // 1115555g000cc
        // o11555550jj9c
        // 666654eeef999
        // 66664444efff9
        // 6l6n444444999
        // Step Complete initialize block puzzle time: 255ms
        // Step Solve complete time: 2787629ms
        // Step Print solution time: 0ms

        // iterate Count: 17372861
        // bbii222333333
        // bbddd223r0aak
        // bbbd2223000aa
        // b88822t3h0saa
        // p11822v3h0aam
        // 11187222000qc
        // 18887777770cc
        // 111117wggu0cc
        // 1115555g000cc
        // o11555550jj9c
        // 666654eeef999
        // 66664444efff9
        // 6l6n444444999
        // Step Complete initialize block puzzle time: 875ms
        // Step Solve complete time: 955254ms
        // Step Print solution time: 1ms
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
//    @Disabled
    public void testSolve12a() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(13, 13), new String[]{"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1,1", "11", "11", "1", "1", "1",
                "1", "11,11,111,1", "111,01", "111,001", "1,111", "01,111,001,111", "11,011,011,11",
                "1,111111,01", "01,11,11,11,01", "111111,1,1,1,1", "1111,11111,01", "1111,1111,101",
                "111,011,111,11,11,0111", "011,111,1,11111,111,011", "111,001,001,111", "01,1111,111111",
                "01,111,01,01,111,001,001,111,1"

        });
        assertPuzzleSolvable(blockPuzzle);
        // iterate Count: 1078661
        // g888bb5555iic
        // g118bb55555cc
        // 1118bbb5eqncc
        // 1888b7tueeecc
        // 111117777770c
        // 111fff7yaa000
        // m11vf222waa0k
        // 33333322xaa0l
        // 36666222aa000
        // 3666622s9ddd0
        // 3646r22999pd0
        // 34444o2229000
        // j4444449990hh
        // Step Complete initialize block puzzle time: 323ms
        // Step Solve complete time: 86446ms
        // Step Print solution time: 2ms

        // iterate Count: 752877
        // g888bb5555iic
        // g118bb55555cc
        // 1118bbb5eqncc
        // 1888b7tueeecc
        // 111117777770c
        // 111fff7yaa000
        // m11vf222waa0k
        // 33333322xaa0l
        // 36666222aa000
        // 3666622s9ddd0
        // 3646r22999pd0
        // 34444o2229000
        // j4444449990hh
        // Step Complete initialize block puzzle time: 1719ms
        // Step Solve complete time: 51727ms
        // Step Print solution time: 2ms
    }

    @Test
    public void testSolve12b() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(13, 13), new String[]{"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "11,11,111,1", "111,01", "111,001", "1,111", "01,111,001,111", "11,011,011,11",
                "1,111111,01", "01,11,11,11,01", "111111,1,1,1,1", "1111,11111,01", "1111,1111,101",
                "111,011,111,11,11,0111", "011,111,1,11111,111,011", "111,001,001,111", "01,1111,111111",
                "01,111,01,01,111,001,001,111,1"

        });
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve12c() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(13, 13), new String[]{"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1,1", "11,111,1", "111,01", "111,001", "1,111", "01,111,001,111", "11,011,011,11",
                "1,111111,01", "01,11,11,11,01", "111111,1,1,1,1", "1111,11111,01", "1111,1111,101",
                "111,011,111,11,11,0111", "011,111,1,11111,111,011", "111,001,001,111", "01,1111,111111",
                "01,111,01,01,111,001,001,111,1"

        });
        assertPuzzleSolvable(blockPuzzle);
// iterate Count: 13177911
//
// 222333333fffb
// k2236666n9fbb
// 22236666999bb
// 22A36e6tpo9bb
// 22y37eee9990b
// w222777777000
// z888l75555m0g
// x118cc555550g
// 1118ccc5aa000
// 1888crdddaaB0
// 111114qsdaaC0
// 111u4444aa000
// j11v4444440hi
// Step Complete initialize block puzzle time: 356ms
// Step Solve complete time: 928836ms
// Step Print solution time: 1ms

// iterate Count: 5467887
//
// 222333333fffb
// k2236666n9fbb
// 22236666999bb
// 22A36e6tpo9bb
// 22y37eee9990b
// w222777777000
// z888m75555l0g
// x118cc555550g
// 1118ccc5aa000
// 1888crdddaaB0
// 111114qsdaaC0
// 111u4444aa000
// j11v4444440hi
// Step Complete initialize block puzzle time: 233ms
// Step Solve complete time: 917784ms
// Step Print solution time: 1ms

// iterate Count: 480103
//
// 222333333fffb
// k2236666s9fbb
// 22236666999bb
// 22x36e6Ayu9bb
// 22B37eee9990b
// n222777777000
// m888C75555t0g
// o118cc555550g
// 1118ccc5aa000
// 1888czdddaaq0
// 111114vwdaap0
// 111r4444aa000
// j11l4444440ih
// Step Complete initialize block puzzle time: 398ms
// Step Solve complete time: 17190ms
// Step Print solution time: 1ms

// iterate Count: 90231
//
// 222333333fffb
// k2236666s9fbb
// 22236666999bb
// 22x36e6Ayu9bb
// 22B37eee9990b
// n222777777000
// m888C75555t0g
// o118cc555550g
// 1118ccc5aa000
// 1888czdddaaq0
// 111114vwdaap0
// 111r4444aa000
// j11l4444440hi
// Step Complete initialize block puzzle time: 145ms
// Step Solve complete time: 8163ms
// Step Print solution time: 7ms
// 2023-05-21 19:51:38 INFO  BlockPuzzleSolver - Solved
// 2023-05-21 19:51:38 INFO  BlockPuzzleSolver - iterate Count: 88039
// 2023-05-21 19:51:38 INFO  BlockPuzzleSolver - Solution:
// 222333333fffb
// k2236666s9fbb
// 22236666999bb
// 22x36e6Ayu9bb
// 22B37eee9990b
// n222777777000
// m888C75555t0g
// o118cc555550g
// 1118ccc5aa000
// 1888czdddaaq0
// 111114vwdaap0
// 111r4444aa000
// j11l4444440ih
// 2023-05-21 19:51:38 INFO  PerformanceRecorder - Step Complete initialize block puzzle time: 189ms
// 2023-05-21 19:51:38 INFO  PerformanceRecorder - Step Solve complete time: 4823ms
// 2023-05-21 19:51:38 INFO  PerformanceRecorder - Step Print solution time: 6ms

    }

    @Test
    public void testSolve12d() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(13, 13), new String[]{"1,1,1", "11", "1,1", "1",
                "1,1", "11,111,11", "111,01", "111,111", "1,111", "01,111,001,111", "11,0111,0111,11",
                "1,111111,11", "01,11,11,11,01", "111111,1,1,1,1", "11111,11111,01", "1111,1111,101111",
                "111,111,111,111,111,0111", "011,111,1,11111,111,111", "111,001,001,111", "01,1111,111111",
                "01,111,01,01,111,001,001,111,1"
        });
        assertPuzzleSolvable(blockPuzzle);
    }



    @Test
    public void testSolve7() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(13, 13), new String[]{"11", "111,11", "001,111", "1,1111", "111", "01,01,01,11,01",
                "0111,01,01,01,01,01,11,11,1,1,1", "11,11,11,11,1,1,1", "111111,1,111,001", "111,11111",
                "1,111111,1,1,1,1,1,1", "0111111,11", "111111", "01,11", "1,11,11", "11,11,01", "01,011,111",
                "111,111111", "0000001,0000001,0000001,0000001,0000011,1111111,0000011",
                "111,011,011,011,01,01,01,01", "0011111,1111", "1111111"

        });
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve13() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(13, 13), new String[]{"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve13b() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(12, 12), new String[]{"11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11",
                "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11",
                "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11",
                "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11",
                "11", "11", "11", "11", "11", "11", "11", "11", "11"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Disabled
    @Test
    public void testSolve14() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(14, 14), new String[]{"1,1,1", "11", "1,1", "1",
                "1,1", "11,111,11", "111,01", "111,111", "1,111", "01,111,001,111", "11,0111,0111,11",
                "1,111111,11", "01,11,11,11,01", "111111,1,1,1,1", "11111,11111,01", "1111,1111,101111",
                "111,111,111,111,111,0111", "011,111,1,11111,111,111", "111,001,001,111", "01,1111,111111",
                "1111111", "1111111", "1,1,1,1,1,1,1", "1,1,1,1,1,1", "01,111,01,01,111,001,001,111,1"
        });
        assertPuzzleSolvable(blockPuzzle);
//        2023-05-21 21:39:46 INFO  BlockPuzzleSolver - Solved
//        2023-05-21 21:39:46 INFO  BlockPuzzleSolver - iterate Count: 34701447
//        2023-05-21 21:39:46 INFO  BlockPuzzleSolver - Solution:
//        cccccccddddddd
//        b222333333jjje
//        b22234444oajee
//        b22234444aaaee
//        b22234i4444aee
//        b22236iiiaaa0e
//        bk222666666000
//        bk99966888880l
//        fk119gg888880l
//        f1119ggg877000
//        f1999gghhh7770
//        f111115hhh7770
//        f111m555577000
//        f111m5555550nn
//        2023-05-21 21:39:46 INFO  PerformanceRecorder - Step Complete initialize block puzzle time: 170ms
//        2023-05-21 21:39:46 INFO  PerformanceRecorder - Step Solve complete time: 1893456ms
//        2023-05-21 21:39:46 INFO  PerformanceRecorder - Step Print solution time: 1ms
    }

    @Test
    public void testSolve15() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(12, 12), new String[]{"1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1",
                "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1",
                "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1",
                "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1",
                "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1",
                "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1", "1,1"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve30() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(5, 5), new String[]{
                "11", "11", "11", "11", "11",
                "11", "11", "11", "11", "11",
                "1", "1", "1", "1", "1"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve31() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(7, 7), new String[]{
                "11", "11", "11",
                "11", "11", "11",
                "11", "11", "11",
                "11", "11", "11",
                "11", "11", "11",
                "11", "11", "11",
                "11", "11", "11",
                "1", "1", "1", "1", "1", "1", "1"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve32() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(7, 7), new String[]{
                "11", "11", "11",
                "11", "11", "11",
                "11", "11", "11",
                "11", "11", "11",
                "11", "11", "11",
                "11", "11", "11",
                "11", "11", "11",
                "1", "1", "1", "1", "1", "1", "1"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve33() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(9, 9), new String[]{
                "11", "11", "11", "11",
                "11", "11", "11", "11",
                "11", "11", "11", "11",
                "11", "11", "11", "11",
                "11", "11", "11", "11",
                "11", "11", "11", "11",
                "11", "11", "11", "11",
                "11", "11", "11", "11",
                "11", "11", "11", "11",
                "1", "1", "1", "1", "1", "1", "1", "1", "1"});
        assertPuzzleSolvable(blockPuzzle);
    }


    @Test
    public void testSolve34() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(11, 11), new String[]{
                "11", "11", "11", "11", "11",
                "11", "11", "11", "11", "11",
                "11", "11", "11", "11", "11",
                "11", "11", "11", "11", "11",
                "11", "11", "11", "11", "11",
                "11", "11", "11", "11", "11",
                "11", "11", "11", "11", "11",
                "11", "11", "11", "11", "11",
                "11", "11", "11", "11", "11",
                "11", "11", "11", "11", "11",
                "11", "11", "11", "11", "11",
                "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve35() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(12, 12), new String[]{"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1,1", "11", "111,1", "111,01", "11,011", "011,11",
                "1,111111,01", "01,11,11", "11,01", "111111", "1,1,1,1", "1111,11111,01", "1111,1111,101",
                "111,011,111,11,11,0111", "011,111,1,11111,111,011", "111,001,001,111", "01,1111,111111",
                "01,111,01,01,111,001,001,111,1"
        });
        assertPuzzleSolvable(blockPuzzle);
// Solved
// iterate Count: 55479
//
// 44442225555a
// 44444225555a
// j4st2225q5na
// m77722bbbr0a
// o11722bdd000
// 11176222dd0g
// 17776666660g
// 111116ee9000
// 111u3ee99ff0
// l11333399pf0
// ccc333333000
// ic888888k0hh
// Step Complete initialize block puzzle time: 100ms
// Step Solve complete time: 2180ms
// Step Print solution time: 9ms
// Solved
// iterate Count: 93617
//
// 44442225555a
// 44444225555a
// t4qr2225k5ja
// u77722bbbl0a
// s11722bdd000
// 11176222dd0g
// 17776666660g
// 111116ee9000
// 111i3ee99ff0
// m11333399of0
// ccc333333000
// nc888888p0hh
    }

    @Test
    public void testSolve16() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(13, 13), new String[]{
                "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11",
                "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11",
                "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11",
                "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11",
                "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11", "11",
                "11", "11", "11", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve17a() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(10, 10), new String[]{
                "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve17() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(8, 8), new String[]{
                "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1", "1", "1", "1", "1"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve18() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(13, 13), new String[]{"1", "11", "1,1", "01,11", "11,1", "11,01", "1111", "1,1,1,1", "11,11", "111,1",
                "111,01", "111,001", "1,111", "01,111", "001,111", "11,011", "011,11", "1,11,1", "01,11,01",
                "01,11,1", "1,11,01", "11111", "1,1,1,1,1", "1111,1", "1111,01", "1111,001", "111,101",
                "111,011", "111,11", "11,0111", "011,111", "1,111,1", "1,111,01", "1,111,001", "01,111,1",
                "01,111,01", "01,111,001", "001,111,1", "001,111,01", "001,111,001"
        });
        assertPuzzleSolvable(blockPuzzle);
// iterate Count: 293846
// Solution:
// 4444aaaaccccc
// 43oooaxxrbbbb
// B333omxxrrrbj
// B37kmmzzngggj
// 777kkmzlnnggj
// 67yykAllnpppj
// 666yAAlsspfff
// D6vveqqqssfuf
// 0vveeeqiiuuu5
// 0hhhe8iiiw555
// 0hh28881www95
// 02228dd111999
// 02ttttddd1CC9
// Step Complete initialize block puzzle time: 104ms
// Step Solve complete time: 17267ms
// Step Print solution time: 0ms
    }

    @Test
    public void testSolve19() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(8, 5), new String[]{"1,1", "1,1,1", "11,01", "11,11", "011,11", "1,11,1",
                "001,111", "1111", "001,001,111", "00111,111,1",});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    @Disabled
    public void testSolve20() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(13, 13), new String[]{"11", "111,11", "001,111", "1,1111", "111", "01,01,01,11,01",
                "0111,01,01,01,01,01,11,11,1,1,1", "11,11,11,11,1,1,1", "111111,1,111,001", "111,11111",
                "1,111111", "1,1,1,1,1,1", "0111111,11", "111111", "01,11", "1,11,11", "11,11,01", "01,011,111",
                "111,111111", "0000001,0000001,0000001,0000001,0000011,1111110", "01,11",
                "111,011,011,011,01,01,01,01", "0011111,1111", "1111111"

        });
        assertPuzzleSolvable(blockPuzzle);
        // only block disabled
//        2023-05-21 00:09:05 INFO  BlockPuzzleSolver - Solved
//        2023-05-21 00:09:05 INFO  BlockPuzzleSolver - iterate Count: 2208052
//        2023-05-21 00:09:05 INFO  BlockPuzzleSolver - Solution:
//        8ccccccc55555
//        8888885555111
//        9000666666j11
//        90g66mmmjjj11
//        90ggddddddb11
//        90gg44iiilb12
//        90hh44iillb12
//        90hh44fnnbb12
//        00eh44ffffb12
//        00ee433333322
//        0eee43222222k
//        07774333aaakk
//        07777773aaaaa
//        2023-05-21 00:09:05 INFO  PerformanceRecorder - Step Complete initialize block puzzle time: 119ms
//        2023-05-21 00:09:05 INFO  PerformanceRecorder - Step Solve complete time: 93313ms
//        2023-05-21 00:09:05 INFO  PerformanceRecorder - Step Print solution time: 1ms
        // only block enabled
//        2023-05-21 00:10:08 INFO  BlockPuzzleSolver - Solved
//        2023-05-21 00:10:08 INFO  BlockPuzzleSolver - iterate Count: 1098612
//        2023-05-21 00:10:08 INFO  BlockPuzzleSolver - Solution:
//        8ccccccc55555
//        8888885555111
//        9000666666j11
//        90g66mmmjjj11
//        90ggddddddb11
//        90gg44iiilb12
//        90hh44iillb12
//        90hh44fnnbb12
//        00eh44ffffb12
//        00ee433333322
//        0eee43222222k
//        07774333aaakk
//        07777773aaaaa
//        2023-05-21 00:10:08 INFO  PerformanceRecorder - Step Complete initialize block puzzle time: 8ms
//        2023-05-21 00:10:08 INFO  PerformanceRecorder - Step Solve complete time: 63235ms
//        2023-05-21 00:10:08 INFO  PerformanceRecorder - Step Print solution time: 1ms

    }

    @Test
    public void testSolve21() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(6, 6), new String[]{"11,01", "1,1,1", "011,11", "111,1", "1,1,1,1", "01,111",
                "11,11", "111,11", "001,111,1"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve22() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(10, 4), new String[]{"1,1", "11,1", "111", "11,11", "1111", "1,11,01",
                "01,01,11", "11111", "01,11,01", "011,11111"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve23() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(4, 10), new String[]{"11", "111", "1,11", "01,111", "11,01,01", "11,11",
                "01,011,11", "11,11,01", "11,01,011", "1,1,1,1,1"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve24() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(10, 4), new String[]{"11", "111", "01,11", "111,1", "01,11,11", "01,11,1",
                "1111", "11,11", "01,111", "01,11111,0001"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve25() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(5, 8), new String[]{"1,1", "111", "01,11", "01,01,11", "1,11,01", "01,11,01",
                "11,11", "11,0111,001", "1,11,111", "1111"});
        assertPuzzleSolvable(blockPuzzle);
        // only block disabled
//        2023-05-14 23:53:18 INFO  BlockPuzzleSolver - Solved
//        2023-05-14 23:53:18 INFO  BlockPuzzleSolver - iterate Count: 250
//        2023-05-14 23:53:18 INFO  BlockPuzzleSolver - Solution:
//        2023-05-14 23:53:18 INFO  Solution - 55557
//        2023-05-14 23:53:18 INFO  Solution - 10077
//        2023-05-14 23:53:18 INFO  Solution - 11000
//        2023-05-14 23:53:18 INFO  Solution - 11103
//        2023-05-14 23:53:18 INFO  Solution - 66233
//        2023-05-14 23:53:18 INFO  Solution - 66243
//        2023-05-14 23:53:18 INFO  Solution - 92244
//        2023-05-14 23:53:18 INFO  Solution - 98884
//        2023-05-14 23:53:18 INFO  PerformanceRecorder - Step Complete initialize block puzzle time: 1ms
//        2023-05-14 23:53:18 INFO  PerformanceRecorder - Step Solve complete time: 3ms
//        2023-05-14 23:53:18 INFO  PerformanceRecorder - Step Print solution time: 0ms
        // only block enabled
//        2023-05-14 23:53:18 INFO  BlockPuzzleSolver - Solved
//        2023-05-14 23:53:18 INFO  BlockPuzzleSolver - iterate Count: 252
//        2023-05-14 23:53:18 INFO  BlockPuzzleSolver - Solution:
//        2023-05-14 23:53:18 INFO  Solution - 55557
//        2023-05-14 23:53:18 INFO  Solution - 10077
//        2023-05-14 23:53:18 INFO  Solution - 11000
//        2023-05-14 23:53:18 INFO  Solution - 11103
//        2023-05-14 23:53:18 INFO  Solution - 66233
//        2023-05-14 23:53:18 INFO  Solution - 66243
//        2023-05-14 23:53:18 INFO  Solution - 92244
//        2023-05-14 23:53:18 INFO  Solution - 98884
//        2023-05-14 23:53:18 INFO  PerformanceRecorder - Step Complete initialize block puzzle time: 0ms
//        2023-05-14 23:53:18 INFO  PerformanceRecorder - Step Solve complete time: 4ms
//        2023-05-14 23:53:18 INFO  PerformanceRecorder - Step Print solution time: 0ms

    }

    @Test
    public void testSolve26() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(8, 3), new String[]{"1,1", "111", "01,11", "11,11", "11,011", "111,01", "001,111"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve27() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(
                new Dimension(3, 8), new String[]{"11", "1,1,1", "01,11", "11,11", "1,11,01", "1,11,1", "01,01,11"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve28() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(10, 4), new String[]{"1,1", "11,1", "1,1,1", "01,11,1", "01,111", "01,01,11",
                "11,11", "1111", "1111,0001", "01,111111"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve29() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(10, 4), new String[]{"1,1", "11,01", "1,1,1", "1,111", "111,01", "1111", "11,11",
                "011,11,01", "1,11,0111", "11,0111"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve855() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(4, 10), new String[]{"11", "111", "11,11", "1,1,11", "1,11,01", "01,11,01",
                "01,111,01", "1,1,1,1", "01,01,01,11", "01,01,11,01"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve482() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(6, 8), new String[]{"1,1", "01,11", "111", "11,011", "11,11", "111,01", "1,1,11",
                "1,1,1,1", "1111,01", "111,1,1", "11,1,1,1", "11111"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve489() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(10, 5), new String[]{"11", "11,10", "01,11,1", "11,11", "111,01", "01,01,11",
                "1,1,1,1", "1,111,1", "1,1,1,11", "01,111,01", "11,01,11", "11,011,01"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve484() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(6, 8), new String[]{"11,1", "1,1,1", "1111", "11,011", "11,11", "1,11,1",
                "1,1,11", "1,11,011", "1,111,001", "11,01,11", "1001,11111"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve486() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(8, 7), new String[]{"11", "111", "01,11", "1,111,1", "1,111,001", "011,111",
                "1111,1", "111,0011", "1,1,111", "1,1111,0001", "1111,00011", "111111"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve486_2() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(9, 6), new String[]{"11,1", "1,1,1", "1111", "11,11", "11,011", "1,111",
                "101,111", "1,111,1", "111,1,1", "11,11,01", "11111", "001,001,1111,1"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve485() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(4, 12), new String[]{"1,1", "01,11", "11,11", "1,1,1,1", "11,1,1", "1,11,1",
                "01,11,11", "011,11,01", "01,01,11,01", "11,01,01,011", "11,1,11,01"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve491() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(7, 7), new String[]{"11", "01,11", "111", "1,111", "011,11", "111,01", "1111",
                "111,101", "01,1111", "001,111,001", "111,0011", "1,111,001"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve491_2() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(7, 7), new String[]{"11", "11,1", "1,1,1", "1,11,01", "111,01", "001,111",
                "11,11", "1,1,1,1", "1,1,1,11", "111,01,01", "1,111,001", "01,111,0011"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve492() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(7, 7), new String[]{"11", "1,1,1", "1,11", "11,011", "1111", "111,001", "11,11",
                "1,1111", "111,011", "111,1,1", "001,111,1", "01,011,11"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve493() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(8, 6), new String[]{"1,1", "1,11", "111", "11,11", "011,11", "01,11,01", "1111",
                "01,01,11", "1,11,11", "1,111,01", "01,01,11,01", "11,01,011"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolve496() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(10, 5), new String[]{"1,1", "11,01", "11,11", "011,11", "11,01,01", "01,11,01",
                "1,1,1,1", "01,01,111", "11111", "01,11,11", "01,1111", "111,0011"});
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolveOnlyBlock() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(7, 7), new String[]{
                "1111,01,01,01,0111,01",
                "11,11,01",
                "111,101", "1", "1",
                "1,1", "11,1,1", "11,1", "1",
                "1", "1", "1",
                "1111", "111", "1,1,1", "1,1,1"
        });
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolveOnlyBlock2() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(6, 7), new String[]{
                "1111,01,01,01,0111,01",
                "11,1",
                "1",
                "1,11", "1,1,1,1,1", "11", "11,1,1",
                "1", "1",
                "11", "11", "1", "1,11111"
        });
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolveOnlyBlock3() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(7, 7), new String[]{
                "1111,01,01,01,0111,110",
                "11,1",
                "1",
                "1,11", "1,1,1,1", "1", "1", "1,11,1",
                "1,1",
                "11", "1", "1,111", "11111,1,1,1,1,1,1"
        });
        assertPuzzleSolvable(blockPuzzle);
    }

    @Test
    public void testSolveNeverFinish() {
        BlockPuzzle blockPuzzle = new BlockPuzzle(new Dimension(7, 7), new String[]{
                "1",
                "1",
                "1",
                "1",
                "1",
                "1",
                "1,1",
                "1,1,1,1",
                "1,11",
                "1,11,1",
                "1,11111",
                "11",
                "11,1",
                "1111,01,01,01,0111,011"
        });
        assertThrows(IllegalStateException.class, () -> new BlockPuzzleSolver(blockPuzzle));
    }

    private void assertPuzzleSolvable(BlockPuzzle blockPuzzle) {
//        long disableOnlyIterateCount;
//        {
//            BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
//            BoardFillState.isEnableOnly = false;
//            Solution solutions = solver.solve();
//            assertFalse(solutions.isEmpty());
//            disableOnlyIterateCount = solver.iterateCount;
//        }
//        System.out.println("-".repeat(80));
//        long enableOnlyIterateCount;
//        {
//            BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
//            BoardFillState.isEnableOnly = true;
//            Solution solutions = solver.solve();
//            assertFalse(solutions.isEmpty());
//            enableOnlyIterateCount = solver.iterateCount;
//        }
//        assertTrue(enableOnlyIterateCount <= disableOnlyIterateCount);
        BlockPuzzleSolver solver = new BlockPuzzleSolver(blockPuzzle);
        Solution solutions = solver.solve();
        assertFalse(solutions.isEmpty());
    }

}
