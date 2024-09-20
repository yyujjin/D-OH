package com.DOH.DOH.service.user;

import com.DOH.DOH.dto.user.MyPageProfileDTO;


public interface MyPageProfileService {
    void insert(MyPageProfileDTO myPageProfileDTO);
    MyPageProfileDTO findByUserEmail(String userEmail);
}
