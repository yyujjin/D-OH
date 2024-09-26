package com.DOH.DOH.mapper.user;

import com.DOH.DOH.dto.user.MyPageDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyPageMapper {
    MyPageDTO findByuserEmail(String userEmail);
    MyPageDTO profilePhoto(String userEmail);
    void insertProfilePhoto(MyPageDTO myPageDTO);
    void insertUserEmail(String userEmail);
}
