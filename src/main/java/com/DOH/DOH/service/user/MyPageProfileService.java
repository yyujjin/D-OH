package com.DOH.DOH.service.user;

import com.DOH.DOH.dto.user.MyPageProfileDTO;
import com.DOH.DOH.dto.user.MyPageSkillDTO;


public interface MyPageProfileService {
    void insert(MyPageProfileDTO myPageProfileDTO);
    MyPageProfileDTO findByUserEmail(String userEmail);
    MyPageProfileDTO findIdByUserEmail(Long id, String userEmail);
    void update(MyPageProfileDTO myPageProfileDTO);

}
