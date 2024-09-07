package com.DOH.DOH.service.chat;

import com.DOH.DOH.dto.chat.ChatRoomDTO;

import java.util.List;
import java.util.UUID;

public class ChatRoomService {

    //채티방 생성
    public ChatRoomDTO createChatRoom(ChatRoomDTO chatRoomDTO){

        //채팅방 이름은 사용안하고 dto 받아서 id만 생성해주고 리턴시키기
        //참여자 목록은 클라이언트에서 넘어옴
        String roomId = UUID.randomUUID().toString();
        chatRoomDTO.setRoomId(roomId);

        //TODO:채팅방 정보를 DB에 저장하는 로직 추가하기

        return chatRoomDTO;
    }


}
