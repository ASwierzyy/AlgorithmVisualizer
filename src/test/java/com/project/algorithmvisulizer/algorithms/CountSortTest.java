package com.project.algorithmvisulizer.algorithms;

import com.project.algorithmvisulizer.DTOs.AlgorithmStep;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CountSortTest {
    private final SortingAlgorithm sorter = new CountSort();

    @Test
    void shouldSortCorrectly() {
        List<Integer> input = List.of(4, 2, 2, 8, 3, 3, 1);
        List<AlgorithmStep> steps = new ArrayList<>();

        sorter.sort(new ArrayList<>(input), steps::add);

        List<Integer> finalSnapshot = steps.getLast().arraySnapshot();
        assertEquals(List.of(1, 2, 2, 3, 3, 4, 8), finalSnapshot);
    }

    @Test
    void shouldEmitSteps() {
        List<AlgorithmStep> steps = new ArrayList<>();
        sorter.sort(List.of(3, 1, 2), steps::add);

        assertFalse(steps.isEmpty());
    }
}