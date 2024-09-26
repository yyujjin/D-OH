package com.DOH.DOH.service.user;

import com.DOH.DOH.dto.user.MyPageDTO;

import java.util.List;

public interface MyPageService {
    MyPageDTO findByuserEmail(String userEmail);
    MyPageDTO profilePhoto(String userEmail);
    void insertProfilePhoto(MyPageDTO myPageDTO);
    void insertUserEmail(String userEmail);
}
