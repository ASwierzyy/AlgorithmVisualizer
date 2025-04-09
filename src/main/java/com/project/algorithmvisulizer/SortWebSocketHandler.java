package com.project.algorithmvisulizer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.algorithmvisulizer.DTOs.AlgorithmRequest;
import com.project.algorithmvisulizer.DTOs.AlgorithmStepWithId;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.List;

@Component
public class SortWebSocketHandler extends TextWebSocketHandler {
    private final AlgorithmService algorithmService;
    private final ObjectMapper objectMapper;

    public SortWebSocketHandler(AlgorithmService algorithmService, ObjectMapper objectMapper) {
        this.algorithmService = algorithmService;
        this.objectMapper = objectMapper;
    }
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            JsonNode node = objectMapper.readTree(message.getPayload());
            if (node.has("algorithms")) {
                List<String> algorithms = objectMapper.convertValue(node.get("algorithms"), List.class);
                List<Integer> array = objectMapper.convertValue(node.get("array"), List.class);

                algorithmService.executeRace(algorithms, array, (algorithmName, step) -> {
                    synchronized (session) {
                        try {
                            if (step != null) {
                                AlgorithmStepWithId wrapped = new AlgorithmStepWithId(algorithmName, step);
                                String json = objectMapper.writeValueAsString(wrapped);
                                if (session.isOpen()) {
                                    session.sendMessage(new TextMessage(json));
                                }
                            } else {
                                String doneJson = String.format("{\"algorithm\": \"%s\", \"done\": true}", algorithmName);
                                if (session.isOpen()) {
                                    session.sendMessage(new TextMessage(doneJson));
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else if (node.has("algorithm")) {
                AlgorithmRequest request = objectMapper.treeToValue(node, AlgorithmRequest.class);
                algorithmService.execute(request.algorithm(), request.array(), step -> {
                    try {
                        if (session.isOpen()) {
                            String json = objectMapper.writeValueAsString(step);
                            session.sendMessage(new TextMessage(json));
                            Thread.sleep(25);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                session.sendMessage(new TextMessage("{\"done\":true}"));
            } else {
                session.sendMessage(new TextMessage("{\"error\": \"Invalid request format\"}"));
            }
        } catch (Exception e) {
            try {
                session.sendMessage(new TextMessage("{\"error\": \"Internal server error\"}"));
            } catch (Exception ignored) {}
            e.printStackTrace();
        }
    }
}
