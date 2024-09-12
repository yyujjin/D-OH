package com.DOH.DOH.service.user;

import com.DOH.DOH.dto.user.LoginDTO;

import java.util.HashMap;

public interface LoginService {

    //회원가입 처리 메서드
    public void register(LoginDTO loginDTO);
}
