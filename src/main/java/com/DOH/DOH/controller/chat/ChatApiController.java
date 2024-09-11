package com.DOH.DOH.controller.chat;

import com.DOH.DOH.dto.chat.ChatRoomDTO;
import com.DOH.DOH.dto.chat.MessageDTO;
import com.DOH.DOH.service.chat.ChatRoomService;
import com.DOH.DOH.service.chat.MessageService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
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
    private final ChatPageController chatPageController;

    public ChatApiController(SimpMessagingTemplate messagingTemplate, ChatRoomService chatRoomService, MessageService messageService, ChatPageController chatPageController) {
        this.messagingTemplate = messagingTemplate;
        this.chatRoomService = chatRoomService;
        this.messageService = messageService;
        this.chatPageController = chatPageController;
    }

@Autowired
    HttpSession httpSession;

    //채팅방 입장
    @PostMapping("/room")
    public ResponseEntity createChatRoom(@RequestBody ChatRoomDTO chatRoomDTO){
        //todo:chatDTO 받아서 Is채팅방 있는지. 서비스 로직만들기
        //있으면 그 채팅방 아이디 저장
        //없으면 생성하고 아이디 저장
        //이거 반환값 없음
        log.info("넘어온DTO:{}",chatRoomDTO);
        if (chatRoomService.findChatRoomByRoomId(chatRoomDTO).isEmpty()){
            chatRoomService.createChatRoom(chatRoomDTO);
        }

        return ResponseEntity.ok().build();
    }

    //채팅 메시지 전송
    @MessageMapping("/message")
    @SendToUser("/queue/messages")
    // WebSocket을 통해 /app/message 경로로 메시지를 받음
    public MessageDTO sendMessage(MessageDTO messageDTO, HttpSession httpSession) {

        String user = (String) httpSession.getAttribute("userId");
        log.info("로그이ㅣㅣㅣㅣㄴ :{}",user);

        messageDTO.setSender(user);
        messageDTO.setReceiver("aaaa");
        messagingTemplate.convertAndSendToUser(messageDTO.getReceiver(),"/queue/messages",messageDTO.getContent());


        log.info("받은 메시지 :{}",messageDTO.getContent());
        /*// 메시지를 DB에 저장
        messageService.saveMessage(messageDto);*/

        log.info("수신자 : {}",messageDTO.getReceiver());
        // 메시지를 해당 수신자에게 전송 (WebSocket)



        return messageDTO;

    }
}

//자기 자신도 그 경로를 구독하고 있어서