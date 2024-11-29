package com.ShopLive.websocket;

import com.ShopLive.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class BidWebSocketHandler extends TextWebSocketHandler {
    private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("New WebSocket connection established: " + session.getId());
    }

    public void broadCastBidUpdate(String message){
        for(WebSocketSession session : sessions){
            try {
                session.sendMessage(new TextMessage(message));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}

//A WebSocket handler to send real-time updates to connected clients.