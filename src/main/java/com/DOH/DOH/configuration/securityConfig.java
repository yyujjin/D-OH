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
                .authorizeHttpRequests((auth) -> auth
                        // 정적 리소스는 로그인 없이도 접근 가능하게 설정
                        .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                        // 로그인, 회원가입은 누구나 접근 가능
                        .requestMatchers("/", "/users/login", "/users/register").permitAll()
                        // 관리자만 접근 가능한 경로
                        .requestMatchers("/notice/admin/**", "/event/admin/**").hasRole("ADMIN")
                        // 일반 사용자만 접근 가능한 경로
                        .requestMatchers("/users/**").hasRole("USER")
                        // 그 외 모든 요청은 인증이 필요
                        .anyRequest().authenticated()
                )
                .formLogin((auth) -> auth
                        .loginPage("/users/login") // 사용자 정의 로그인 페이지
                        .loginProcessingUrl("/users/login") // 로그인 폼 액션 URL
                        .defaultSuccessUrl("/", true) // 로그인 성공 시 리디렉션
                        .failureUrl("/users/login?error=true") // 로그인 실패 시 리디렉션
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout") // 로그아웃 URL
                        .logoutSuccessUrl("/") // 로그아웃 성공 후 리디렉션
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 로그아웃 시 쿠키 삭제
                        .permitAll()
                )
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId() // 세션 고정 공격 방지
                )
                .csrf((auth) -> auth
                        // 로그인, 회원가입, 로그아웃 시 CSRF 검증 제외
                        .ignoringRequestMatchers("/users/login", "/users/register", "user/logout")
                );

        return http.build();
    }
}
