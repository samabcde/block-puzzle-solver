package com.samabcde.puzzlesolver.app;

import com.samabcde.puzzlesolver.component.BlockPuzzle;
import com.samabcde.puzzlesolver.component.BlockPuzzleSolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.Arrays;
import java.util.concurrent.Callable;

@Command(name = "solve")
public class SolverApplication implements Callable<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(BlockPuzzleSolver.class);

    @Option(names = {"-w", "--width"}, description = "Width of Puzzle")
    private Integer width;

    @Option(names = {"-h", "--height"}, description = "Height of Puzzle")
    private Integer height;

    @Option(names = {"-bs", "--blocks"}, description = "The blocks in the puzzle, " +
            "should contains '0'(empty) ,'1'(block), ','(row separator) and ';'(block separator) only", split = ";")
    private String[] blocks;

    public static void main(String... args) {
        int exitCode = new CommandLine(new SolverApplication()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        logger.info("width:" + width + ", height:" + height + ", blocks:" + Arrays.toString(blocks));
        BlockPuzzle blockPuzzle = new BlockPuzzle(width, height, blocks);
        new BlockPuzzleSolver(blockPuzzle).solve();
        return 0;
    }
}
