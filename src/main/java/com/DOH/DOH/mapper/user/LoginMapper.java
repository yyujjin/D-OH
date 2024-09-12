package com.DOH.DOH.mapper.user;

import com.DOH.DOH.dto.user.LoginDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

@Mapper
public interface LoginMapper {

    public void userRegister(LoginDTO loginDTO);
    //해당 이메일로 조회된 사용자의 정보
    public LoginDTO getUserByUserEmail(String userEmail);

}
