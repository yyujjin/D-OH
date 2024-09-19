package com.DOH.DOH.service.user;

import com.DOH.DOH.dto.user.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Collection;
import java.util.Iterator;

@Service
public class UserSessionService {

    public String userEmail(){
        String userEamil = SecurityContextHolder.getContext().getAuthentication().getName();
        return userEamil;
    }
    public String userRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        return role;
    }

    public String nickName() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetail = (CustomUserDetails) authentication.getPrincipal();
        return userDetail.getNickName();
    }

}
