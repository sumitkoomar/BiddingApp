package com.ShopLive.websocket;

import com.ShopLive.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PingMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Scope("singleton")
public class ProductWebSocketHandler extends TextWebSocketHandler {
    private ConcurrentLinkedQueue<WebSocketSession> sessions = new ConcurrentLinkedQueue<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        // Send a ping message to keep the connection alive
//        session.sendMessage(new PingMessage());
        System.out.println("New WebSocket connection established: " + session.getId());
        System.out.println("Session added. Current session count: " + sessions.size());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("WebSocket session closed: " + session.getId() + ", Status: " + status);
        sessions.remove(session);
        System.out.println("Session removed. Current session count: " + sessions.size());
    }

    public void broadcastProductUpdate(Product product) {
        System.out.println("Broadcasting product: " + product.getName());

        try {
            String productJson = new ObjectMapper().writeValueAsString(product);
            TextMessage textMessage = new TextMessage(productJson);

            // Temporary list to track problematic sessions
            List<WebSocketSession> sessionsToRemove = new ArrayList<>();

            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    try {
                        System.out.println("Sending message to session: " + session.getId());
                        session.sendMessage(textMessage);
                    } catch (IOException e) {
                        System.out.println("Error sending message to session: " + session.getId());
                        e.printStackTrace();
                        sessionsToRemove.add(session); // Mark session for removal
                    }
                } else {
                    System.out.println("Session " + session.getId() + " is closed. Marking for removal.");
                    sessionsToRemove.add(session); // Mark session for removal
                }
            }

            // Remove problematic sessions after broadcasting attempt
            sessions.removeAll(sessionsToRemove);
            System.out.println("Current active session count: " + sessions.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


