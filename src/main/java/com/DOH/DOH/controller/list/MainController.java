package com.DOH.DOH.controller.list;

import com.DOH.DOH.service.user.UserSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Iterator;
@Slf4j
@Controller
public class MainController {

    private final UserSessionService userSessionService;

    public MainController(UserSessionService userSessionService){
        this.userSessionService = userSessionService;
    }

    @GetMapping("/")
    public String main(Model model){

        // 우선 UserSessionService에서 값을 가져옴
        String userEmail = userSessionService.userEmail();
        String userRole = userSessionService.userRole();

        // 백업 로직: UserSessionService에서 값을 가져오지 못했을 때 SecurityContextHolder로 대체
        if (userEmail == null || userEmail.isEmpty()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                userEmail = auth.getName(); // 사용자 이메일
                userRole = auth.getAuthorities().iterator().next().getAuthority(); // 사용자 역할
            }
        }

        log.info("userEmail : " + userEmail);
        log.info("userRole : " + userRole);

        // Thymeleaf로 전달
        model.addAttribute("userEmail", userEmail);
        model.addAttribute("userRole", userRole);

        return "list/main"; // 해당 템플릿 파일로 리턴
    }
}
