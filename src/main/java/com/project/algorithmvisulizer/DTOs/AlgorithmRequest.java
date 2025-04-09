package com.project.algorithmvisulizer.DTOs;

import java.util.List;

public record AlgorithmRequest(String algorithm, List<Integer> array) {
}
