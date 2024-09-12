package com.DOH.DOH.controller.list;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ProfileListController {

    @GetMapping("/portfolio/list")
    public String portfolioList(){
        return "list/portfolioList";
    }
}
