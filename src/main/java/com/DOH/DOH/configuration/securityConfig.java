package com.DOH.DOH.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
                // 1. 모든 나머지 요청은 인증된 사용자만 접근 가능하도록 설정
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
                // 2. 로그인 실패 시 error=true 파라미터 없이 리디렉션 설정
                .formLogin((auth) -> auth.loginPage("/users/login")
                        .loginProcessingUrl("/users/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/users/login") // 로그인 실패 시 파라미터 없이 리디렉션
                        .permitAll()
                );
        http
                // 3. 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 요청을 처리할 URL
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST")) // POST 요청으로 처리
                        .logoutSuccessUrl("/") // 로그아웃 성공 시 이동할 URL
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 쿠키 삭제
                        .permitAll()
                );

        http
                // 4. 보안 설정 - 세션 고정 공격 방지
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId());

        http
                // 5. CSRF 설정에서 로그인, 회원가입, 로그아웃 경로만 제외
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers("/users/login", "/users/register", "/logout")
                );

        return http.build();
    }
}
