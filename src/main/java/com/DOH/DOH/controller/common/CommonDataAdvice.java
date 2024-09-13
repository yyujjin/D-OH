package com.DOH.DOH.controller.common;

import com.DOH.DOH.service.user.UserSessionService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CommonDataAdvice {

    private final UserSessionService userSessionService;

    public CommonDataAdvice(UserSessionService userSessionService) {
        this.userSessionService = userSessionService;
    }

    // 공통적으로 사용되는 데이터를 설정하는 메서드
    @ModelAttribute
    public void addCommonAttributes(Model model) {
        // 사용자 이메일과 권한을 모델에 추가
        model.addAttribute("userEmail", userSessionService.userEmail());
    }
}
