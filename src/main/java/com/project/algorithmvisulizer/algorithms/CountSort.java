package com.project.algorithmvisulizer.algorithms;

import com.project.algorithmvisulizer.DTOs.AlgorithmStep;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

@Component("countSort")
public class CountSort implements SortingAlgorithm {
    @Override
    public void sort(List<Integer> input, Consumer<AlgorithmStep> stepPublisher) {
        if (input.isEmpty()) return;

        List<Integer> arr = new ArrayList<>(input);
        int max = Collections.max(arr);

        int[] count = new int[max + 1];

        for (int i = 0; i < arr.size(); i++) {
            count[arr.get(i)]++;
            stepPublisher.accept(new AlgorithmStep(new ArrayList<>(arr), List.of(i)));
        }

        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        int[] output = new int[arr.size()];
        for (int i = arr.size() - 1; i >= 0; i--) {
            int value = arr.get(i);
            output[--count[value]] = value;
            stepPublisher.accept(new AlgorithmStep(toList(output, arr.size()), List.of(count[value])));
        }

        for (int i = 0; i < arr.size(); i++) {
            arr.set(i, output[i]);
            stepPublisher.accept(new AlgorithmStep(new ArrayList<>(arr), List.of(i)));
        }
    }

    private List<Integer> toList(int[] arr, int size) {
        List<Integer> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(arr[i]);
        }
        return list;
    }
}