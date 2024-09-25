package com.DOH.DOH.dto.user;

import lombok.Data;

@Data
public class EmailDTO {
	// 이메일 주소
    private String userEmail;
    // 인증 코드
    private String verifyCode;
}