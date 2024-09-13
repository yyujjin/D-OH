package com.DOH.DOH.service.user;

import com.DOH.DOH.dto.user.RegisterDTO;

public interface RegisterService {

    //회원가입 처리 메서드
    public void register(RegisterDTO registerDTO);
}
