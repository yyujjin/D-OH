package com.DOH.DOH.service.notifications;

import com.DOH.DOH.dto.notifications.EventDTO;

import java.util.List;

public interface EventService {

    List<EventDTO> getAllEvents();

    EventDTO getEventById(int id);

    EventDTO createEvent(EventDTO eventDTO);

    EventDTO updateEvent(int id, EventDTO eventDTO);

    boolean deleteEvent(int id);
}
