package com.DOH.DOH.controller.user;

import com.DOH.DOH.dto.user.EmailDTO;
import com.DOH.DOH.service.user.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailController {
    private final EmailService emailService;

    // 인증코드 메일 발송
    @PostMapping("/send")
    public String mailSend(@RequestBody EmailDTO emailDTO) throws MessagingException {
        log.info("EmailController.mailSend()");
        emailService.sendEmail(emailDTO.getUserEmail());
        return "인증코드가 발송되었습니다.";
    }

 // 인증코드 인증
    @PostMapping("/verify")
    public boolean verify(@RequestBody EmailDTO emailDTO) {
        log.info("EmailController.verify() - Verifying code for email: {}", emailDTO.getUserEmail());
        boolean result = emailService.verifyEmailCode(emailDTO.getVerifyCode(), emailDTO.getUserEmail());
        log.info("EmailController.verify() - Verification result: {}", result);
        return result;
    }
}