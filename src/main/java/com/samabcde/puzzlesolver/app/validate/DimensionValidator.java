package com.samabcde.puzzlesolver.app.validate;

public class DimensionValidator {
    public void validate(Integer width, Integer height) {
        boolean isWidthPositive = width > 0;
        boolean isHeightPositive = height > 0;
        if (!isWidthPositive && !isHeightPositive) {
            throw new IllegalArgumentException("width:%d and height:%s must be positive".formatted(width, height));
        }
        if (!isWidthPositive) {
            throw new IllegalArgumentException("width:%d must be positive".formatted(width));
        }
        if (!isHeightPositive) {
            throw new IllegalArgumentException("height:%d must be positive".formatted(height));
        }
    }
}
