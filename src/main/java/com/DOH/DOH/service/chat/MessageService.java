package com.DOH.DOH.service.chat;

import com.DOH.DOH.dto.chat.MessageDTO;
import com.DOH.DOH.mapper.chat.ChatMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<MessageDTO>getUnreadMessages(String receiver){return chatMapper.getMessages(receiver);}

    //메시지 sender별 분류 후 그룹 화
    public Map<String,List<MessageDTO>> groupMessagesBySender (List<MessageDTO> unreadMessagesList) {
       return unreadMessagesList.stream()
                .collect(Collectors.groupingBy(MessageDTO::getSender));
    }
}
