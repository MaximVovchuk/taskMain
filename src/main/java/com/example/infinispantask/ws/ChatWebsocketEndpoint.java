package com.example.infinispantask.ws;

import com.example.infinispantask.CacheService;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
@Component
@ServerEndpoint(value = "/chat")
public class ChatWebsocketEndpoint {
    private static CacheService cacheService;
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        sendUpdate(cacheService.getCache().toString());
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println(throwable.toString());
    }

    public void sendUpdate(String message) {
        sessions.forEach(session -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Autowired
    private void setCacheService(CacheService cacheService) {
        ChatWebsocketEndpoint.cacheService = cacheService;
    }
}