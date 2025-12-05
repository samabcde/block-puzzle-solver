package com.samabcde.puzzlesolver.app.validate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BlocksValidatorTest {
    BlocksValidator validator = new BlocksValidator(6, 4);

    @ParameterizedTest
    @MethodSource("invalidBlocksTestCases")
    void Given_InvalidBlocks_When_Validate_Should_ThrowIllegalArgumentException(
            String[] blocks, String validationMessage) {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> validator.validate(blocks));
        assertEquals(validationMessage, exception.getMessage());
    }

    public static Stream<Arguments> invalidBlocksTestCases() {
        return Stream.of(
                arguments(new String[] {}, "blocks must not be empty"),
                arguments(
                        new String[] {"$ #", "a", "0,1"},
                        "below blocks contains character other than '0', '1', ',' [$ #, a]"),
                arguments(new String[] {"1"}, "total block weight:1 must equals puzzle size 24"));
    }

    @ParameterizedTest
    @MethodSource("validBlocksTestCases")
    void Given_ValidBlocks_When_Validate_Should_NotThrowIllegalArgumentException(String[] blocks) {
        assertDoesNotThrow(() -> validator.validate(blocks));
    }

    public static Stream<Arguments> validBlocksTestCases() {
        return Stream.of(
                arguments((Object) new String[] {"111111111111111111111111"}),
                arguments((Object) new String[] {"111111111111", "111111111111"}),
                arguments((Object) new String[] {"111,0111,111,0111", "111,00111,111,00111"}));
    }
}
