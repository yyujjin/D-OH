package com.DOH.DOH.service.user;

import com.DOH.DOH.dto.user.CustomUserDetails;
import com.DOH.DOH.dto.user.RegisterDTO;
import com.DOH.DOH.mapper.user.RegisterMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private RegisterMapper registerMapper;

    public CustomUserDetailsService(RegisterMapper registerMapper) {

        this.registerMapper = registerMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        RegisterDTO data = registerMapper.getUserByUserEmail(userEmail);

        if (data == null) {
            throw new UsernameNotFoundException("User not found with email: " + userEmail);
        }

        return new CustomUserDetails(data);

//        return null;
    }
}
