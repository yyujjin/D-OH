package com.DOH.DOH.service.chat;

import com.DOH.DOH.dto.chat.MessageDTO;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    // 메시지 저장
    public MessageDTO saveMessage(MessageDTO messageDto) {
        //todo : 메시지 db에 저장하는 로직 작성하기

        return messageDto;
    }
}
