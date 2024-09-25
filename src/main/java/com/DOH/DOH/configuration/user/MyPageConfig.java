package com.DOH.DOH.configuration.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
public class MyPageConfig {
        @Bean
        public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
            return new HiddenHttpMethodFilter();
        }
}