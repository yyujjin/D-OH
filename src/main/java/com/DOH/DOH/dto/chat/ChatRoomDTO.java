package com.DOH.DOH.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomDTO {
    private String roomId; //채팅방 ID
    private String client; //의뢰인
    private String creator; //제작자
}
