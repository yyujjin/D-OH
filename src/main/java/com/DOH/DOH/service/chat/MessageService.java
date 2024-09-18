package com.DOH.DOH.service.chat;

import com.DOH.DOH.dto.chat.MessageDTO;
import com.DOH.DOH.mapper.chat.ChatMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MessageService {

    private final ChatMapper chatMapper;

    public MessageService(ChatMapper chatMapper) {
        this.chatMapper = chatMapper;
    }

    // 메시지 저장
    public void saveMessage(MessageDTO messageDto) {
        chatMapper.saveMessage(messageDto);
    }

    //메시지 불러오기
    public List<MessageDTO>getUnreadMessages(String receiver){return chatMapper.getUnreadMessages(receiver);}

    //메시지 sender별 분류 후 그룹 화
    public Map<String,List<MessageDTO>> groupMessagesBySender (List<MessageDTO> unreadMessagesList) {
       return unreadMessagesList.stream()
                .collect(Collectors.groupingBy(MessageDTO::getSender));
    }

    //전체 메시지 가져오기
    public List<MessageDTO> allMessages (MessageDTO messageDTO){
        log.info("가져온 메시지 : "+chatMapper.allMessages(messageDTO));
        return chatMapper.allMessages(messageDTO);
    }

    //sender, receiver로 메시지 분류 해서 프론트로 내보내기
    public Map<String, List<MessageDTO>> filterMessagesBySenderAndReceiver(String userId,MessageDTO messageDTO) {
        List<MessageDTO> senderMessageList = new ArrayList<>();
        List<MessageDTO> receiverMessageList = new ArrayList<>();

        // 메시지 리스트 순회하면서 sender와 receiver로 구분
        for (MessageDTO message : allMessages(messageDTO)) {
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
}
