package com.DOH.DOH.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDTO {
    public enum MessageType{
        ENTER, TALK
    }

    private MessageType type; //메시지 타입
    private String roomId; //방 번호
    private String sender; //메시지 보낸 사람
    private String message; //메시지
}
