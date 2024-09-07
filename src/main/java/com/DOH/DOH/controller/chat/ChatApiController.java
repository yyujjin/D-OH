package com.DOH.DOH.controller.chat;

import com.DOH.DOH.dto.chat.ChatRoomDTO;
import com.DOH.DOH.dto.chat.MessageDto;
import com.DOH.DOH.service.chat.ChatRoomService;
import com.DOH.DOH.service.chat.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/chat")
public class ChatApiController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomService chatRoomService;
    private final MessageService messageService;

    public ChatApiController(SimpMessagingTemplate messagingTemplate, ChatRoomService chatRoomService, MessageService messageService) {
        this.messagingTemplate = messagingTemplate;
        this.chatRoomService = chatRoomService;
        this.messageService = messageService;
    }


    //채팅방 생성
    @PostMapping("/room")
    public ChatRoomDTO createChatRoom(@RequestBody ChatRoomDTO chatRoomDTO){
        return chatRoomService.createChatRoom(chatRoomDTO);
    }

    //채팅 메시지 전송
    @MessageMapping("/message")  // WebSocket을 통해 /app/message 경로로 메시지를 받음
    public void sendMessage(MessageDto messageDto) {
        // 메시지를 DB에 저장
        messageService.saveMessage(messageDto);

        // 메시지를 해당 수신자에게 전송 (WebSocket)
        messagingTemplate.convertAndSendToUser(
                messageDto.getReceiver(),
                "/queue/messages" + messageDto.getRoomId(),
                messageDto
        );
    }

    //채팅방 찾기


}
