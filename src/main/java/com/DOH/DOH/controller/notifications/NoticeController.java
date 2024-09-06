package com.DOH.DOH.controller.notifications;

import com.DOH.DOH.dto.notifications.NoticeDTO;
import com.DOH.DOH.service.notifications.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("notice")
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    // 공지사항 목록 페이지
    @GetMapping("/list")
    public String noticeList(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
        int totalPages = noticeService.getTotalPages();  // 전체 페이지 수 계산
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        noticeService.getNoticeList(page, model);  // 공지사항 목록 조회
        return "notifications/noticeList";
    }

    // 공지사항 작성 페이지
    @GetMapping("/write")
    public String noticeWrite(Model model) {
        // 초기값이 필요하면 모델에 추가 가능 (예: 현재 날짜)
        model.addAttribute("currentDate", new java.util.Date());
        return "notifications/noticeWrite";
    }

    // 공지사항 등록 처리
    @PostMapping("/register")
    public String registerNotice(@ModelAttribute NoticeDTO noticeDTO, Model model) {
        log.info("공지사항 등록: {}", noticeDTO);

        // 임시 저장 여부에 따른 처리
        if (noticeDTO.isNoticeTempSave()) {
            // 임시 저장 로직
            noticeService.saveTempNotice(noticeDTO);
        } else {
            // 정식 공지 등록 처리 로직
            noticeService.saveNotice(noticeDTO);
        }

        // 공지사항 목록 페이지로 리디렉션
        return "redirect:/notice/list";
    }
}
