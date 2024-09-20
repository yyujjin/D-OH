package com.DOH.DOH.controller.list;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/portfolio")
public class ProfileListController {

    @GetMapping("/list")
    public String portfolioList(){
        return "list/portfolioList";
    }

    @GetMapping("/list/detail")
    public String viewPortfolio() {
        return "user/portfolioDetail";
    }
}
