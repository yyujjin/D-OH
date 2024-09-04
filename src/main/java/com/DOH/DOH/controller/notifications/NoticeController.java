package com.DOH.DOH.controller.notifications;

import com.DOH.DOH.service.notifications.NoticeService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("notice")
public class NoticeController {
    @Autowired
    NoticeService noticeService;

    @GetMapping("list")
    public String noticeList(Model model, @RequestParam(required = false, defaultValue = "1") int page) {
        // 공지사항 목록을 서비스 계층에서 불러와 모델에 추가
        noticeService.getNoticeList(page, model);

        // 공지사항 목록 페이지로 이동 (로그인 여부와 상관없이)
        return "notice/list";
    }
}
