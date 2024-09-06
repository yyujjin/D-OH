package com.DOH.DOH.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDTO {
    public enum MessageType{
        ENTER, TALK, LEAVE
    }

    private MessageType type; //메시지 타입
    private String sender; //보낸 사람
    private String receiver; // 받는 사람
    private String message; //메시지 내용
}