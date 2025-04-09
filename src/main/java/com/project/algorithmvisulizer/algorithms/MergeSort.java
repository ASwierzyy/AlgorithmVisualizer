package com.project.algorithmvisulizer.algorithms;

import com.project.algorithmvisulizer.DTOs.AlgorithmStep;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Component("mergeSort")
public class MergeSort implements SortingAlgorithm{
    @Override
    public void sort(List<Integer> input, Consumer<AlgorithmStep> stepPublisher) {
        List<Integer> arr = new ArrayList<>(input);
        mergeSort(arr, 0, arr.size() - 1, stepPublisher);
    }

    private void mergeSort(List<Integer> arr, int left, int right, Consumer<AlgorithmStep> stepPublisher) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(arr, left, mid, stepPublisher);
            mergeSort(arr, mid + 1, right, stepPublisher);

            merge(arr, left, mid, right, stepPublisher);
        }
    }

    private void merge(List<Integer> arr, int left, int mid, int right, Consumer<AlgorithmStep> stepPublisher) {
        List<Integer> temp = new ArrayList<>(arr);

        int i = left, j = mid + 1, k = left;

        while (i <= mid && j <= right) {
            if (temp.get(i) <= temp.get(j)) {
                arr.set(k++, temp.get(i++));
            } else {
                arr.set(k++, temp.get(j++));
            }
            stepPublisher.accept(new AlgorithmStep(new ArrayList<>(arr), List.of(i - 1, j - 1)));
        }

        while (i <= mid) {
            arr.set(k++, temp.get(i++));
            stepPublisher.accept(new AlgorithmStep(new ArrayList<>(arr), List.of(i - 1)));
        }

        while (j <= right) {
            arr.set(k++, temp.get(j++));
            stepPublisher.accept(new AlgorithmStep(new ArrayList<>(arr), List.of(j - 1)));
        }
    }
}