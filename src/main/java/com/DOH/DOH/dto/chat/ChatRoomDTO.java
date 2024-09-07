package com.DOH.DOH.dto.chat;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatRoomDTO {
    private String roomId; //채팅방 ID
    private String roomName; //채팅방 이름
    private List<String> participants; //채팅방에 참여하는 사용자 리스트

}
