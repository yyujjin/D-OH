package com.DOH.DOH.controller.notifications;

import com.DOH.DOH.dto.notifications.NoticeDTO;
import com.DOH.DOH.service.notifications.NoticeService;
import com.DOH.DOH.service.user.UserSessionService;
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
    public String noticeList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") String pageNum, Model model) {
        int page = Integer.parseInt(pageNum);

        int totalPages = noticeService.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        List<NoticeDTO> noticeList = noticeService.getNoticeList(page);
        model.addAttribute("noticeList", noticeList);

        return "notifications/noticeList";
    }

    // 공지사항 작성(작성&수정) 페이지 렌더링
    @PostMapping("/admin/write")
    public String noticeWriteForm(@RequestParam(value = "noticeNum", required = false) Long noticeNum, ModelMap modelMap) throws Exception {
        if (noticeNum != null) {
            NoticeDTO noticeInfo = noticeService.getNoticeById(noticeNum);
            modelMap.addAttribute("noticeDTO", noticeInfo);
            log.info("공지사항 수정 페이지 - noticeNum: {}", noticeNum);
        } else {
            modelMap.addAttribute("noticeDTO", new NoticeDTO());
            log.info("공지사항 신규 등록 페이지");
        }
        return "notifications/noticeWrite";
    }

    // 공지사항 작성(작성&수정) 처리 (POST로 처리)
    @PostMapping("/admin/register")
    public String noticeWrite(@ModelAttribute NoticeDTO noticeDTO, RedirectAttributes redirectAttributes) throws Exception {
        String userEmail = userSessionService.userEmail();
        String userRole = userSessionService.userRole();
        Long userNum = userSessionService.userNum();  // Retrieve userNum from session

        // 권한 검사: 관리자만 작성 가능
        if (!userEmail.equals("admin") && !userRole.equals("ROLE_ADMIN")) {
            redirectAttributes.addFlashAttribute("errorMessage", "공지사항 작성 권한이 없습니다.");
            return "redirect:/notice/list";
        }

        // Set the userNum in the NoticeDTO
        noticeDTO.setUserNum(Math.toIntExact(userNum));

        if (noticeDTO.getNoticeNum() == null) {
            noticeService.noticeRegister(noticeDTO);
            log.info("공지사항 등록 완료 - 제목: {}, 작성자: {}", noticeDTO.getNoticeTitle(), userEmail);
        } else {
            noticeService.noticeUpdate(noticeDTO);
            log.info("공지사항 수정 완료 - noticeNum: {}", noticeDTO.getNoticeNum());
        }

        return "redirect:/notice/list";
    }

    // 공지사항 임시 저장 처리
    @PostMapping("/admin/tempSave")
    public String saveTempNotice(@ModelAttribute NoticeDTO noticeDTO, RedirectAttributes redirectAttributes) throws Exception {
        String userEmail = userSessionService.userEmail();
        String userRole = userSessionService.userRole();
        Long userNum = userSessionService.userNum();  // Retrieve userNum from session

        // 권한 검사: 관리자만 임시 저장 가능
        if (!userEmail.equals("admin") && !userRole.equals("ROLE_ADMIN")) {
            redirectAttributes.addFlashAttribute("errorMessage", "공지사항 임시 저장 권한이 없습니다.");
            return "redirect:/notice/list";
        }

        noticeDTO.setUserNum(Math.toIntExact(userNum));

        noticeService.saveTempNotice(noticeDTO);
        log.info("공지사항 임시 저장 완료 - 제목: {}", noticeDTO.getNoticeTitle());

        return "redirect:/notice/list";
    }

    // 공지사항 삭제 처리
    @PostMapping("/admin/delete")
    public String deleteNotice(@RequestParam("noticeNum") Long noticeNum, RedirectAttributes redirectAttributes) throws Exception {
        String userEmail = userSessionService.userEmail();
        String userRole = userSessionService.userRole();

        // 권한 검사: 관리자만 삭제 가능
        if (!userEmail.equals("admin") && !userRole.equals("ROLE_ADMIN")) {
            redirectAttributes.addFlashAttribute("errorMessage", "공지사항 삭제 권한이 없습니다.");
            return "redirect:/notice/list";
        }

        noticeService.deleteNotice(noticeNum);
        log.info("공지사항 삭제 완료 - noticeNum: {}", noticeNum);

        return "redirect:/notice/list";
    }
}
