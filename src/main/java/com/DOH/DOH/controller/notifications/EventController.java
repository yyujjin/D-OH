package com.DOH.DOH.controller.notifications;

import com.DOH.DOH.dto.notifications.EventDTO;
import com.DOH.DOH.service.notifications.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    // 이벤트 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<EventDTO> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    // 이벤트 세부 내용 조회
    @GetMapping("/{eventNum}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable("eventNum") int eventNum) {
        EventDTO event = eventService.getEventById(eventNum);
        if (event != null) {
            return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 이벤트 등록
    @PostMapping("/write")
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO) {
        EventDTO createdEvent = eventService.createEvent(eventDTO);
        return ResponseEntity.ok(createdEvent);
    }

    // 이벤트 수정
    @PutMapping("/{eventNum}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable("eventNum") int eventNum, @RequestBody EventDTO eventDTO) {
        EventDTO updatedEvent = eventService.updateEvent(eventNum, eventDTO);
        if (updatedEvent != null) {
            return ResponseEntity.ok(updatedEvent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 이벤트 삭제
    @DeleteMapping("/{eventNum}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("eventNum") int eventNum) {
        if (eventService.deleteEvent(eventNum)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
