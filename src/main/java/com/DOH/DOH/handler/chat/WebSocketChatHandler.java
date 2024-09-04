package com.DOH.DOH.handler.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;



@Component
public class WebSocketChatHandler extends TextWebSocketHandler{

    private static final Logger log = LoggerFactory.getLogger(WebSocketChatHandler.class);

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}",payload);
        TextMessage textMessage = new TextMessage("Welcom welcom!!!!!");
        session.sendMessage(textMessage);
    }
}
