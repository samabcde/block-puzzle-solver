package com.samabcde.puzzlesolver.app.validate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DimensionValidatorTest {

    @ParameterizedTest
    @CsvSource(textBlock = """
            0,0,width:0 and height:0 must be positive
            -1,-1,width:-1 and height:-1 must be positive
            0,1,width:0 must be positive
            -1,1,width:-1 must be positive
            1,-1,height:-1 must be positive
            1,0,height:0 must be positive
            """)
    void Given_InvalidInput_When_Validate_ShouldThrowIllegalArgumentException(Integer width, Integer height, String validationMessage) {
        DimensionValidator validator = new DimensionValidator();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(width, height));
        assertEquals(validationMessage, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            1,1
            """)
    void Given_ValidInput_When_Validate_ShouldNotThrowIllegalArgumentException(Integer width, Integer height) {
        DimensionValidator validator = new DimensionValidator();
        assertDoesNotThrow(() -> validator.validate(width, height));
    }
}