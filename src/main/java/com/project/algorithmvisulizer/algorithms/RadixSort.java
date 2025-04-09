package com.project.algorithmvisulizer.algorithms;

import com.project.algorithmvisulizer.DTOs.AlgorithmStep;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

@Component("radixSort")
public class RadixSort implements SortingAlgorithm {
    @Override
    public void sort(List<Integer> input, Consumer<AlgorithmStep> stepPublisher) {
        List<Integer> arr = new ArrayList<>(input);
        int max = Collections.max(arr);
        int exp = 1;

        while (max / exp > 0) {
            countingSortByDigit(arr, exp, stepPublisher);
            exp *= 10;
        }
    }

    private void countingSortByDigit(List<Integer> arr, int exp, Consumer<AlgorithmStep> stepPublisher) {
        int n = arr.size();
        int[] output = new int[n];
        int[] count = new int[10];

        for (int i = 0; i < n; i++) {
            count[(arr.get(i) / exp) % 10]++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr.get(i) / exp) % 10;
            output[count[digit] - 1] = arr.get(i);
            count[digit]--;
        }

        for (int i = 0; i < n; i++) {
            arr.set(i, output[i]);
            stepPublisher.accept(new AlgorithmStep(new ArrayList<>(arr), List.of(i)));
        }
    }
}