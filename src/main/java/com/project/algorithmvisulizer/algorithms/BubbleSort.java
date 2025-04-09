package com.project.algorithmvisulizer.algorithms;

import com.project.algorithmvisulizer.DTOs.AlgorithmStep;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
@Component("bubbleSort")
public class BubbleSort implements SortingAlgorithm {
    @Override
    public void sort(List<Integer> input, Consumer<AlgorithmStep> publisher) {
        List<Integer> arr = new ArrayList<>(input);
        for (int i = 0; i < arr.size() - 1; i++) {
            for (int j = 0; j < arr.size() - i - 1; j++) {
                if (arr.get(j) > arr.get(j + 1)) {
                    Collections.swap(arr, j, j + 1);
                }
                publisher.accept(new AlgorithmStep(new ArrayList<>(arr), List.of(j, j + 1)));
            }
        }
    }
}
