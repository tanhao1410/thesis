package com.github.tanhao1410.thesis.management.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class WebSocketHandler extends TextWebSocketHandler {

    ///用来存储所有登录的用户的，当一个用户在多处登录？key用 userid不行！
    private static final HashMap<Long, List<WebSocketSession>> userMap;

    static {
        userMap = new HashMap<Long, List<WebSocketSession>>();
    }

    public WebSocketHandler() {
    }

    /**
     * 连接成功时候，会触发页面上onopen方法
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        final URI uri = session.getUri();
        final String rawQuery = uri.getRawQuery();
        if (rawQuery != null && rawQuery.contains("=")) {
            String userId = rawQuery.split("=")[1];

            if (userMap.containsKey(Long.parseLong((String) userId))) {
                final List<WebSocketSession> userSessions = userMap.get(Long.parseLong((String) userId));
                userSessions.add(session);
            } else {
                List<WebSocketSession> userSessions = new ArrayList<>();
                userSessions.add(session);
                userMap.put(Long.parseLong((String) userId), userSessions);
            }
            System.out.println("websocket用户加入，用户id:" + (String) userId);

        }

    }

    /**
     * 关闭连接时触发
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        final URI uri = session.getUri();
        final String rawQuery = uri.getRawQuery();
        if (rawQuery != null && rawQuery.contains("=")) {
            String userId = rawQuery.split("=")[1];
            if (userMap.containsKey(Long.parseLong((String) userId))) {
                final List<WebSocketSession> userSessions = userMap.get(Long.parseLong((String) userId));
                userSessions.remove(session);
            }
            System.out.println("websocket用户退出，用户id:" + (String) userId);
        }
    }

    /**
     * js调用websocket.send时候，会调用该方法
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
    }

    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        final URI uri = session.getUri();
        final String rawQuery = uri.getRawQuery();
        if (rawQuery != null && rawQuery.contains("=")) {
            String userId = rawQuery.split("=")[1];
            if (userMap.containsKey(Long.parseLong((String) userId))) {
                final List<WebSocketSession> userSessions = userMap.get(Long.parseLong((String) userId));
                userSessions.remove(session);
            }
            System.out.println("websocket用户退出，用户id:" + (String) userId);
        }
    }

    /**
     * 给指定在线用户发送消息
     */
    public void sendMessageToUsers(TextMessage message, Set<Long> userIds) {
        userMap.forEach((userId, sessions) -> {
            if (userIds.contains(userId)) {
                final List<WebSocketSession> userSessions = userMap.get(userId);
                for (WebSocketSession session : userSessions) {
                    try {
                        if (session.isOpen()) {
                            session.sendMessage(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public boolean supportsPartialMessages() {
        return false;
    }


}