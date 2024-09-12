package com.DOH.DOH.controller.chat;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/chat")
public class ChatPageController {

    @Autowired
    HttpSession httpSession;

    @GetMapping("/start")
    public String startChatPage(){
        return "/chat/start-chat";
    }

    @GetMapping("/room")
    public String enterRoom(Model model){
        model.addAttribute("userId",httpSession.getAttribute("userId"));
        log.info("세션:{}",httpSession.getAttribute("userId"));
        return "/chat/chatRoom";
    }

    @GetMapping("/login")
    public String login() {
        return "/chat/login";
    }

    @PostMapping("/login")
    public String loginOK(HttpSession httpSession, @RequestParam String userId) {
        httpSession.setAttribute("userId", userId);
        String user = (String) httpSession.getAttribute("userId");
        log.info("현재 로그인한 사용자의 아이디 : {}",user);

        return "/chat/chatRoom";
    }
}
