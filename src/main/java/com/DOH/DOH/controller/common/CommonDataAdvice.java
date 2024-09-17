package com.DOH.DOH.controller.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class CommonDataAdvice {

    // 공통적으로 사용되는 데이터를 설정하는 메서드
    @ModelAttribute
    public void addCommonAttributes(Model model, Principal principal) {
        String userEmail = principal != null ? principal.getName() : "anonymousUser";
        String userRole = "ROLE_ANONYMOUS";

        // Spring Security에서 현재 사용자 권한을 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            userRole = authentication.getAuthorities().iterator().next().getAuthority();
        }

        // 사용자 이메일과 권한을 모델에 추가
        model.addAttribute("userEmail", userEmail);
        model.addAttribute("userRole", userRole);
    }
}
