package com.DOH.DOH.service.chat;

import com.DOH.DOH.dto.chat.MessageDTO;
import com.DOH.DOH.mapper.chat.ChatMapper;
import com.DOH.DOH.service.user.UserSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChatService {

    private final ChatMapper chatMapper;
    private final UserSessionService userSessionService;

    public ChatService(ChatMapper chatMapper, UserSessionService userSessionService) {
        this.chatMapper = chatMapper;
        this.userSessionService = userSessionService;
    }

    // 메시지 저장
    public void saveMessage(MessageDTO messageDto) {
        chatMapper.saveMessage(messageDto);
    }

    //안 읽은 메시지 불러오기
    public List<MessageDTO>getUnreadMessages(String receiver){
        List<MessageDTO> unreadMessages = chatMapper.getUnreadMessages(receiver);
       //sender 유저 (로그인한 유저에게 메시지를 보낸) 의 프로필 사진 가져오기
        for(MessageDTO dto :unreadMessages ){
            dto.setProfilePhoto(chatMapper.getProfilePhoto(dto.getSender()));
        }
        return unreadMessages;
    }

    //메시지 sender별 분류 후 그룹화
    public Map<String,List<MessageDTO>> groupMessagesBySender (List<MessageDTO> unreadMessagesList) {
       return unreadMessagesList.stream()
                .collect(Collectors.groupingBy(MessageDTO::getSender));
    }

    //전체 메시지 가져오기
    public List<MessageDTO> getAllMessages(MessageDTO messageDTO){
        log.info("가져온 메시지 : "+chatMapper.getAllMessages(messageDTO));
        return chatMapper.getAllMessages(messageDTO);
    }

    //sender, receiver로 메시지 분류 해서 프론트로 내보내기
    public Map<String, List<MessageDTO>> filterMessagesBySenderAndReceiver(String userId,MessageDTO messageDTO) {
        List<MessageDTO> senderMessageList = new ArrayList<>();
        List<MessageDTO> receiverMessageList = new ArrayList<>();

        // 메시지 리스트 순회하면서 sender와 receiver로 구분
        for (MessageDTO message : getAllMessages(messageDTO)) {
            //sender은 자기 자신으로 설정해뒀음 JS에
            if (message.getSender().equals(userId)) {
                senderMessageList.add(message);
            } else {
                receiverMessageList.add(message);
            }
        }

        log.info("sned메시지 "+ senderMessageList);
        log.info("recieve메시지" + receiverMessageList);

        // 두 리스트를 Map에 담아 반환
        Map<String, List<MessageDTO>> result = new HashMap<>();
        result.put("sentMessages", senderMessageList);  // sender 메시지 리스트
        result.put("receivedMessages", receiverMessageList);  // receiver 메시지 리스트

        return result;
    }
    //메시지 읽음 처리
    public void setMessageAsRead(MessageDTO messageDTO) {
        chatMapper.setMessageAsRead(messageDTO);
    }

    //메시지 삭제
    public void deleteMessages(MessageDTO messageDTO) {chatMapper.deleteMessages(messageDTO);}

    //로그인한 유저를 기준으로 다른 유저가 보낸 최신 메시지들을 조회
    public List<MessageDTO>findLatestMessagesForLoggedInUser (String userNickName) {
      return chatMapper.findLatestMessagesForLoggedInUser(userNickName);
    };
}
