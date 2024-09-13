package com.DOH.DOH.controller;

import com.DOH.DOH.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class Test {

    @Autowired
    TestService testService;

    @GetMapping("/01")
    public String index() {
        int id =1;
        log.info("가져온 이름 : {}",testService.test(1));
        return "index";
    }
}
