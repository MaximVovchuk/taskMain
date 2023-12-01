package com.example.infinispantask;

import com.example.infinispantask.entities.Department;
import com.example.infinispantask.ws.ChatWebsocketEndpoint;
import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryModified;
import org.infinispan.notifications.cachelistener.event.CacheEntryModifiedEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Listener(clustered = true)
@Component
public class CacheListener {
    private final ChatWebsocketEndpoint chatWebsocketEndpoint;

    public CacheListener(@Lazy ChatWebsocketEndpoint chatWebsocketEndpoint) {
        this.chatWebsocketEndpoint = chatWebsocketEndpoint;
    }

    @CacheEntryModified
    public void modified(CacheEntryModifiedEvent<Long, Department> event) {
        if (event.getNewValue().getId() == 2L) {
            System.out.println("Cache entry modified: " + event.getNewValue().toString());
            chatWebsocketEndpoint.sendUpdate("Modified info: " + event.getNewValue().toString());
        }
    }
}
