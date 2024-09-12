package com.DOH.DOH.service.user;

import com.DOH.DOH.dto.user.CustomUserDetails;
import com.DOH.DOH.dto.user.LoginDTO;
import com.DOH.DOH.mapper.user.LoginMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private LoginMapper loginMapper;

    public CustomUserDetailsService(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        LoginDTO data = loginMapper.getUserByUserEmail(userEmail);

        if (data == null) {
            throw new UsernameNotFoundException("User not found with email: " + userEmail);
        }

        return new CustomUserDetails(data);

//        return null;
    }
}
