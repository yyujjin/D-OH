package com.DOH.DOH.service.user;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine; // Thymeleaf 템플릿 엔진 추가
    private final Map<String, String> verificationCodes = new HashMap<>();

    public EmailService(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    // 인증 코드 생성 메서드
    private String generateCode() {
        return UUID.randomUUID().toString().substring(0, 6); // 간단한 6자리 인증 코드 생성
    }

    // 이메일을 생성하고 발송하는 메서드
    public void sendEmail(String toEmail) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        // 이메일 제목 설정
        helper.setSubject("[D'OH] 이메일 인증 코드");

        // 인증 코드 생성
        String authCode = generateCode();

        // Thymeleaf Context 설정
        Context context = new Context();
        context.setVariable("authCode", authCode);

        // Thymeleaf 템플릿 처리
        String emailContent = templateEngine.process("user/email-template", context);  // 템플릿 파일명(email-template.html)과 Context를 전달

        // 이메일 본문 설정 (HTML 내용)
        helper.setText(emailContent, true);

        // 수신자 이메일 주소 설정
        helper.setTo(toEmail);

        // 발신자 이메일 주소 설정
        helper.setFrom("mkh240422@gmail.com");

        // 인증 코드를 Map에 저장, 이메일 key-인증 코드를 value로 저장
        verificationCodes.put(toEmail, authCode);

        // 이메일 발송
        javaMailSender.send(message);
    }

    // 이메일 코드 확인 메서드
    public boolean verifyEmailCode(String inputCode, String email) {
        log.debug("EmailService.verifyEmailCode() - Verifying code for email: {}, Input Code: {}", email, inputCode);
        String actualCode = verificationCodes.get(email);
        log.debug("EmailService.verifyEmailCode() - Actual Code: {}", actualCode);
        if (actualCode != null && actualCode.equals(inputCode)) {
            // 인증 성공 시 코드 제거
            verificationCodes.remove(email);
            log.debug("EmailService.verifyEmailCode() - Verification successful");
            return true;
        }
        log.debug("EmailService.verifyEmailCode() - Verification failed");
        return false;
    }
}
