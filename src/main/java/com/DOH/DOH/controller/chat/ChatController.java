package com.DOH.DOH.controller.chat;

import com.DOH.DOH.dto.chat.ChatRoomDTO;
import com.DOH.DOH.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ChatRoomDTO createRoom(@RequestParam String name) {
        return chatService.createRoom(name);
    }

    @GetMapping
    public List<ChatRoomDTO> findAllRoom() {
        return chatService.findAllRoom();
    }
}
