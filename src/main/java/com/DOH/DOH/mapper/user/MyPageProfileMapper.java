package com.DOH.DOH.mapper.user;

import com.DOH.DOH.dto.user.MyPageProfileDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyPageProfileMapper {
    void insert(MyPageProfileDTO myPageProfileDTO);
    MyPageProfileDTO findByUserEmail(String userEmail);
}
