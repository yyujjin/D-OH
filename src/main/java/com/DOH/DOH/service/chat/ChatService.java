package com.DOH.DOH.service.chat;

import com.DOH.DOH.dto.chat.ChatRoomDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ObjectMapper objectMapper;
    private Map<String, ChatRoomDTO> chatRooms;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<ChatRoomDTO> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoomDTO findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    public ChatRoomDTO createRoom(String name) {
        String randomId = UUID.randomUUID().toString();
        ChatRoomDTO chatRoomDTO = ChatRoomDTO.builder()
                .roomId(randomId)
                .name(name)
                .build();
        chatRooms.put(randomId, chatRoomDTO);
        log.info("실행됨:{}",randomId,"chatRoomDTO :{}",chatRoomDTO);
        return chatRoomDTO;
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage((objectMapper.writeValueAsString(message))));
        }catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
