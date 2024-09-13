package com.DOH.DOH.mapper.user;

import com.DOH.DOH.dto.user.RegisterDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegisterMapper {

    public void userRegister(RegisterDTO registerDTO);
    //해당 이메일로 조회된 사용자의 정보
    public RegisterDTO getUserByUserEmail(String userEmail);

}
