package com.DOH.DOH.service.chat;

import com.DOH.DOH.dto.chat.MessageDTO;
import com.DOH.DOH.mapper.chat.ChatMapper;
import org.springframework.stereotype.Service;
import java.util.List;

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
    public List<MessageDTO>getMessages(String receiver){return chatMapper.getMessages(receiver);}
}
