package com.DOH.DOH.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MyPageController {
    @GetMapping("/mypage")
    public String myPage() {
        return "user/MyPage";
    }

    @GetMapping("/portfolio")
    public String getPortfolioFragment(Model model) {

        return "user/MyPagePortFolio";
   }
   @GetMapping("/upload")
    public String uploadFragment(Model model) {
        return "user/MyPageUpload";
   }
   @GetMapping("/profileWrite")
    public String profileWriteFragment(Model model) {
        return "user/MyPageProfile";
   }
}