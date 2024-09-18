package com.DOH.DOH.mapper.chat;

import com.DOH.DOH.dto.chat.MessageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {

    //메시지 저장
    void saveMessage(MessageDTO messageDTO);

    //안읽은 메시지 불러오기
    List<MessageDTO> getUnreadMessages(String receiver);

    //저장된 전체 메시지 불러오기
    List<MessageDTO>allMessages(MessageDTO messageDTO);

}
