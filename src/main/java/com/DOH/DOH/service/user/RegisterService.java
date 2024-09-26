package com.DOH.DOH.service.user;

import com.DOH.DOH.dto.user.RegisterDTO;

public interface RegisterService {

    //회원가입 처리 메서드
    public void register(RegisterDTO registerDTO);

    //이메일 중복 확인 메서드
    boolean duplicateUserEmail(String userEmail);
    
    //닉네임 중복 확인 메서드
    boolean duplicateNickName(String nickName);

}
