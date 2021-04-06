package com.samabcde.puzzlesolver.app;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name = "solve")
public class SolverApplication implements Callable<Integer> {
    @Option(names = {"-w", "--width"}, description = "Width of Puzzle")
    private Integer width;

    @Option(names = {"-h", "--height"}, description = "Height of Puzzle")
    private Integer height;

    public static void main(String... args) {
        int exitCode = new CommandLine(new SolverApplication()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("width:" + width + ", height:" + height);
        return 0;
    }
}
