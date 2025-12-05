package com.samabcde.puzzlesolver.component;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BlockTest {
    @ParameterizedTest
    @CsvSource(
            textBlock =
                    """
                    01,01| 1| 2
                    10,10| 1| 2
                    00,11| 2| 1
                    11,00| 2| 1
                    """,
            delimiter = '|')
    public void trimValue(String value, int expectedWidth, int expectedHeight) {
        Block block = new Block(value, 1);
        assertEquals(expectedWidth, block.getWidth());
        assertEquals(expectedHeight, block.getHeight());
    }

    @ParameterizedTest
    @CsvSource(
            textBlock =
                    """
                    1| 1
                    0| 0
                    -1| -1
                    """,
            delimiter = '|')
    public void id(int id, int expected) {
        Block block = new Block("1", id);
        assertEquals(block.id, expected);
    }

    @ParameterizedTest
    @CsvSource(
            textBlock =
                    """
                    1| 1
                    11| 2
                    1,11| 2
                    1011,111| 4
                    """,
            delimiter = '|')
    public void width(String value, int expected) {
        Block block = new Block(value, 1);
        assertEquals(block.getWidth(), expected);
    }

    @ParameterizedTest
    @CsvSource(
            textBlock =
                    """
                    1| 1
                    1,1| 2
                    1,11| 2
                    11,01,11| 3
                    """,
            delimiter = '|')
    public void height(String value, int expected) {
        Block block = new Block(value, 1);
        assertEquals(block.getHeight(), expected);
    }

    @ParameterizedTest
    @CsvSource(
            textBlock =
                    """
                    1| 1
                    1,1| 2
                    1,11| 3
                    11,01,11| 5
                    """,
            delimiter = '|')
    public void weight(String value, int expected) {
        Block block = new Block(value, 1);
        assertEquals(block.getWeight(), expected);
    }

    @ParameterizedTest
    @CsvSource(
            textBlock =
                    """
                    0|0|true
                    0|1|true
                    0|2|true
                    0|3|true
                    0|4|true
                    1|0|true
                    1|1|true
                    1|2|false
                    1|3|true
                    1|4|true
                    2|0|true
                    2|1|false
                    2|2|false
                    2|3|false
                    2|4|true
                    """,
            delimiter = '|')
    public void get(int rowIndex, int columnIndex, boolean expected) {
        Block block = new Block("11111,11011,10001", 1);
        assertEquals(expected, block.get(rowIndex, columnIndex));
    }

    @Test
    public void toStringTest() {
        Block block = new Block("11111,11011,10001", 1);
        assertEquals(
                "11111" + System.lineSeparator() + "11011" + System.lineSeparator() + "10001",
                block.toString());
    }
}
