package com.DOH.DOH.service.notifications;

import com.DOH.DOH.dto.notifications.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EventDTO getEventById(int id) {
        Optional<Event> event = eventRepository.findById(id);
        return event.map(this::convertToDTO).orElse(null);
    }

    @Override
    public EventDTO createEvent(EventDTO eventDTO) {
        Event event = convertToEntity(eventDTO);
        event = eventRepository.save(event);
        return convertToDTO(event);
    }

    @Override
    public EventDTO updateEvent(int id, EventDTO eventDTO) {
        if (eventRepository.existsById(id)) {
            Event event = convertToEntity(eventDTO);
            event.setEventNum(id);
            event = eventRepository.save(event);
            return convertToDTO(event);
        }
        return null;
    }

    @Override
    public boolean deleteEvent(int id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private EventDTO convertToDTO(Event event) {
        EventDTO dto = new EventDTO();
        dto.setEventNum(event.getEventNum());
        dto.setEventTitle(event.getEventTitle());
        dto.setEventContent(event.getEventContent());
        dto.setEventCreateTime(event.getEventCreateTime());
        dto.setEventImageName(event.getEventImageName());
        dto.setEventImageUrl(event.getEventImageUrl());
        dto.setUserNum(event.getUserNum());
        dto.setEventTempSave(event.isEventTempSave());
        return dto;
    }

    private Event convertToEntity(EventDTO dto) {
        Event event = new Event();
        event.setEventNum(dto.getEventNum());
        event.setEventTitle(dto.getEventTitle());
        event.setEventContent(dto.getEventContent());
        event.setEventCreateTime(dto.getEventCreateTime());
        event.setEventImageName(dto.getEventImageName());
        event.setEventImageUrl(dto.getEventImageUrl());
        event.setUserNum(dto.getUserNum());
        event.setEventTempSave(dto.isEventTempSave());
        return event;
    }
}