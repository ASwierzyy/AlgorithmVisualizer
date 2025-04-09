package com.project.algorithmvisulizer.algorithms;

import com.project.algorithmvisulizer.DTOs.AlgorithmStep;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InsertionSortTest {
    private final SortingAlgorithm sorter = new InsertionSort();

    @Test
    void shouldSortCorrectly() {
        List<Integer> input = List.of(9, 1, 5, 2);
        List<AlgorithmStep> steps = new ArrayList<>();

        sorter.sort(new ArrayList<>(input), steps::add);

        List<Integer> finalSnapshot = steps.getLast().arraySnapshot();
        assertEquals(List.of(1, 2, 5, 9), finalSnapshot);
    }

    @Test
    void shouldEmitSteps() {
        List<AlgorithmStep> steps = new ArrayList<>();
        sorter.sort(List.of(3, 2, 1), steps::add);

        assertFalse(steps.isEmpty());
    }
}