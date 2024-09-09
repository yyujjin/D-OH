package com.DOH.DOH.controller.notifications;

import com.DOH.DOH.dto.notifications.EventDTO;
import com.DOH.DOH.service.notifications.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("event")
public class EventController {

    @Autowired
    EventService eventService;

    // 이벤트 목록 조회
    @GetMapping("/list")
    public String eventList(Model model, @RequestParam(required = false, defaultValue = "1") int page) {
        eventService.getEventList(page, model);
        return "notifications/eventList";
    }

    // 이벤트 작성 페이지 이동
    @GetMapping("/write")
    public String showEventWritePage() {
        return "notifications/eventWrite";  // 이벤트 작성 페이지로 이동
    }

    // 이벤트 등록
    @PostMapping("/create")
    public String createEvent(@ModelAttribute EventDTO eventDTO) {
        eventService.writeEvent(eventDTO);
        return "redirect:/event/list";  // 작성 후 목록으로 리다이렉트
    }

    // 이벤트 임시 저장
    @PostMapping("/save")
    public String saveEvent(@ModelAttribute EventDTO eventDTO) {
        eventDTO.setEventTempSave(true);
        eventService.writeEvent(eventDTO);  // 임시 저장 후 목록으로 리다이렉트
        return "redirect:/event/list";
    }

    // 이벤트 삭제
    @PostMapping("/delete")
    public String deleteEvent(@RequestParam("eventNum") int eventNum) {
        eventService.deleteEvent(eventNum);
        return "redirect:/event/list";  // 삭제 후 목록으로 리다이렉트
    }
}
