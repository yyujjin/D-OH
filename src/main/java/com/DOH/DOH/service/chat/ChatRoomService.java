package com.DOH.DOH.service.chat;

import com.DOH.DOH.dto.chat.ChatRoomDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ChatRoomService {

    public Optional<ChatRoomDTO> findChatRoomByRoomId(ChatRoomDTO chatRoomDTO){
       //todo:제작자, 의뢰자로 룸 아이디 찾기
        return Optional.ofNullable(chatRoomDTO);
    }

    //채티방 생성
    public void createChatRoom(ChatRoomDTO chatRoomDTO){
       String roomId = UUID.randomUUID().toString();

        //TODO:채팅방 정보를 DB에 저장하는 로직 추가하기
    }

}
