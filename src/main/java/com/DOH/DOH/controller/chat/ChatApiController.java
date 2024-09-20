package com.DOH.DOH.controller.chat;

import com.DOH.DOH.dto.chat.MessageDTO;
import com.DOH.DOH.service.chat.ChatService;
import com.DOH.DOH.service.user.UserSessionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/users/chat")
public class ChatApiController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;
    private final ChatPageController chatPageController;
    private final UserSessionService userSessionService;

    public ChatApiController(SimpMessagingTemplate messagingTemplate, ChatService chatService, ChatPageController chatPageController, UserSessionService userSessionService) {
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
        this.chatPageController = chatPageController;
        this.userSessionService = userSessionService;
    }

    //채팅 메시지 전송
    @MessageMapping("/messages")
    // WebSocket을 통해 /app/message 경로로 메시지를 받음
    public MessageDTO sendMessage(MessageDTO messageDTO) throws JsonProcessingException {

        chatService.saveMessage(messageDTO);
        //세션이 있다면 바로 구독 경로로 보내고 없다면 디비에 저장하는 로직 추가

        String messageAsJson = new ObjectMapper().writeValueAsString(messageDTO.getContent());
        messagingTemplate.convertAndSend("/queue/messages/"+ messageDTO.getReceiver(), messageAsJson);

        return messageDTO;

    }

    //읽지 않은 메시지 조회
    @GetMapping("/messages/unread")
    public Map<String,List<MessageDTO>> getUnreadMessages() {
        String  userNickName = userSessionService.nickName();

        if ("anonymousUser".equals( userNickName)) {
            // 빈 리스트 반환 또는 상태 코드를 명확하게 설정하는 것이 좋음
            return Collections.emptyMap(); // 빈 리스트 반환
        }

        List<MessageDTO>getUnreadMessages = chatService.getUnreadMessages(userNickName);

        return  chatService.groupMessagesBySender(getUnreadMessages);
    }

    //전체 메시지 가져오기
    @GetMapping("/messages")
    public List<MessageDTO> getAllMessages(@RequestBody MessageDTO messageDTO) {

        return  chatService.getAllMessages(messageDTO);
    }

    // 로그인 상태 확인
    @GetMapping("/isLoggedIn")
    public boolean isLoggedIn() {
        if ("anonymousUser".equals(userSessionService.userEmail())) {
            return false;
        }
        return true;
    }

    // 특정 사용자와의 메시지 조회
    @PostMapping("/messages/{userNickName}")
    public ResponseEntity<Map<String, List<MessageDTO>>> getMessagesByUserId(
            @PathVariable String userNickName,
            @RequestBody MessageDTO messageDTO) {

        Map<String, List<MessageDTO>> messages = chatService.filterMessagesBySenderAndReceiver( userNickName, messageDTO);
        //메시지 읽음 처리
        chatService.setMessageAsRead(messageDTO);

        return ResponseEntity.ok(messages);
    }

    @PatchMapping("/messages/delete")
    public ResponseEntity deleteMessages(@RequestBody MessageDTO messageDTO) {
        chatService.deleteMessages(messageDTO);
        return ResponseEntity.ok().build();
    }
}
