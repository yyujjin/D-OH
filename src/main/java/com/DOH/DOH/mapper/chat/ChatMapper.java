package com.DOH.DOH.mapper.chat;

import com.DOH.DOH.dto.chat.MessageDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMapper {

    public void saveMessage(MessageDTO messageDTO);

}
