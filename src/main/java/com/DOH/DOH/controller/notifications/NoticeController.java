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

    // 공지사항 상세 조회
    @GetMapping("/detail")
    public String noticeDetail(@RequestParam(name = "noticeNum", defaultValue = "0") int noticeNum, Model model) {
        if (noticeNum <= 0) {
            // 파라미터가 누락되었거나 유효하지 않은 경우 처리
            return "redirect:/notice/list";  // 목록 페이지로 리다이렉트 또는 오류 페이지 표시
        }
        NoticeDTO notice = noticeService.getNoticeDetail(noticeNum);
        model.addAttribute("notice", notice);
        return "notifications/noticeDetail";
    }

    // 공지사항 작성 페이지
    @GetMapping("/admin/write")
    public String noticeWrite(Model model) {
        // 초기값이 필요하면 모델에 추가 가능 (예: 현재 날짜)
        model.addAttribute("currentDate", new java.util.Date());
        return "notifications/noticeWrite";
    }

    // 공지사항 등록 처리
    @PostMapping("/admin/register")
    public String registerNotice(@ModelAttribute NoticeDTO noticeDTO, HttpSession session) {
        // 로그인된 사용자 정보 가져오기 (세션에서 사용자 ID나 번호를 가져온다고 가정)
        NoticeDTO loggedInUser = (NoticeDTO) session.getAttribute("loggedInUser");

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

    // 공지사항 임시 저장
    @PostMapping("/admin/save")
    public String saveNotice(@ModelAttribute NoticeDTO noticeDTO, HttpSession session) {
        NoticeDTO loggedInUser = (NoticeDTO) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            noticeDTO.setUserNum(loggedInUser.getUserNum());
            noticeDTO.setNoticeTempSave(true);  // 임시 저장 플래그 설정
        } else {
            throw new RuntimeException("로그인된 사용자가 없습니다.");
        }

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
