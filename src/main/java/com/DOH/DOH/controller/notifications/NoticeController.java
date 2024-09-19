package com.DOH.DOH.controller.notifications;

import com.DOH.DOH.dto.notifications.NoticeDTO;
import com.DOH.DOH.service.notifications.NoticeService;
import com.DOH.DOH.service.user.UserSessionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("notice")
public class NoticeController {

    private final NoticeService noticeService;
    private final UserSessionService userSessionService;

    public NoticeController(NoticeService noticeService, UserSessionService userSessionService) {
        this.noticeService = noticeService;
        this.userSessionService = userSessionService;
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
    public String noticeList(
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") String pageNum,
            Model model) {

        // 현재 로그인한 사용자의 이메일 가져오기
        String userEmail = userSessionService.userEmail();
        model.addAttribute("userEmail", userEmail);

        // 페이지 번호 처리
        int page = Integer.parseInt(pageNum);  // pageNum을 int로 변환

        // 총 페이지 수 가져오기
        int totalPages = noticeService.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        // 해당 페이지의 공지사항 목록 가져오기
        List<NoticeDTO> noticeList = noticeService.getNoticeList(page);
        model.addAttribute("noticeList", noticeList);  // 공지사항 목록을 모델에 추가

        // 뷰 리턴
        return "notifications/noticeList";  // templates/notifications/list.html 템플릿을 사용
    }

    // 공지사항 상세 조회
    @GetMapping("/detail")
    public String noticeDetail(@RequestParam(name = "noticeNum", defaultValue = "0") int noticeNum, Model model) {
        // 현재 로그인한 사용자의 이메일 가져오기
        String userEmail = userSessionService.userEmail();
        model.addAttribute("userEmail", userEmail);

        if (noticeNum <= 0) {
            return "redirect:/notice/list";  // 목록 페이지로 리다이렉트
        }
        NoticeDTO notice = noticeService.getNoticeDetail(noticeNum);
        model.addAttribute("notice", notice);

        return "notifications/noticeDetail";
    }

    // 공지사항 등록
    @PostMapping("/admin/register")
    public String noticeRegister(NoticeDTO noticeDTO, Model model) {
        // 현재 로그인한 사용자의 이메일 가져오기
        String userEmail = userSessionService.userEmail();
        model.addAttribute("userEmail", userEmail);

        // 공지사항 등록 서비스 호출
        noticeService.noticeRegister(noticeDTO, model);

        // 공지사항 목록 페이지로 이동
        return "notifications/noticeList";
    }


    //공지사항 작성
    @GetMapping("/admin/write")
    public String noticeWrite(Model model) {
        String userEmail = userSessionService.userEmail();
        model.addAttribute("userEmail", userEmail);

        return "notifications/noticeWrite";
    }
}
