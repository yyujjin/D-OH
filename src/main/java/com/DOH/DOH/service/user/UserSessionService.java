package com.DOH.DOH.service.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

@Service
public class UserSessionService {
//1.userEmail 가져오는 메서드 만들기
//2.유저권한 가져오는 메서드 만들기

    public String userEmail(){
        String userEamil = SecurityContextHolder.getContext().getAuthentication().getName();
        return userEamil;
    };
    public String userRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        return role;
    };


}