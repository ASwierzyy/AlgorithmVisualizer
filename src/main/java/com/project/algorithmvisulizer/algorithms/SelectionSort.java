package com.project.algorithmvisulizer.algorithms;

import com.project.algorithmvisulizer.DTOs.AlgorithmStep;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Collections.swap;

@Component("selectionSort")
public class SelectionSort implements SortingAlgorithm{
    @Override
    public void sort(List<Integer> input, Consumer<AlgorithmStep> stepConsumer) {
        List<Integer> arr = new ArrayList<>(input);
        for (int i = 0; i < arr.size() - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < arr.size(); j++) {
                if (arr.get(j) < arr.get(minIdx)) minIdx = j;
                stepConsumer.accept(new AlgorithmStep(new ArrayList<>(arr), List.of(j, minIdx)));
            }
            swap(arr, i, minIdx);
            stepConsumer.accept(new AlgorithmStep(new ArrayList<>(arr), List.of(i, minIdx)));
        }
    }
}
