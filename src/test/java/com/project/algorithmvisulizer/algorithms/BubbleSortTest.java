package com.project.algorithmvisulizer.algorithms;

import com.project.algorithmvisulizer.DTOs.AlgorithmStep;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BubbleSortTest {
    private final SortingAlgorithm sorter = new BubbleSort();

    @Test
    void shouldSortCorrectlyViaFinalStep() {
        List<Integer> input = List.of(5, 3, 1, 2);
        List<AlgorithmStep> steps = new ArrayList<>();

        sorter.sort(new ArrayList<>(input), steps::add);

        List<Integer> lastSnapshot = steps.getLast().arraySnapshot();
        assertEquals(List.of(1, 2, 3, 5), lastSnapshot);
    }

    @Test
    void shouldEmitSteps() {
        List<Integer> input = new ArrayList<>(List.of(3, 1, 2));
        List<AlgorithmStep> steps = new ArrayList<>();

        sorter.sort(input, steps::add);

        assertFalse(steps.isEmpty());
        steps.forEach(step -> {
            assertNotNull(step.arraySnapshot());
            assertNotNull(step.highlightIndices());
        });
    }
}