package com.project.algorithmvisulizer.algorithms;

import com.project.algorithmvisulizer.DTOs.AlgorithmStep;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {
    private final SortingAlgorithm sorter = new QuickSort();

    @Test
    void shouldSortCorrectly() {
        List<Integer> input = List.of(7, 2, 5, 1, 4);
        List<AlgorithmStep> steps = new ArrayList<>();

        sorter.sort(new ArrayList<>(input), steps::add);

        List<Integer> finalSnapshot = steps.getLast().arraySnapshot();
        assertEquals(List.of(1, 2, 4, 5, 7), finalSnapshot);
    }

    @Test
    void shouldEmitSteps() {
        List<AlgorithmStep> steps = new ArrayList<>();
        sorter.sort(List.of(3, 1, 2), steps::add);

        assertFalse(steps.isEmpty());
    }
}