package com.DOH.DOH.service.user;

import com.DOH.DOH.dto.user.MyPageProfileDTO;
import com.DOH.DOH.dto.user.MyPageSkillDTO;
import com.DOH.DOH.dto.user.RegisterDTO;


public interface MyPageProfileService {
    void insert(MyPageProfileDTO myPageProfileDTO);
    MyPageProfileDTO findByUserEmail(String userEmail);
    MyPageProfileDTO findIdByUserEmail(Long id, String userEmail);
    void update(MyPageProfileDTO myPageProfileDTO);
    void updateUserNickName(String newName, String oldName);
    void updateUserInfoNickName(String newName, String oldName);
}
