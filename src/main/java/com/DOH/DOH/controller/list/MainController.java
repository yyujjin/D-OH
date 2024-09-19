package com.DOH.DOH.controller.list;

import com.DOH.DOH.service.user.UserSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Slf4j
@Controller
public class MainController {

    private final UserSessionService userSessionService;

    public MainController(UserSessionService userSessionService){
        this.userSessionService = userSessionService;
    }

    @GetMapping("/")
    public String main(Model model, Principal principal) {
        // Principal에서 사용자 이메일과 역할 가져오기
        String userEmail = principal != null ? principal.getName() : "anonymousUser";
        String userRole = principal != null ? SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority() : "ROLE_ANONYMOUS";

        log.info("userEmail : {}", userEmail);
        log.info("userRole : {}", userRole);

        // Thymeleaf로 전달
        model.addAttribute("userEmail", userEmail);
        model.addAttribute("userRole", userRole);

        return "list/main"; // 해당 템플릿 파일로 리턴
    }
}