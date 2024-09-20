package com.DOH.DOH.controller.notifications;

import com.DOH.DOH.dto.notifications.NoticeDTO;
import com.DOH.DOH.service.notifications.NoticeService;
import com.DOH.DOH.service.user.UserSessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    // 공지사항 목록 페이지
    @GetMapping("/list")
    public String noticeList(
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") String pageNum,
            Model model) {

        // 페이지 번호 처리
        int page = Integer.parseInt(pageNum);  // pageNum을 int로 변환

        // 총 페이지 수 가져오기
        int totalPages = noticeService.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        // 해당 페이지의 공지사항 목록 가져오기
        List<NoticeDTO> noticeList = noticeService.getNoticeList(Integer.parseInt(pageNum));
        model.addAttribute("noticeList", noticeList);  // 공지사항 목록을 모델에 추가

        // 뷰 리턴
        return "notifications/noticeList";  // templates/notifications/list.html 템플릿을 사용
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

    //공지사항 작성 & 수정
    @GetMapping("/admin/write")
    public String noticeWrite(Model model) {
        String userEmail = userSessionService.userEmail();
        model.addAttribute("userEmail", userEmail);

        return "notifications/noticeWrite";
    }


}
