package com.DOH.DOH.mapper.notifications;

import com.DOH.DOH.dto.notifications.EventDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventMapper {

    // Add pagination support by offset
    List<EventDTO> getEventList(@Param("offset") int offset);

    EventDTO getEventById(@Param("eventNum") int eventNum);

    void writeEvent(EventDTO eventDTO);

    void updateEvent(EventDTO eventDTO);

    void deleteEvent(@Param("eventNum") int eventNum);
}
