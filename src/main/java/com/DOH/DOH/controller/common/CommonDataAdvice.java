package com.DOH.DOH.controller.common;

import com.DOH.DOH.dto.user.CustomUserDetails;
import com.DOH.DOH.service.user.UserSessionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class CommonDataAdvice {

    private final UserSessionService userSessionService;

    public CommonDataAdvice(UserSessionService userSessionService) {
        this.userSessionService = userSessionService;
    }

    @ModelAttribute
    public void addCommonAttributes(Model model) {

        model.addAttribute("userEmail", userSessionService.userEmail());
        model.addAttribute("userRole", userSessionService.userRole());
        model.addAttribute("nickName", userSessionService.nickName());
    }
}
