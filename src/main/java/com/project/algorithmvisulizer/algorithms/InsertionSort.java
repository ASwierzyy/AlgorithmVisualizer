package com.project.algorithmvisulizer.algorithms;
import com.project.algorithmvisulizer.DTOs.AlgorithmStep;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Component("insertionSort")
public class InsertionSort implements SortingAlgorithm {

    @Override
    public void sort(List<Integer> input, Consumer<AlgorithmStep> stepPublisher) {
        List<Integer> arr = new ArrayList<>(input);

        for (int i = 1; i < arr.size(); i++) {
            int key = arr.get(i);
            int j = i - 1;

            stepPublisher.accept(new AlgorithmStep(new ArrayList<>(arr), List.of(i)));

            while (j >= 0 && arr.get(j) > key) {
                arr.set(j + 1, arr.get(j));
                stepPublisher.accept(new AlgorithmStep(new ArrayList<>(arr), List.of(j, j + 1)));
                j--;
            }

            arr.set(j + 1, key);
            stepPublisher.accept(new AlgorithmStep(new ArrayList<>(arr), List.of(j + 1)));
        }
    }
}