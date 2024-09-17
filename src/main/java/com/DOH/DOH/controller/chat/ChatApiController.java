package com.DOH.DOH.controller.chat;

import com.DOH.DOH.dto.chat.MessageDTO;
import com.DOH.DOH.service.chat.MessageService;
import com.DOH.DOH.service.user.UserSessionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/chat")
public class ChatApiController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;
    private final ChatPageController chatPageController;
    private final UserSessionService userSessionService;

    public ChatApiController(SimpMessagingTemplate messagingTemplate, MessageService messageService, ChatPageController chatPageController, UserSessionService userSessionService) {
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
        this.chatPageController = chatPageController;
        this.userSessionService = userSessionService;
    }

    //채팅 메시지 전송
    @MessageMapping("/message")
    // WebSocket을 통해 /app/message 경로로 메시지를 받음
    public MessageDTO sendMessage(MessageDTO messageDTO) throws JsonProcessingException {

        log.info("수신자 : {}", messageDTO.getReceiver());
        log.info("보낸 메시지 : {}", messageDTO.getContent());

        messageService.saveMessage(messageDTO);
        //세션이 있다면 바로 구독 경로로 보내고 없다면 디비에 저장하는 로직 추가

        String messageAsJson = new ObjectMapper().writeValueAsString(messageDTO.getContent());
        messagingTemplate.convertAndSend("/queue/messages/"+ messageDTO.getReceiver(), messageAsJson);

        log.info("수신자 경로 : /queue/messages/" + messageDTO.getReceiver());

        return messageDTO;

    }

    @GetMapping("/messages/check")
    public Map<String,List<MessageDTO>> getMessages() {
        String userId = userSessionService.userEmail();
        log.info("가져와진 메시지"+messageService.getUnreadMessages(userId));

        if ("anonymousUser".equals(userId)) {
            // 빈 리스트 반환 또는 상태 코드를 명확하게 설정하는 것이 좋음
            return Collections.emptyMap(); // 빈 리스트 반환
        }

        List<MessageDTO>getUnreadMessages = messageService.getUnreadMessages(userId);

        return  messageService.groupMessagesBySender(getUnreadMessages);
    }

    @GetMapping("/isLoggedIn")
    public boolean isLoggedIn() {
        if ("anonymousUser".equals(userSessionService.userEmail())) {
            return false;
        }
        return true;
    }
}
