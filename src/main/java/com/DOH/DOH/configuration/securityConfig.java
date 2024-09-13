package com.DOH.DOH.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class securityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {



        http
                //특정한 경로에 요청을 허용, 거부
                .authorizeHttpRequests((auth)-> auth
                        .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                        .requestMatchers("/","/users/login","/users/register").permitAll()
                        .requestMatchers("/notice/admin/**","/event/admin/**").hasRole("ADMIN")
                        .requestMatchers("/users/**").hasRole("USER")
//                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                        //나머지 모든 요청은
                        .anyRequest().permitAll()
                );
        http
                .formLogin((auth)-> auth.loginPage("/users/login")
                        .loginProcessingUrl("/users/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/users/login")
                        .permitAll()
                );
        http
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 요청을 처리할 URL
                        .logoutSuccessUrl("/") // 로그아웃 성공 시 이동할 URL
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 쿠키 삭제
                        .permitAll()
                );

        http
                //보안 설정 - 세션쿠키 아이디 값을 변경
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId());

        http
                .csrf((auth) -> auth.disable());


        return http.build();
    }
}
