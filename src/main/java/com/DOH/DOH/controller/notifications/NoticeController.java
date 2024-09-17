package com.DOH.DOH.controller.notifications;

import com.DOH.DOH.dto.notifications.NoticeDTO;
import com.DOH.DOH.service.notifications.NoticeService;
import com.DOH.DOH.service.user.UserSessionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("notice")
public class NoticeController {

    private final NoticeService noticeService;
    private final UserSessionService userSessionService;

    public NoticeController(NoticeService noticeService, UserSessionService userSessionService) {
        this.noticeService = noticeService;
        this.userSessionService = userSessionService;
    }

    // 모든 메서드에서 userEmail을 Model에 추가
    @ModelAttribute
    public void addUserEmailToModel(Model model) {
        String userEmail = userSessionService.userEmail();
        String userRole = userSessionService.userRole();

        // 백업 로직: SecurityContextHolder에서 값을 가져옴
        if (userEmail == null || userEmail.isEmpty()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                userEmail = auth.getName(); // 사용자 이메일
                userRole = auth.getAuthorities().iterator().next().getAuthority(); // 사용자 역할
            }
        }

        // 사용자 이메일과 역할을 모델에 추가
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

    // 공지사항 작성 페이지
    @GetMapping("/admin/write")
    public String noticeWrite(Model model) {
        model.addAttribute("currentDate", new java.util.Date());
        return "notifications/noticeWrite";
    }

    // 공지사항 등록 처리
    @PostMapping("/admin/register")
    public String registerNotice(@ModelAttribute NoticeDTO noticeDTO) {
        // 공지사항 저장 처리
        noticeService.saveNotice(noticeDTO);

        return "redirect:/notice/list";
    }

    // 공지사항 임시 저장
    @PostMapping("/admin/save")
    public String saveNotice(@ModelAttribute NoticeDTO noticeDTO) {
        noticeService.saveNotice(noticeDTO);

        return "redirect:/notice/list";
    }

    // 공지사항 삭제
    @PostMapping("/admin/delete")
    public String deleteNotice(@RequestParam("noticeNum") int noticeNum) {
        noticeService.deleteNotice(noticeNum);
        return "redirect:/notice/list";
    }
}
