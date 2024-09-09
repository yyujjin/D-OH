package com.DOH.DOH.service.chat;

import com.DOH.DOH.dto.chat.MessageDto;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    // 메시지 저장
    public MessageDto saveMessage(MessageDto messageDto) {
        //todo : 메시지 db에 저장하는 로직 작성하기

        return messageDto;
    }
}
