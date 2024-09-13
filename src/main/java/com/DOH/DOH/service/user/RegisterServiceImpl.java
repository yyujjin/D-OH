package com.DOH.DOH.service.user;

import com.DOH.DOH.dto.user.RegisterDTO;
import com.DOH.DOH.mapper.user.RegisterMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService{

    private final RegisterMapper registerMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public RegisterServiceImpl(RegisterMapper registerMapper, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.registerMapper = registerMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void register(RegisterDTO registerDTO) {
        // 중복된 이메일 있는지 확인
        RegisterDTO existingUser = registerMapper.getUserByUserEmail(registerDTO.getUserEmail());
        if (existingUser != null) {
            return;
        }

        // 비밀번호를 BCrypt로 암호화하여 LoginDTO에 설정
        registerDTO.setUserPassword(bCryptPasswordEncoder.encode(registerDTO.getUserPassword()));
        //회원가입 시 ROLE_ADMIN, ROLE_USER 설정
        registerDTO.setRole("ROLE_USER");

        registerMapper.userRegister(registerDTO);
    }


}
