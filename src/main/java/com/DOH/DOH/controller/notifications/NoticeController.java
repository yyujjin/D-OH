package com.DOH.DOH.controller.notifications;

import com.DOH.DOH.dto.notifications.NoticeDTO;
import com.DOH.DOH.service.notifications.NoticeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("notice")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    // 공통적으로 userEmail과 userRole을 Model에 추가하는 메서드
    @ModelAttribute
    public void addUserDetails(Model model, Principal principal) {
        String userEmail = principal != null ? principal.getName() : "anonymousUser";
        String userRole = "ROLE_ANONYMOUS";

        // Spring Security에서 현재 사용자 역할 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            userRole = authentication.getAuthorities().iterator().next().getAuthority();
        }

        model.addAttribute("userEmail", userEmail);
        model.addAttribute("userRole", userRole);
    }

    // 공지사항 목록 페이지
    @GetMapping("/list")
    public String noticeList(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
        int totalPages = noticeService.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        noticeService.getNoticeList(page, model);
        return "notifications/noticeList";
    }

    // 공지사항 상세 조회
    @GetMapping("/detail")
    public String noticeDetail(@RequestParam(name = "noticeNum", defaultValue = "0") int noticeNum, Model model) {
        if (noticeNum <= 0) {
            return "redirect:/notice/list";  // 목록 페이지로 리다이렉트
        }
        NoticeDTO notice = noticeService.getNoticeDetail(noticeNum);
        model.addAttribute("notice", notice);

        return "notifications/noticeDetail";
    }
}
