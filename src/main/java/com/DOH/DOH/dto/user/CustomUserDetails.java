package com.DOH.DOH.dto.user;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;



public class CustomUserDetails implements UserDetails {

    //    private LoginDTO loginDTO;
    private RegisterDTO registerDTO;

    public CustomUserDetails(RegisterDTO registerDTO){

        this.registerDTO = registerDTO;
    }


    @Override
    //사용자의 특정한 권한을 return = role 값
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return registerDTO.getRole();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {

        return registerDTO.getUserPassword();
    }

    @Override
    public String getUsername() {

        return registerDTO.getUserEmail();
    }


}