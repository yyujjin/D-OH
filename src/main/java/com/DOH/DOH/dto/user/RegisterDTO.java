package com.DOH.DOH.dto.user;

import lombok.Data;

@Data
public class RegisterDTO {
    private int userNum;
    private String userEmail;
    private String userPassword;
    //security 권한
    private String role;
}
