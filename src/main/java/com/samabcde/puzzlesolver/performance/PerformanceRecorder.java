package com.samabcde.puzzlesolver.performance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PerformanceRecorder {
    private static final Logger logger = LoggerFactory.getLogger(PerformanceRecorder.class);
    private final Map<String, Long> executeTime = new LinkedHashMap<>();

    public void init() {
        addExecution("init");
    }

    public void addExecution(String description) {
        executeTime.put(description, Instant.now().toEpochMilli());
    }

    public void print() {
        List<Map.Entry<String,Long>> executeTimes = executeTime.entrySet().stream().toList();
        for (int i = 0; i < executeTimes.size() - 1; i++) {
            logger.info("Step " + executeTimes.get(i + 1).getKey() + " time: "
                    + (executeTimes.get(i + 1).getValue() - executeTimes.get(i).getValue()) + "ms");
        }
    }
}
