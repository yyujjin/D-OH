package com.DOH.DOH.mapper.user;

import com.DOH.DOH.dto.user.MyPageProfileDTO;
import com.DOH.DOH.dto.user.MyPageSkillDTO;
import com.DOH.DOH.dto.user.RegisterDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MyPageProfileMapper {
    void insert(MyPageProfileDTO myPageProfileDTO);
    MyPageProfileDTO findByUserEmail(String userEmail);
    MyPageProfileDTO findIdByUserEmail(Long id, String userEmail);
    int update(MyPageProfileDTO myPageProfileDTO);
    void updateUserNickName(@Param("newName") String newName, @Param("oldName") String oldName);
    void updateUserInfoNickName(@Param("newName") String newName, @Param("oldName") String oldName);
}
