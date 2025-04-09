package com.project.algorithmvisulizer.algorithms;

import com.project.algorithmvisulizer.DTOs.AlgorithmStep;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

@Component("quickSort")
public class QuickSort implements SortingAlgorithm {
    @Override
    public void sort(List<Integer> input, Consumer<AlgorithmStep> stepPublisher) {
        List<Integer> arr = new ArrayList<>(input);
        quickSort(arr, 0, arr.size() - 1, stepPublisher);
    }

    private void quickSort(List<Integer> arr, int low, int high, Consumer<AlgorithmStep> stepPublisher) {
        if (low < high) {
            int pi = partition(arr, low, high, stepPublisher);

            quickSort(arr, low, pi - 1, stepPublisher);
            quickSort(arr, pi + 1, high, stepPublisher);
        }
    }

    private int partition(List<Integer> arr, int low, int high, Consumer<AlgorithmStep> stepPublisher) {
        int pivot = arr.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr.get(j) < pivot) {
                i++;
                Collections.swap(arr, i, j);
                stepPublisher.accept(new AlgorithmStep(new ArrayList<>(arr), List.of(i, j)));
            }
        }

        Collections.swap(arr, i + 1, high);
        stepPublisher.accept(new AlgorithmStep(new ArrayList<>(arr), List.of(i + 1, high)));

        return i + 1;
    }
}