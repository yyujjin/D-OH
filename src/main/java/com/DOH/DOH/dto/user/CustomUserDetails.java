package com.DOH.DOH.dto.user;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;



public class CustomUserDetails implements UserDetails {

//    private LoginDTO loginDTO;
    private LoginDTO loginDTO;

    public CustomUserDetails(LoginDTO loginDTO){

        this.loginDTO = loginDTO;
    }


    @Override
    //사용자의 특정한 권한을 return = role 값
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return loginDTO.getRole();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return loginDTO.getUserPassword();
    }

    @Override
    public String getUsername() {
        return loginDTO.getUserEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
