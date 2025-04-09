package com.project.algorithmvisulizer.algorithms;

import com.project.algorithmvisulizer.DTOs.AlgorithmStep;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {
    private final SortingAlgorithm sorter = new MergeSort();

    @Test
    void shouldSortCorrectly() {
        List<Integer> input = List.of(10, 4, 3, 9, 1);
        List<AlgorithmStep> steps = new ArrayList<>();

        sorter.sort(new ArrayList<>(input), steps::add);

        List<Integer> finalSnapshot = steps.getLast().arraySnapshot();
        assertEquals(List.of(1, 3, 4, 9, 10), finalSnapshot);
    }

    @Test
    void shouldEmitSteps() {
        List<AlgorithmStep> steps = new ArrayList<>();
        sorter.sort(List.of(4, 2, 1), steps::add);

        assertFalse(steps.isEmpty());
    }
}