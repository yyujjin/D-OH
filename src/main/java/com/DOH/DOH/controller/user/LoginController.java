package com.DOH.DOH.controller.user;

import com.DOH.DOH.dto.user.LoginDTO;
import com.DOH.DOH.service.user.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;

@Slf4j
@Controller
public class LoginController {

    private final LoginService loginService;

    //@Autowired->생성자 주입을 통해 LoginService 주입
    public LoginController(LoginService loginService) {

        this.loginService = loginService;
    }

    //로그인 페이지로 이동
    @GetMapping("/login")
    public String showLogin() {

        return "user/login";
    }
    //회원가입 페이지로 이동
    @GetMapping("/users/register")
    public String showRegisterForm(){

        return "user/register";
    }

    @PostMapping("/users/register")
    public String showRegister(@ModelAttribute LoginDTO loginDTO) {

        System.out.println(loginDTO);

        loginService.register(loginDTO);

        return "redirect:/login";
    }

}
