package com.DOH.DOH.mapper.user;

import com.DOH.DOH.dto.user.MyPageProfileDTO;
import com.DOH.DOH.dto.user.MyPageSkillDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyPageProfileMapper {
    void insert(MyPageProfileDTO myPageProfileDTO);
    MyPageProfileDTO findByUserEmail(String userEmail);
    MyPageProfileDTO findIdByUserEmail(Long id, String userEmail);
    int update(MyPageProfileDTO myPageProfileDTO);
}
