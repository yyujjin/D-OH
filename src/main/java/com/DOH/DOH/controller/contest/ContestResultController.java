package com.DOH.DOH.controller.contest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ContestResultController {
    @GetMapping("/result")
    public String test() {
        return "/contest/result";
    }
}
