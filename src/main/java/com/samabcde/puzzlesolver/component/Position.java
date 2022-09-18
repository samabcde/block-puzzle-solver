package com.samabcde.puzzlesolver.component;

public record Position(int x, int y) {

    @Override
    public String toString() {
        return "y:" + y + ",x:" + x;
    }
}