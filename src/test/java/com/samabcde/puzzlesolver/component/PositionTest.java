package com.samabcde.puzzlesolver.component;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PositionTest {
    @Test
    void toStringTest() {
        assertEquals("y:1,x:1", new Position(1, 1).toString());
    }
}
