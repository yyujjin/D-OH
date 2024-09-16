package com.DOH.DOH.dto.chat;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageDTO {
    private String sender; //발신자
    private String receiver; //수신자
    private String content; //메시지 내용
    private LocalDateTime timestamp; //메시지 전송 시간
    private boolean isRead; //메시지 읽음 여부

    @Override
    public String toString() {
        return "MessageDTO{" +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", isRead=" + isRead +
                '}';
    }
}