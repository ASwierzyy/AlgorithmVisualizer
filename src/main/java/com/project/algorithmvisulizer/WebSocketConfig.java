package com.project.algorithmvisulizer;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final SortWebSocketHandler sortWebSocketHandler;

    public WebSocketConfig(SortWebSocketHandler sortWebSocketHandler) {
        this.sortWebSocketHandler = sortWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(sortWebSocketHandler, "/ws/sort").setAllowedOrigins("*");
    }
}
