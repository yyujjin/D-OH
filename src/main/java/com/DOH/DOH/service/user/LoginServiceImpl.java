package com.DOH.DOH.service.user;

import com.DOH.DOH.dto.user.LoginDTO;
import com.DOH.DOH.mapper.user.LoginMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class LoginServiceImpl implements LoginService{

    private final LoginMapper loginMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public LoginServiceImpl(LoginMapper loginMapper, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.loginMapper = loginMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void register(LoginDTO loginDTO) {
        // 중복된 이메일 있는지 확인
        LoginDTO existingUser = loginMapper.getUserByUserEmail(loginDTO.getUserEmail());
        if (existingUser != null) {
            return;
        }

        // 비밀번호를 BCrypt로 암호화하여 LoginDTO에 설정
        loginDTO.setUserPassword(bCryptPasswordEncoder.encode(loginDTO.getUserPassword()));
        //회원가입 시 ROLE_ADMIN, ROLE_USER 설정
        loginDTO.setRole("ROLE_USER");

        loginMapper.userRegister(loginDTO);
    }


}
