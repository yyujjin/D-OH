package com.DOH.DOH.handler.chat;

import com.DOH.DOH.dto.chat.ChatMessageDTO;
import com.DOH.DOH.dto.chat.ChatRoomDTO;
import com.DOH.DOH.service.chat.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketChatHandler extends TextWebSocketHandler{

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}",payload);
        ChatMessageDTO chatMessageDTO = objectMapper.readValue(payload,ChatMessageDTO.class);
        ChatRoomDTO chatRoomDTO = chatService.findRoomById(chatMessageDTO.getRoomId());
        chatRoomDTO.handleActions(session,chatMessageDTO,chatService);
    }
}