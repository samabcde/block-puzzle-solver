package com.samabcde.puzzlesolver.app;

import com.samabcde.puzzlesolver.component.BlockPuzzle;
import com.samabcde.puzzlesolver.solve.BlockPuzzleSolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.Arrays;
import java.util.concurrent.Callable;

@Command(name = "solve", mixinStandardHelpOptions = true, version = "0.0.1")
public class SolverApplication implements Callable<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(BlockPuzzleSolver.class);

    @Option(names = {"-pw", "--puzzle-width"}, description = "Width of Puzzle", required = true)
    private Integer width;

    @Option(names = {"-ph", "--puzzle-height"}, description = "Height of Puzzle", required = true)
    private Integer height;

    @Option(names = {"-bs", "--blocks"}, description = "The blocks in the puzzle, " +
            "should contains '0'(empty) ,'1'(point), ','(row separator) and ';'(block separator) only, e.g. the `T` shape " +
            "is \"111,01\"", split = ";", required = true)
    private String[] blocks;

    public static void main(String... args) {
        int exitCode = new CommandLine(new SolverApplication()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        logger.info("width:" + width + ", height:" + height + ", blocks:" + Arrays.toString(blocks));
        BlockPuzzle blockPuzzle = new BlockPuzzle(width, height, blocks);
        new BlockPuzzleSolver(blockPuzzle).solve();
        return 0;
    }
}
