package com.DOH.DOH.controller.notifications;

import com.DOH.DOH.dto.notifications.EventDTO;
import com.DOH.DOH.service.notifications.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
    public ResponseEntity<List<EventDTO>> getAllEvents(@RequestParam(defaultValue = "1") int page) {
        // Model을 사용하지 않고 List<EventDTO>를 반환하도록 수정
        List<EventDTO> events = eventService.getAllEvents(page);
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
        eventService.writeEvent(eventDTO); // 작성 메소드 호출
        return ResponseEntity.ok(eventDTO);
    }

    // 이벤트 수정
    @PutMapping("/{eventNum}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable("eventNum") int eventNum, @RequestBody EventDTO eventDTO) {
        eventDTO.setId(eventNum); // eventDTO에 id를 설정
        eventService.updateEvent(eventDTO); // 수정 메소드 호출
        return ResponseEntity.ok(eventDTO);
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
