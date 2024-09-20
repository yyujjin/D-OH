package com.DOH.DOH.controller.notifications;

import com.DOH.DOH.dto.notifications.NoticeDTO;
import com.DOH.DOH.service.notifications.NoticeService;
import com.DOH.DOH.service.user.UserSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
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

    //공지사항 작성(작성&수정)
    @GetMapping("/admin/write")
    public String noticeWrite(@RequestParam(value = "noticeNum", required = false) Long noticeNum, Model model, RedirectAttributes redirectAttributes) {
        String userEmail = userSessionService.userEmail();
        String userRole = userSessionService.userRole();

        // 특정 사용자만 작성 가능
        if (!userEmail.equals("admin") && !userRole.equals("ROLE_ADMIN")) {
            redirectAttributes.addFlashAttribute("errorMessage", "공지사항 작성 권한이 없습니다.");
            return "redirect:/notice/list";  // 권한이 없으면 목록 페이지로 리다이렉트
        }

        model.addAttribute("userEmail", userEmail);

        if (noticeNum != null) {
            // 수정할 공지사항이 있는 경우, 해당 공지사항의 데이터를 불러옴
            NoticeDTO noticeDTO = noticeService.getNoticeById(noticeNum);

            // 로그 추가: noticeNum 값과 함께 로그 출력
            log.info("공지사항 수정 - noticeNum: {}", noticeNum);

            model.addAttribute("noticeDTO", noticeDTO);
        } else {
            log.info("공지사항 신규 등록");

            model.addAttribute("noticeDTO", new NoticeDTO());  // 빈 DTO 추가 (신규 등록 시 사용)
        }

        return "notifications/noticeWrite";  // templates/notifications/write.html 템플릿을 사용
    }

    // 공지사항 등록(작성&수정)
    @PostMapping("/admin/register")
    public String noticeRegister(NoticeDTO noticeDTO, Model model, RedirectAttributes redirectAttributes) {
        String userEmail = userSessionService.userEmail();
        String userRole = userSessionService.userRole();

        // 특정 사용자만 작성 가능
        if (!userEmail.equals("admin") && !userRole.equals("ROLE_ADMIN")) {
            redirectAttributes.addFlashAttribute("errorMessage", "공지사항 작성 권한이 없습니다.");
            return "redirect:/notice/list";  // 권한이 없으면 목록 페이지로 리다이렉트
        }

        model.addAttribute("userEmail", userEmail);

        if (noticeDTO.getNoticeNum() == null) {
            // 신규 공지사항 등록
            noticeService.noticeRegister(noticeDTO, model);
        } else {
            // 기존 공지사항 수정
            noticeService.noticeUpdate(noticeDTO, model);
        }

        // 공지사항 목록 페이지로 리다이렉트
        return "redirect:/notice/list";
    }
}