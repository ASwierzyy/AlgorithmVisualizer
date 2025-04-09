package com.project.algorithmvisulizer;
import com.project.algorithmvisulizer.DTOs.AlgorithmStep;
import com.project.algorithmvisulizer.algorithms.SortingAlgorithm;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AlgorithmService {
    private final Map<String, SortingAlgorithm> algorithms;

    public AlgorithmService(List<SortingAlgorithm> algorithmList) {
        this.algorithms = algorithmList.stream()
                .collect(Collectors.toMap(
                        algo -> algo.getClass().getAnnotation(Component.class).value(),
                        Function.identity()));
    }

    public void execute(String algorithmName, List<Integer> array, Consumer<AlgorithmStep> stepConsumer) {
        SortingAlgorithm algorithm = algorithms.get(algorithmName);
        if (algorithm == null) {
            throw new IllegalArgumentException("Unknown algorithm: " + algorithmName);
        }
        algorithm.sort(array, stepConsumer);
    }

    public void executeRace(
            List<String> algorithmNames,
            List<Integer> baseArray,
            BiConsumer<String, AlgorithmStep> stepPublisher
    ) {
        for (String algo : algorithmNames) {
            List<Integer> clonedArray = new ArrayList<>(baseArray);
            Thread.startVirtualThread(() -> {
                try {
                    Consumer<AlgorithmStep> delayedPublisher = step -> {
                        stepPublisher.accept(algo, step);
                        try {
                            Thread.sleep(25);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    };
                    execute(algo, clonedArray, delayedPublisher);
                    stepPublisher.accept(algo, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
