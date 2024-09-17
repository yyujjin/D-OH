package com.DOH.DOH.controller.notifications;

import com.DOH.DOH.dto.notifications.NoticeDTO;
import com.DOH.DOH.service.notifications.NoticeService;
import com.DOH.DOH.service.user.UserSessionService;
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

    // 공지사항 목록 페이지
    @GetMapping("/list")
    public String noticeList(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
        int totalPages = noticeService.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        noticeService.getNoticeList(page, model);
        model.addAttribute("userEmail", userSessionService.userEmail());

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
        model.addAttribute("userEmail", userSessionService.userEmail());

        return "notifications/noticeDetail";
    }

    // 공지사항 작성 페이지
    @GetMapping("/admin/write")
    public String noticeWrite(Model model) {
        String userEmail = userSessionService.userEmail();

        model.addAttribute("currentDate", new java.util.Date());
        model.addAttribute("userEmail", userSessionService.userEmail());

        return "notifications/noticeWrite";
    }

    // 공지사항 등록 처리
    @PostMapping("/admin/register")
    public String registerNotice(@ModelAttribute NoticeDTO noticeDTO) {
        String userEmail = userSessionService.userEmail();

        // 공지사항 저장 처리
        noticeService.saveNotice(noticeDTO);

        return "redirect:/notice/list";
    }

    // 공지사항 임시 저장
    @PostMapping("/admin/save")
    public String saveNotice(@ModelAttribute NoticeDTO noticeDTO) {
        String userEmail = userSessionService.userEmail();

        noticeService.saveNotice(noticeDTO);

        return "redirect:/notice/list";
    }

    // 공지사항 삭제
    @PostMapping("/admin/delete")
    public String deleteNotice(@RequestParam("noticeNum") int noticeNum) {
        String userEmail = userSessionService.userEmail();

        return "redirect:/notice/list";
    }
}
