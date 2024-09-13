package com.DOH.DOH.controller.chat;

import com.DOH.DOH.service.user.UserSessionService;
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

    private final UserSessionService userSessionService;

    public ChatPageController(UserSessionService userSessionService) {
        this.userSessionService = userSessionService;
    }

    @GetMapping("/start")
    public String startChatPage(){
        return "/chat/start-chat";
    }

    @GetMapping("/room")
    public String enterRoom(Model model){
        String userId = userSessionService.userEmail();
        model.addAttribute("userId",userId);
        return "/chat/chatRoom";
    }
}
