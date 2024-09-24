package com.DOH.DOH.controller.list;

import com.DOH.DOH.dto.list.ContestListDTO;
import com.DOH.DOH.service.list.MainService;
import com.DOH.DOH.service.user.UserSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;

@Slf4j
@Controller
public class MainController {

    @Autowired
    private MainService mainService;

    private final UserSessionService userSessionService;

    public MainController(UserSessionService userSessionService){
        this.userSessionService = userSessionService;
    }

    @GetMapping("/")
    public String main(Model model, Principal principal) {

        String userEmail = userSessionService.userEmail();
        String userRole = userSessionService.userRole();
        String userNickName = userSessionService.nickName();

        log.info("userEmail : {}", userEmail);
        log.info("userRole : {}", userRole);
        log.info("userNickName : {}",userNickName);

        ArrayList<ContestListDTO> imageList = mainService.getImageList();
        model.addAttribute("imageList",imageList);

        return "list/main"; // 해당 템플릿 파일로 리턴
    }
}