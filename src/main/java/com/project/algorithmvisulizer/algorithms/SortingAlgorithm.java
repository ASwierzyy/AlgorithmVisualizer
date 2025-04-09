package com.project.algorithmvisulizer.algorithms;

import com.project.algorithmvisulizer.DTOs.AlgorithmStep;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;
@Component
public interface SortingAlgorithm {
    void sort(List<Integer> input, Consumer<AlgorithmStep> publisher);
}
