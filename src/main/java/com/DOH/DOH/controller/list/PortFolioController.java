package com.DOH.DOH.controller.list;

import com.DOH.DOH.dto.list.PortFolioDTO;
import com.DOH.DOH.service.list.PortFolioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Slf4j
@Controller
@RequestMapping("/portfolio")
public class PortFolioController {

    @Autowired
    PortFolioService portFolioService;

    @GetMapping("/list")
    public String portfolioList(Model model)
    {
        ArrayList<PortFolioDTO> dto = portFolioService.getPortFolioList();
        model.addAttribute("portfolioList", dto);
        return "list/portfolioList";

    }

    @GetMapping("/list/detail")
    public String viewPortfolio() {
        return "user/portfolioDetail";
    }
}
