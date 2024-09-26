package com.DOH.DOH.controller.user;

import com.DOH.DOH.dto.user.RegisterDTO;
import com.DOH.DOH.service.user.RegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j

@Controller
public class RegisterController {


    private final RegisterService registerService;

    //@Autowired->생성자 주입을 통해 LoginService 주입
    public RegisterController(RegisterService loginService) {

        this.registerService = loginService;
    }

    //로그인 페이지로 이동
    @GetMapping("/users/login")
    public String showLogin() {

        return "user/login";
    }
    //회원가입 페이지로 이동
    @GetMapping("/users/register")
    public String showRegisterForm(){

        return "user/register";
    }

    @PostMapping("/users/register")
    public String showRegister(@ModelAttribute RegisterDTO registerDTO) {

        registerService.register(registerDTO);

        return "redirect:/users/login";
    }

    @RestController
    @RequestMapping("/api/v1/users")
    @RequiredArgsConstructor
    public class  RegisterApiController{

        private final RegisterService registerService;

        @GetMapping("/check-email")
        public boolean checkEmail(@RequestParam String userEmail) {

            return registerService.duplicateUserEmail(userEmail);

        }

        @GetMapping("/check-nickname")
        public boolean checkNickName(@RequestParam String nickName) {

            return registerService.duplicateNickName(nickName);

        }

    }
}
