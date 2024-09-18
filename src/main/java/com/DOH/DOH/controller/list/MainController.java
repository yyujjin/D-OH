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

        String userEmail = userSessionService.userEmail();
        String userRole = userSessionService.userRole();
        log.info("userEmail : "+userEmail);
        log.info("userRole : "+userRole);

        return "list/main";
    }

    @GetMapping("testSidebar")
    public String testSidebar() {
        return "/fragments/quickMenu";
    }
}
