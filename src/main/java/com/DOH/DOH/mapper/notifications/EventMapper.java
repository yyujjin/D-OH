package com.DOH.DOH.mapper.notifications;

import com.DOH.DOH.dto.notifications.EventDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventMapper {

    List<EventDTO> getAllEvents();

    EventDTO getEventById(@Param("id") int id);

    void createEvent(EventDTO eventDTO);

    void updateEvent(@Param("id") int id, EventDTO eventDTO);

    void deleteEvent(@Param("id") int id);
}
