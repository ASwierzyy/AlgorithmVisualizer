package com.project.algorithmvisulizer.algorithms;

import com.project.algorithmvisulizer.DTOs.AlgorithmStep;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RadixSortTest {
    private final SortingAlgorithm sorter = new RadixSort();

    @Test
    void shouldSortCorrectly() {
        List<Integer> input = List.of(170, 45, 75, 90, 802, 24);
        List<AlgorithmStep> steps = new ArrayList<>();

        sorter.sort(new ArrayList<>(input), steps::add);

        List<Integer> finalSnapshot = steps.getLast().arraySnapshot();
        assertEquals(List.of(24, 45, 75, 90, 170, 802), finalSnapshot);
    }

    @Test
    void shouldEmitSteps() {
        List<AlgorithmStep> steps = new ArrayList<>();
        sorter.sort(List.of(10, 5, 15), steps::add);

        assertFalse(steps.isEmpty());
    }
}