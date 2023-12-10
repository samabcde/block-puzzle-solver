package com.samabcde.puzzlesolver.app.validate;

import java.util.Arrays;
import java.util.List;

public class BlocksValidator {
    private final Integer width;
    private final Integer height;

    public BlocksValidator(Integer width, Integer height) {
        this.width = width;
        this.height = height;
    }

    public void validate(String[] blocks) {
        if (blocks.length == 0) {
            throw new IllegalArgumentException("blocks must not be empty");
        }
        List<String> containsInvalids = Arrays.stream(blocks)
                .filter(s -> s.chars().anyMatch(c -> !(c == '0' || c == '1' || c == ','))).toList();
        if (!containsInvalids.isEmpty()) {
            throw new IllegalArgumentException("below blocks contains character other than '0', '1', ',' %s".formatted(containsInvalids.toString()));
        }
        long weight = Arrays.stream(blocks).mapToLong(s -> s.chars().filter(c -> c == '1').count()).sum();
        if (weight != (long) width * height) {
            throw new IllegalArgumentException("total block weight:%s must equals puzzle size %d".formatted(weight, width * height));
        }
    }
}
