package com.DOH.DOH.controller.notifications;

import com.DOH.DOH.dto.notifications.EventDTO;
import com.DOH.DOH.service.notifications.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("event")
public class EventController {

    @Autowired
    EventService eventService;

    // 이벤트 목록 조회
    @GetMapping("/list")
    public String eventList(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
        int totalPages = eventService.getTotalPages();  // 전체 페이지 수 계산

        // 이벤트 목록을 모델에 추가
        List<EventDTO> eventList = eventService.getEventList(page);
        model.addAttribute("eventList", eventList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        return "notifications/eventList";  // 이벤트 목록 페이지로 이동
    }

    // 이벤트 상세 조회
    @GetMapping("/detail")
    public String showEventDetail(@RequestParam(name = "eventNum", defaultValue = "0") int eventNum, Model model) {
        if (eventNum <= 0) {
            // 파라미터가 누락되었거나 유효하지 않은 경우 처리
            return "redirect:/event/list";  // 목록 페이지로 리다이렉트 또는 오류 페이지 표시
        }
        EventDTO event = eventService.getEventDetail(eventNum);
        model.addAttribute("event", event);
        return "notifications/eventView";
    }


    // 이벤트 작성 페이지 이동
    @GetMapping("/write")
    public String showEventWritePage(Model model) {
        model.addAttribute("currentDate", new java.util.Date());
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
