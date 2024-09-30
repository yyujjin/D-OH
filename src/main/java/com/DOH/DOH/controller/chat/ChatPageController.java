package com.DOH.DOH.controller.chat;

import com.DOH.DOH.service.chat.ChatService;
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
@RequestMapping("/users")
public class ChatPageController {

    private final UserSessionService userSessionService;
    private final ChatService chatService;

    public ChatPageController(UserSessionService userSessionService, ChatService chatService) {
        this.userSessionService = userSessionService;
        this.chatService = chatService;
    }

    //채팅방 입장
    @GetMapping("/chat")
    public String enterRoom(Model model, @RequestParam String receiver){
        String userNickName = userSessionService.nickName();

        //로그인한 본인 
        model.addAttribute("sender",userNickName);
        //메시지를 받는 사람
        model.addAttribute("receiver",receiver);
        model.addAttribute("receiverPhoto",chatService.userProfilePhoto(receiver));
        return "/chat/chatRoom";
    }
}
