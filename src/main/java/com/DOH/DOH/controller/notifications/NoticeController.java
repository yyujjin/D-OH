package com.DOH.DOH.controller.notifications;

import com.DOH.DOH.dto.notifications.NoticeDTO;
import com.DOH.DOH.service.notifications.NoticeService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String registerNotice(@ModelAttribute NoticeDTO noticeDTO, HttpSession session) {
        // 로그인된 사용자 정보 가져오기 (세션에서 사용자 ID나 번호를 가져온다고 가정)
        UserDTO loggedInUser = (UserDTO) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            // userNum 설정
            noticeDTO.setUserNum(loggedInUser.getUserNum());
        } else {
            // 로그인된 사용자가 없을 경우 처리
            throw new RuntimeException("로그인된 사용자가 없습니다.");
        }

        // 공지사항 저장 처리
        noticeService.saveNotice(noticeDTO);

        return "redirect:/notice/list";
    }
}
