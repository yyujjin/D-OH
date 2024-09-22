package com.DOH.DOH.controller.notifications;

import com.DOH.DOH.dto.notifications.NoticeDTO;
import com.DOH.DOH.service.notifications.NoticeService;
import com.DOH.DOH.service.user.UserSessionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
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
        List<NoticeDTO> noticeList = noticeService.getNoticeList(page);
        model.addAttribute("noticeList", noticeList);  // 공지사항 목록을 모델에 추가

        return "notifications/noticeList";  // templates/notifications/list.html 템플릿을 사용
    }

    // 공지사항 작성(작성&수정) 페이지 렌더링
    @PostMapping("/admin/write")
    public String noticeWriteForm(@RequestParam(value = "noticeNum", required = false) Long noticeNum, ModelMap modelMap) throws Exception {
        // 수정 작업 시 공지사항 정보 조회
        if (noticeNum != null) {
            NoticeDTO noticeInfo = noticeService.getNoticeById(noticeNum);
            modelMap.addAttribute("noticeDTO", noticeInfo);  // 수정 시 사용될 정보
            log.info("공지사항 수정 페이지 - noticeNum: {}", noticeNum);
        } else {
            // 새로운 공지사항 등록을 위한 빈 객체 전달
            modelMap.addAttribute("noticeDTO", new NoticeDTO());
            log.info("공지사항 신규 등록 페이지");
        }
        return "notifications/noticeWrite";  // templates/notifications/noticeWrite.html 템플릿 사용
    }

    // 공지사항 작성(작성&수정) 처리 (POST로 처리)
    @PostMapping("/admin/register")
    public String noticeWrite(@ModelAttribute NoticeDTO noticeDTO, RedirectAttributes redirectAttributes) throws Exception {
        String userEmail = userSessionService.userEmail();
        String userRole = userSessionService.userRole();

        // 권한 검사: 관리자만 작성 가능
        if (!userEmail.equals("admin") && !userRole.equals("ROLE_ADMIN")) {
            redirectAttributes.addFlashAttribute("errorMessage", "공지사항 작성 권한이 없습니다.");
            return "redirect:/notice/list";  // 권한 없으면 목록 페이지로 리다이렉트
        }

        // 작성과 수정을 구분
        if (noticeDTO.getNoticeNum() == null) {
            // 신규 공지사항 등록
            noticeService.noticeRegister(noticeDTO);
            log.info("공지사항 등록 완료 - 제목: {}", noticeDTO.getNoticeTitle());
        } else {
            // 기존 공지사항 수정
            noticeService.noticeUpdate(noticeDTO);
            log.info("공지사항 수정 완료 - noticeNum: {}", noticeDTO.getNoticeNum());
        }

        return "redirect:/notice/list";  // 등록/수정 후 공지사항 목록 페이지로 리다이렉트
    }

    //삭제
}
